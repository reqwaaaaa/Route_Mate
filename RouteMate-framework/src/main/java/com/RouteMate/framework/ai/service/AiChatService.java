package com.RouteMate.framework.ai.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.alibaba.fastjson2.JSON;
import com.RouteMate.common.exception.ServiceException;
import com.RouteMate.common.utils.StringUtils;
import com.RouteMate.framework.ai.domain.AiChatRequest;
import com.RouteMate.framework.ai.domain.AiChatResponse;
import com.RouteMate.framework.ai.domain.AiMessage;
import com.RouteMate.framework.ai.util.AiResponseProcessor;
import com.RouteMate.framework.config.AiConfig;

/**
 * AI聊天服务
 * 
 * @author RouteMate
 */
@Service
public class AiChatService
{
    private static final Logger log = LoggerFactory.getLogger(AiChatService.class);

    @Autowired
    private AiConfig aiConfig;

    /**
     * 发送聊天请求（非流式）
     *
     * @param messages 消息列表
     * @return AI响应
     */
    public AiChatResponse chat(List<AiMessage> messages)
    {
        return chat(messages, false);
    }

    /**
     * 发送聊天请求
     *
     * @param messages 消息列表
     * @param stream 是否流式输出
     * @return AI响应
     */
    public AiChatResponse chat(List<AiMessage> messages, boolean stream)
    {
        AiChatRequest request = buildRequest(messages, stream);
        AiChatResponse response = sendRequest(request);

        // 处理响应中的AI回答数据
        if (response != null && response.getChoices() != null) {
            for (AiChatResponse.Choice choice : response.getChoices()) {
                if (choice.getMessage() != null && StringUtils.isNotEmpty(choice.getMessage().getContent())) {
                    String processedContent = AiResponseProcessor.processAiResponse(choice.getMessage().getContent());
                    choice.getMessage().setContent(processedContent);
                }
            }
        }

        return response;
    }

    /**
     * 发送流式聊天请求
     * 
     * @param messages 消息列表
     * @param callback 流式回调函数
     */
    public void chatStream(List<AiMessage> messages, Consumer<String> callback)
    {
        AiChatRequest request = buildRequest(messages, true);
        sendStreamRequest(request, callback);
    }

    /**
     * 发送流式聊天请求（SSE）
     *
     * @param messages 消息列表
     * @param emitter SSE发射器
     */
    public void chatStreamSse(List<AiMessage> messages, SseEmitter emitter)
    {
        chatStream(messages, content -> {
            try {
                // 对流式内容也进行处理
                String processedContent = AiResponseProcessor.processStreamContent(content);
                if (StringUtils.isNotEmpty(processedContent)) {
                    emitter.send(SseEmitter.event().data(processedContent));
                }
            } catch (IOException e) {
                log.error("SSE发送失败", e);
                emitter.completeWithError(e);
            }
        });
    }

    /**
     * 处理流式内容，清理特定标记但保留换行符
     *
     * @param content 流式内容片段
     * @return 处理后的内容
     */
    private String processStreamContent(String content)
    {
        if (StringUtils.isEmpty(content)) {
            return content;
        }

        String processedContent = content;

        // 移除 ** 标记
        processedContent = processedContent.replaceAll("\\*\\*", "");

        // 移除 ### 标记
        processedContent = processedContent.replaceAll("###", "");

        // 移除 _PAUSE_ 标记
        processedContent = processedContent.replaceAll("_PAUSE_", "");

        return processedContent;
    }

