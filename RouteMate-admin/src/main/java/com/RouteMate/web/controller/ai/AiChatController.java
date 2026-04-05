package com.RouteMate.web.controller.ai;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.RouteMate.common.annotation.Anonymous;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.RouteMate.common.annotation.Log;
import com.RouteMate.common.core.controller.BaseController;
import com.RouteMate.common.core.domain.AjaxResult;
import com.RouteMate.common.enums.BusinessType;
import com.RouteMate.common.utils.StringUtils;
import com.RouteMate.framework.ai.domain.AiChatResponse;
import com.RouteMate.framework.ai.domain.AiMessage;
import com.RouteMate.framework.ai.service.AiChatService;
import com.RouteMate.framework.config.AiConfig;

/**
 * AI聊天控制器
 *
 * @author RouteMate
 */
@RestController
@RequestMapping("/ai/chat")
public class AiChatController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(AiChatController.class);

    @Autowired
    private AiChatService aiChatService;

    @Autowired
    private AiConfig aiConfig;

    /**
     * 简单聊天接口
     */
    @PostMapping("/simple")
    @Log(title = "AI简单聊天", businessType = BusinessType.OTHER)
    public AjaxResult simpleChat(@RequestBody SimpleChatRequest request)
    {
        if (StringUtils.isEmpty(request.getMessage())) {
            return error("消息内容不能为空");
        }

        try {
            String response = aiChatService.simpleChat(request.getMessage());
            return success(response);
        } catch (Exception e) {
            log.error("AI聊天失败", e);
            return error("AI聊天失败: " + e.getMessage());
        }
    }

    /**
     * 多轮聊天接口
     */
    @PostMapping("/conversation")
    @Log(title = "AI多轮聊天", businessType = BusinessType.OTHER)
    public AjaxResult conversation(@RequestBody ConversationRequest request)
    {
        if (request.getMessages() == null || request.getMessages().isEmpty()) {
            return error("消息列表不能为空");
        }

        try {
            AiChatResponse response = aiChatService.chat(request.getMessages());
            return success(response);
        } catch (Exception e) {
            log.error("AI多轮聊天失败", e);
            return error("AI多轮聊天失败: " + e.getMessage());
        }
    }

    /**
     * 流式聊天接口（SSE）
     */
    @Anonymous
    @GetMapping("/stream")
    public SseEmitter streamChat(@RequestParam String message)
    {
        if (StringUtils.isEmpty(message)) {
            throw new IllegalArgumentException("消息内容不能为空");
        }

        SseEmitter emitter = new SseEmitter(60000L); // 60秒超时

        CompletableFuture.runAsync(() -> {
            try {
                List<AiMessage> messages = new ArrayList<>();
                messages.add(AiMessage.system(aiConfig.getDefaultSystemPrompt()));
                messages.add(AiMessage.user(message));

                aiChatService.chatStreamSse(messages, emitter);
                emitter.complete();
            } catch (Exception e) {
                log.error("流式聊天失败", e);
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }

    /**
     * 流式多轮聊天接口（SSE）
     */
    @PostMapping("/stream")
    @Log(title = "AI流式聊天", businessType = BusinessType.OTHER)
    public SseEmitter streamConversation(@RequestBody ConversationRequest request)
    {
        if (request.getMessages() == null || request.getMessages().isEmpty()) {
            throw new IllegalArgumentException("消息列表不能为空");
        }

        SseEmitter emitter = new SseEmitter(60000L); // 60秒超时

        CompletableFuture.runAsync(() -> {
            try {
                aiChatService.chatStreamSse(request.getMessages(), emitter);
                emitter.complete();
            } catch (Exception e) {
                log.error("流式多轮聊天失败", e);
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }

    /**
     * 获取AI配置信息
     */
    @GetMapping("/config")
    @PreAuthorize("@ss.hasPermi('ai:chat:config')")
    public AjaxResult getConfig()
    {
        AjaxResult result = AjaxResult.success();
        result.put("model", aiConfig.getDefaultModel());
        result.put("maxTokens", aiConfig.getMaxTokens());
        result.put("temperature", aiConfig.getTemperature());
        result.put("topP", aiConfig.getTopP());
        result.put("streamEnabled", aiConfig.isStreamEnabled());
        result.put("systemPrompt", aiConfig.getDefaultSystemPrompt());
        return result;
    }

    /**
     * 测试AI连接
     */
    @GetMapping("/test")
    @PreAuthorize("@ss.hasPermi('ai:chat:test')")
    public AjaxResult testConnection()
    {
        try {
            String response = aiChatService.simpleChat("你好，请回复'连接测试成功'");
            AjaxResult result = AjaxResult.success("AI连接测试成功", response);
            return result;
        } catch (Exception e) {
            log.error("AI连接测试失败", e);
            return error("AI连接测试失败: " + e.getMessage());
        }
    }

    /**
     * 简单聊天请求对象
     */
    public static class SimpleChatRequest
    {
        private String message;

        public String getMessage()
        {
            return message;
        }

        public void setMessage(String message)
        {
            this.message = message;
        }
    }

    /**
     * 多轮聊天请求对象
     */
    public static class ConversationRequest
    {
        private List<AiMessage> messages;

        public List<AiMessage> getMessages()
        {
            return messages;
        }

        public void setMessages(List<AiMessage> messages)
        {
            this.messages = messages;
        }
    }
}
