package com.RouteMate.web.controller.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.RouteMate.common.core.controller.BaseController;
import com.RouteMate.common.core.domain.AjaxResult;
import com.RouteMate.framework.ai.domain.AiChatResponse;
import com.RouteMate.framework.ai.domain.AiMessage;
import com.RouteMate.framework.ai.service.AiChatService;
import com.RouteMate.framework.ai.util.NetworkTestUtil;
import com.RouteMate.framework.config.AiConfig;

/**
 * AI功能测试控制器
 * 
 * @author RouteMate
 */
@RestController
@RequestMapping("/test/ai")
public class AiTestController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(AiTestController.class);

    @Autowired
    private AiChatService aiChatService;

    @Autowired
    private AiConfig aiConfig;

    /**
     * 测试AI配置
     */
    @GetMapping("/config")
    public AjaxResult testConfig()
    {
        AjaxResult result = AjaxResult.success();
        result.put("apiUrl", aiConfig.getApiUrl());
        result.put("model", aiConfig.getDefaultModel());
        result.put("maxTokens", aiConfig.getMaxTokens());
        result.put("temperature", aiConfig.getTemperature());
        result.put("topP", aiConfig.getTopP());
        result.put("streamEnabled", aiConfig.isStreamEnabled());
        result.put("systemPrompt", aiConfig.getDefaultSystemPrompt());
        result.put("connectTimeout", aiConfig.getConnectTimeout());
        result.put("readTimeout", aiConfig.getReadTimeout());
        return result;
    }

    /**
     * 测试简单聊天
     */
    @GetMapping("/simple")
    public AjaxResult testSimpleChat(@RequestParam(defaultValue = "你好") String message)
    {
        try {
            String response = aiChatService.simpleChat(message);
            return AjaxResult.success("AI简单聊天测试成功", response);
        } catch (Exception e) {
            log.error("AI简单聊天测试失败", e);
            return AjaxResult.error("AI简单聊天测试失败: " + e.getMessage());
        }
    }

    /**
     * 测试多轮聊天
     */
    @PostMapping("/conversation")
    public AjaxResult testConversation(@RequestBody TestConversationRequest request)
    {
        try {
            List<AiMessage> messages = new ArrayList<>();
            messages.add(AiMessage.system(aiConfig.getDefaultSystemPrompt()));
            
            if (request.getMessages() != null) {
                messages.addAll(request.getMessages());
            } else {
                messages.add(AiMessage.user("常见的十字花科植物有哪些？"));
            }
            
            AiChatResponse response = aiChatService.chat(messages);
            return AjaxResult.success("AI多轮聊天测试成功", response);
        } catch (Exception e) {
            log.error("AI多轮聊天测试失败", e);
            return AjaxResult.error("AI多轮聊天测试失败: " + e.getMessage());
        }
    }

    /**
     * 测试流式聊天
     */
    @GetMapping("/stream")
    public SseEmitter testStreamChat(@RequestParam(defaultValue = "请介绍一下人工智能的发展历程") String message)
    {
        SseEmitter emitter = new SseEmitter(120000L); // 2分钟超时

        CompletableFuture.runAsync(() -> {
            try {
                List<AiMessage> messages = new ArrayList<>();
                messages.add(AiMessage.system(aiConfig.getDefaultSystemPrompt()));
                messages.add(AiMessage.user(message));

                log.info("开始流式聊天测试，消息: {}", message);
                
                aiChatService.chatStream(messages, content -> {
                    try {
                        emitter.send(SseEmitter.event().data(content));
                        log.debug("发送流式数据: {}", content);
                    } catch (Exception e) {
                        log.error("SSE发送失败", e);
                        emitter.completeWithError(e);
                    }
                });
                
                emitter.complete();
                log.info("流式聊天测试完成");
            } catch (Exception e) {
                log.error("流式聊天测试失败", e);
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }

    /**
     * 测试连接
     */
    @GetMapping("/connection")
    public AjaxResult testConnection()
    {
        try {
            String testMessage = "请回复'连接测试成功'";
            String response = aiChatService.simpleChat(testMessage);

            AjaxResult result = AjaxResult.success("AI连接测试完成");
            result.put("request", testMessage);
            result.put("response", response);
            result.put("status", response.contains("连接测试成功") ? "成功" : "部分成功");

            return result;
        } catch (Exception e) {
            log.error("AI连接测试失败", e);
            return AjaxResult.error("AI连接测试失败: " + e.getMessage());
        }
    }

    /**
     * 网络诊断
     */
    @GetMapping("/network")
    public AjaxResult networkDiagnostics()
    {
        try {
            AjaxResult result = AjaxResult.success("网络诊断完成");

            // 基础网络信息
            result.put("systemInfo", NetworkTestUtil.getNetworkDiagnostics());

            // URL连通性测试
            result.put("urlTest", NetworkTestUtil.testUrlConnectivity(
                aiConfig.getApiUrl(), aiConfig.getConnectTimeout()));

            // AI API连通性测试
            result.put("apiTest", NetworkTestUtil.testAiApiConnectivity(
                aiConfig.getApiUrl(), aiConfig.getApiKey(), aiConfig.getConnectTimeout()));

            return result;
        } catch (Exception e) {
            log.error("网络诊断失败", e);
            return AjaxResult.error("网络诊断失败: " + e.getMessage());
        }
    }

    /**
     * 获取测试帮助信息
     */
    @GetMapping("/help")
    public AjaxResult help()
    {
        AjaxResult result = AjaxResult.success();
        result.put("message", "AI功能测试说明");
        result.put("apis", new String[]{
            "GET /test/ai/config - 查看AI配置",
            "GET /test/ai/simple?message=xxx - 测试简单聊天",
            "POST /test/ai/conversation - 测试多轮聊天",
            "GET /test/ai/stream?message=xxx - 测试流式聊天",
            "GET /test/ai/connection - 测试AI连接",
            "GET /test/ai/network - 网络诊断",
            "GET /test/ai/performance?count=5 - 性能测试",
            "GET /test/ai/help - 查看帮助信息"
        });
        result.put("examples", new String[]{
            "简单聊天: /test/ai/simple?message=你好",
            "流式聊天: /test/ai/stream?message=请介绍一下人工智能",
            "连接测试: /test/ai/connection",
            "网络诊断: /test/ai/network",
            "性能测试: /test/ai/performance?count=3"
        });
        result.put("notes", new String[]{
            "1. 确保AI配置正确，包括API密钥和模型名称",
            "2. 流式聊天使用SSE技术，需要支持EventSource的客户端",
            "3. 测试前请确保网络连接正常",
            "4. API密钥需要有效且有足够的配额",
            "5. 如果出现超时错误，可以先运行网络诊断",
            "6. 超时配置已优化：连接60秒，读取120秒"
        });
        return result;
    }

    /**
     * 性能测试
     */
    @GetMapping("/performance")
    public AjaxResult performanceTest(@RequestParam(defaultValue = "5") int count)
    {
        if (count > 10) {
            return AjaxResult.error("测试次数不能超过10次");
        }

        List<Long> responseTimes = new ArrayList<>();
        List<String> responses = new ArrayList<>();
        
        for (int i = 0; i < count; i++) {
            try {
                long startTime = System.currentTimeMillis();
                String response = aiChatService.simpleChat("这是第" + (i + 1) + "次性能测试，请简短回复");
                long endTime = System.currentTimeMillis();
                
                responseTimes.add(endTime - startTime);
                responses.add(response);
            } catch (Exception e) {
                log.error("第{}次性能测试失败", i + 1, e);
                responseTimes.add(-1L);
                responses.add("测试失败: " + e.getMessage());
            }
        }
        
        // 计算统计信息
        long totalTime = responseTimes.stream().filter(t -> t > 0).mapToLong(Long::longValue).sum();
        long avgTime = responseTimes.stream().filter(t -> t > 0).mapToLong(Long::longValue).sum() / 
                       responseTimes.stream().filter(t -> t > 0).count();
        long maxTime = responseTimes.stream().filter(t -> t > 0).mapToLong(Long::longValue).max().orElse(0);
        long minTime = responseTimes.stream().filter(t -> t > 0).mapToLong(Long::longValue).min().orElse(0);
        
        AjaxResult result = AjaxResult.success("AI性能测试完成");
        result.put("testCount", count);
        result.put("responseTimes", responseTimes);
        result.put("responses", responses);
        result.put("statistics", new Object() {
            public final long totalTime = AiTestController.this.getTotalTime(responseTimes);
            public final long avgTime = AiTestController.this.getAvgTime(responseTimes);
            public final long maxTime = AiTestController.this.getMaxTime(responseTimes);
            public final long minTime = AiTestController.this.getMinTime(responseTimes);
            public final long successCount = responseTimes.stream().filter(t -> t > 0).count();
        });
        
        return result;
    }

    private long getTotalTime(List<Long> times) {
        return times.stream().filter(t -> t > 0).mapToLong(Long::longValue).sum();
    }

    private long getAvgTime(List<Long> times) {
        return times.stream().filter(t -> t > 0).mapToLong(Long::longValue).sum() / 
               Math.max(1, times.stream().filter(t -> t > 0).count());
    }

    private long getMaxTime(List<Long> times) {
        return times.stream().filter(t -> t > 0).mapToLong(Long::longValue).max().orElse(0);
    }

    private long getMinTime(List<Long> times) {
        return times.stream().filter(t -> t > 0).mapToLong(Long::longValue).min().orElse(0);
    }

    /**
     * 测试多轮聊天请求对象
     */
    public static class TestConversationRequest
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
