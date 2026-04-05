package com.RouteMate.web.controller.test;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.RouteMate.common.core.controller.BaseController;
import com.RouteMate.common.core.domain.AjaxResult;
import com.RouteMate.framework.ai.domain.AiMessage;
import com.RouteMate.framework.ai.service.AiChatService;
import com.RouteMate.framework.config.AiConfig;

/**
 * AI功能验证控制器
 * 
 * @author RouteMate
 */
@RestController
@RequestMapping("/test/ai/verify")
public class AiVerificationController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(AiVerificationController.class);

    @Autowired
    private AiChatService aiChatService;

    @Autowired
    private AiConfig aiConfig;

    /**
     * 验证AI配置
     */
    @GetMapping("/config")
    public AjaxResult verifyConfig()
    {
        try {
            AjaxResult result = AjaxResult.success("AI配置验证完成");
            result.put("apiUrl", aiConfig.getApiUrl());
            result.put("model", aiConfig.getDefaultModel());
            result.put("maxTokens", aiConfig.getMaxTokens());
            result.put("temperature", aiConfig.getTemperature());
            result.put("topP", aiConfig.getTopP());
            result.put("streamEnabled", aiConfig.isStreamEnabled());
            result.put("systemPrompt", aiConfig.getDefaultSystemPrompt());
            result.put("connectTimeout", aiConfig.getConnectTimeout());
            result.put("readTimeout", aiConfig.getReadTimeout());
            
            // 验证配置完整性
            boolean configValid = true;
            List<String> issues = new ArrayList<>();
            
            if (aiConfig.getApiUrl() == null || aiConfig.getApiUrl().isEmpty()) {
                configValid = false;
                issues.add("API URL未配置");
            }
            
            if (aiConfig.getApiKey() == null || aiConfig.getApiKey().isEmpty()) {
                configValid = false;
                issues.add("API Key未配置");
            }
            
            if (aiConfig.getDefaultModel() == null || aiConfig.getDefaultModel().isEmpty()) {
                configValid = false;
                issues.add("默认模型未配置");
            }
            
            result.put("configValid", configValid);
            result.put("issues", issues);
            
            return result;
        } catch (Exception e) {
            log.error("AI配置验证失败", e);
            return error("AI配置验证失败: " + e.getMessage());
        }
    }

    /**
     * 验证AI服务
     */
    @GetMapping("/service")
    public AjaxResult verifyService()
    {
        try {
            // 检查服务是否可用
            boolean serviceAvailable = aiChatService != null;
            
            AjaxResult result = AjaxResult.success("AI服务验证完成");
            result.put("serviceAvailable", serviceAvailable);
            
            if (serviceAvailable) {
                result.put("serviceClass", aiChatService.getClass().getSimpleName());
                result.put("message", "AI服务已正确注入");
            } else {
                result.put("message", "AI服务未正确注入");
            }
            
            return result;
        } catch (Exception e) {
            log.error("AI服务验证失败", e);
            return error("AI服务验证失败: " + e.getMessage());
        }
    }

    /**
     * 验证AI消息创建
     */
    @GetMapping("/message")
    public AjaxResult verifyMessage()
    {
        try {
            // 测试消息创建
            AiMessage systemMsg = AiMessage.system("你是一个测试助手");
            AiMessage userMsg = AiMessage.user("测试消息");
            AiMessage assistantMsg = AiMessage.assistant("测试回复");
            
            List<AiMessage> messages = new ArrayList<>();
            messages.add(systemMsg);
            messages.add(userMsg);
            messages.add(assistantMsg);
            
            AjaxResult result = AjaxResult.success("AI消息验证完成");
            result.put("systemMessage", systemMsg);
            result.put("userMessage", userMsg);
            result.put("assistantMessage", assistantMsg);
            result.put("messageCount", messages.size());
            result.put("message", "AI消息对象创建正常");
            
            return result;
        } catch (Exception e) {
            log.error("AI消息验证失败", e);
            return error("AI消息验证失败: " + e.getMessage());
        }
    }

    /**
     * 验证AI聊天功能（不实际调用API）
     */
    @GetMapping("/chat-mock")
    public AjaxResult verifyChatMock()
    {
        try {
            // 模拟聊天测试（不实际调用API）
            List<AiMessage> messages = new ArrayList<>();
            messages.add(AiMessage.system(aiConfig.getDefaultSystemPrompt()));
            messages.add(AiMessage.user("这是一个测试消息"));
            
            AjaxResult result = AjaxResult.success("AI聊天功能验证完成（模拟）");
            result.put("testMessages", messages);
            result.put("messageCount", messages.size());
            result.put("message", "AI聊天功能结构正常，可以进行实际API调用测试");
            
            return result;
        } catch (Exception e) {
            log.error("AI聊天功能验证失败", e);
            return error("AI聊天功能验证失败: " + e.getMessage());
        }
    }

    /**
     * 综合验证
     */
    @GetMapping("/all")
    public AjaxResult verifyAll()
    {
        try {
            List<String> results = new ArrayList<>();
            List<String> errors = new ArrayList<>();
            
            // 1. 配置验证
            try {
                AjaxResult configResult = verifyConfig();
                if (configResult.get("code").equals(200)) {
                    results.add("✅ AI配置验证通过");
                    Boolean configValid = (Boolean) configResult.get("configValid");
                    if (!configValid) {
                        @SuppressWarnings("unchecked")
                        List<String> issues = (List<String>) configResult.get("issues");
                        errors.addAll(issues);
                    }
                } else {
                    errors.add("❌ AI配置验证失败");
                }
            } catch (Exception e) {
                errors.add("❌ AI配置验证异常: " + e.getMessage());
            }
            
            // 2. 服务验证
            try {
                AjaxResult serviceResult = verifyService();
                if (serviceResult.get("code").equals(200)) {
                    results.add("✅ AI服务验证通过");
                } else {
                    errors.add("❌ AI服务验证失败");
                }
            } catch (Exception e) {
                errors.add("❌ AI服务验证异常: " + e.getMessage());
            }
            
            // 3. 消息验证
            try {
                AjaxResult messageResult = verifyMessage();
                if (messageResult.get("code").equals(200)) {
                    results.add("✅ AI消息验证通过");
                } else {
                    errors.add("❌ AI消息验证失败");
                }
            } catch (Exception e) {
                errors.add("❌ AI消息验证异常: " + e.getMessage());
            }
            
            // 4. 聊天功能验证
            try {
                AjaxResult chatResult = verifyChatMock();
                if (chatResult.get("code").equals(200)) {
                    results.add("✅ AI聊天功能验证通过");
                } else {
                    errors.add("❌ AI聊天功能验证失败");
                }
            } catch (Exception e) {
                errors.add("❌ AI聊天功能验证异常: " + e.getMessage());
            }
            
            // 生成综合结果
            boolean allPassed = errors.isEmpty();
            AjaxResult result = allPassed ? 
                AjaxResult.success("AI功能综合验证完成") : 
                AjaxResult.warn("AI功能验证发现问题");
                
            result.put("allPassed", allPassed);
            result.put("successCount", results.size());
            result.put("errorCount", errors.size());
            result.put("results", results);
            result.put("errors", errors);
            
            if (allPassed) {
                result.put("message", "🎉 所有AI功能验证通过，可以进行实际API测试");
            } else {
                result.put("message", "⚠️ 发现问题，请检查配置和依赖");
            }
            
            return result;
        } catch (Exception e) {
            log.error("AI综合验证失败", e);
            return error("AI综合验证失败: " + e.getMessage());
        }
    }

    /**
     * 获取验证帮助信息
     */
    @GetMapping("/help")
    public AjaxResult getHelp()
    {
        AjaxResult result = AjaxResult.success("AI功能验证帮助");
        result.put("description", "AI功能验证接口，用于检查AI集成的各个组件");
        result.put("endpoints", new String[]{
            "GET /test/ai/verify/config - 验证AI配置",
            "GET /test/ai/verify/service - 验证AI服务",
            "GET /test/ai/verify/message - 验证AI消息",
            "GET /test/ai/verify/chat-mock - 验证AI聊天功能（模拟）",
            "GET /test/ai/verify/all - 综合验证",
            "GET /test/ai/verify/help - 获取帮助信息"
        });
        result.put("usage", new String[]{
            "1. 首先运行综合验证: /test/ai/verify/all",
            "2. 如果有问题，逐个检查各项验证",
            "3. 修复问题后重新验证",
            "4. 所有验证通过后进行实际API测试"
        });
        return result;
    }
}