    /**
     * 简单聊天（单轮对话）
     *
     * @param userMessage 用户消息
     * @return AI回复
     */
    public String simpleChat(String userMessage)
    {
        List<AiMessage> messages = new ArrayList<>();
        messages.add(AiMessage.system(aiConfig.getDefaultSystemPrompt()));
        messages.add(AiMessage.user(userMessage));

        AiChatResponse response = chat(messages);
        if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()) {
            AiMessage message = response.getChoices().get(0).getMessage();
            String content = message != null ? message.getContent() : "";
            // 处理AI回答数据，清理多余的标记和换行符
            return AiResponseProcessor.processAiResponse(content);
        }
        return "";
    }



    /**
     * 构建请求对象
     */
    private AiChatRequest buildRequest(List<AiMessage> messages, boolean stream)
    {
        AiChatRequest request = new AiChatRequest();
        request.setModel(aiConfig.getDefaultModel());
        request.setMessages(messages);
        request.setStream(stream);
        request.setMaxTokens(aiConfig.getMaxTokens());
        request.setTemperature(aiConfig.getTemperature());
        request.setTopP(aiConfig.getTopP());
        
        return request;
    }

    /**
     * 发送HTTP请求（带重试机制）
     */
    private AiChatResponse sendRequest(AiChatRequest request)
    {
        int retryCount = 0;
        int maxRetries = aiConfig.isRetryEnabled() ? aiConfig.getMaxRetries() : 1;

        while (retryCount < maxRetries) {
            try {
                return sendSingleRequest(request);
            } catch (Exception e) {
                retryCount++;
                log.warn("AI请求失败，第{}次重试，错误: {}", retryCount, e.getMessage());

                if (retryCount >= maxRetries) {
                    log.error("AI请求重试{}次后仍然失败", maxRetries, e);
                    throw new ServiceException("AI请求失败: " + e.getMessage());
                }

                // 重试间隔
                if (aiConfig.isRetryEnabled() && retryCount < maxRetries) {
                    try {
                        Thread.sleep(aiConfig.getRetryInterval());
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new ServiceException("AI请求被中断");
                    }
                }
            }
        }

        throw new ServiceException("AI请求失败");
    }

    /**
     * 发送单次HTTP请求
     */
    private AiChatResponse sendSingleRequest(AiChatRequest request)
    {
        try {
            URL url = new URL(aiConfig.getApiUrl());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            // 设置请求方法和头部
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + aiConfig.getApiKey());
            connection.setRequestProperty("User-Agent", "RouteMate-AI-Client/1.0");
            connection.setDoOutput(true);
            connection.setConnectTimeout(aiConfig.getConnectTimeout());
            connection.setReadTimeout(aiConfig.getReadTimeout());

            log.debug("AI请求配置 - 连接超时: {}ms, 读取超时: {}ms",
                     aiConfig.getConnectTimeout(), aiConfig.getReadTimeout());
            
            // 发送请求体
            String requestBody = JSON.toJSONString(request);
            log.debug("AI请求: {}", requestBody);

            try (java.io.OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(requestBody.getBytes(StandardCharsets.UTF_8));
            }
            
            // 读取响应
            int responseCode = connection.getResponseCode();
            log.debug("AI API响应状态码: {}", responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    
                    String responseBody = response.toString();
                    log.debug("AI响应: {}", responseBody);
                    
                    return JSON.parseObject(responseBody, AiChatResponse.class);
                }
            } else {
                // 读取错误响应
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
                    StringBuilder errorResponse = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        errorResponse.append(line);
                    }
                    String errorMsg = errorResponse.toString();
                    log.error("AI请求失败，状态码: {}, 错误信息: {}", responseCode, errorMsg);

                    // 根据状态码提供更具体的错误信息
                    String specificError = getSpecificErrorMessage(responseCode, errorMsg);
                    throw new ServiceException(specificError);
                }
            }
        } catch (java.net.SocketTimeoutException e) {
            log.error("AI请求超时", e);
            throw new ServiceException("AI请求超时，请检查网络连接或增加超时时间");
        } catch (java.net.ConnectException e) {
            log.error("AI连接失败", e);
            throw new ServiceException("无法连接到AI服务，请检查网络和API地址");
        } catch (IOException e) {
            log.error("AI请求IO异常", e);
            throw new ServiceException("AI请求网络异常: " + e.getMessage());
        }
    }

    /**
     * 根据状态码获取具体错误信息
     */
    private String getSpecificErrorMessage(int statusCode, String errorResponse)
    {
        switch (statusCode) {
            case 400:
                return "AI请求参数错误: " + errorResponse;
            case 401:
                return "AI API密钥无效，请检查配置";
            case 403:
                return "AI API访问被拒绝，请检查权限";
            case 404:
                return "AI API地址不存在，请检查配置";
            case 429:
                return "AI API请求频率过高，请稍后重试";
            case 500:
                return "AI服务内部错误，请稍后重试";
            case 502:
            case 503:
            case 504:
                return "AI服务暂时不可用，请稍后重试";
            default:
                return "AI请求失败(状态码:" + statusCode + "): " + errorResponse;
        }
    }

    /**
     * 发送流式HTTP请求
     */
    private void sendStreamRequest(AiChatRequest request, Consumer<String> callback)
    {
        try {
            URL url = new URL(aiConfig.getApiUrl());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            // 设置请求方法和头部
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + aiConfig.getApiKey());
            connection.setDoOutput(true);
            connection.setConnectTimeout(aiConfig.getConnectTimeout());
            connection.setReadTimeout(aiConfig.getReadTimeout());
            
            // 发送请求体
            String requestBody = JSON.toJSONString(request);
            log.debug("AI流式请求: {}", requestBody);

            try (java.io.OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(requestBody.getBytes(StandardCharsets.UTF_8));
            }
            
            // 读取流式响应
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (StringUtils.isNotEmpty(line) && line.startsWith("data: ")) {
                            String data = line.substring(6); // 移除 "data: " 前缀
                            if (!"[DONE]".equals(data)) {
                                try {
                                    AiChatResponse response = JSON.parseObject(data, AiChatResponse.class);
                                    if (response.getChoices() != null && !response.getChoices().isEmpty()) {
                                        AiMessage delta = response.getChoices().get(0).getDelta();
                                        if (delta != null && StringUtils.isNotEmpty(delta.getContent())) {
                                            callback.accept(delta.getContent());
                                        }
                                    }
                                } catch (Exception e) {
                                    log.warn("解析流式响应失败: {}", data, e);
                                }
                            }
                        }
                    }
                }
            } else {
                // 读取错误响应
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
                    StringBuilder errorResponse = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        errorResponse.append(line);
                    }
                    log.error("AI流式请求失败，状态码: {}, 错误信息: {}", responseCode, errorResponse.toString());
                    throw new ServiceException("AI流式请求失败: " + errorResponse.toString());
                }
            }
        } catch (IOException e) {
            log.error("AI流式请求异常", e);
            throw new ServiceException("AI流式请求异常: " + e.getMessage());
        }
    }
}
