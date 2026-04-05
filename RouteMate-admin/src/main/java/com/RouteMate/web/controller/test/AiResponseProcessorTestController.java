package com.RouteMate.web.controller.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.RouteMate.common.core.controller.BaseController;
import com.RouteMate.common.core.domain.AjaxResult;
import com.RouteMate.framework.ai.util.AiResponseProcessor;

/**
 * AI回答数据处理测试控制器
 * 
 * @author RouteMate
 */
@RestController
@RequestMapping("/test/ai/processor")
public class AiResponseProcessorTestController extends BaseController
{
    /**
     * 测试AI回答数据处理
     */
    @PostMapping("/process")
    public AjaxResult testProcessAiResponse(@RequestBody ProcessTestRequest request)
    {
        if (request.getText() == null) {
            return error("测试文本不能为空");
        }
        
        String originalText = request.getText();
        String processedText = AiResponseProcessor.processAiResponse(originalText);
        
        AjaxResult result = AjaxResult.success("AI回答数据处理测试完成");
        result.put("originalText", originalText);
        result.put("processedText", processedText);
        result.put("originalLength", originalText.length());
        result.put("processedLength", processedText.length());
        result.put("containsMarkers", AiResponseProcessor.containsCleanableMarkers(originalText));
        result.put("stats", AiResponseProcessor.getProcessingStats(originalText, processedText));
        
        return result;
    }

    /**
     * 测试流式内容处理
     */
    @PostMapping("/stream")
    public AjaxResult testProcessStreamContent(@RequestBody ProcessTestRequest request)
    {
        if (request.getText() == null) {
            return error("测试文本不能为空");
        }
        
        String originalText = request.getText();
        String processedText = AiResponseProcessor.processStreamContent(originalText);
        
        AjaxResult result = AjaxResult.success("流式内容处理测试完成");
        result.put("originalText", originalText);
        result.put("processedText", processedText);
        result.put("originalLength", originalText.length());
        result.put("processedLength", processedText.length());
        
        return result;
    }

    /**
     * 批量处理测试
     */
    @PostMapping("/batch")
    public AjaxResult testBatchProcess(@RequestBody BatchProcessTestRequest request)
    {
        if (request.getTexts() == null || request.getTexts().length == 0) {
            return error("测试文本数组不能为空");
        }
        
        String[] originalTexts = request.getTexts();
        String[] processedTexts = AiResponseProcessor.batchProcessAiResponse(originalTexts);
        
        AjaxResult result = AjaxResult.success("批量处理测试完成");
        result.put("originalTexts", originalTexts);
        result.put("processedTexts", processedTexts);
        result.put("count", originalTexts.length);
        
        return result;
    }

    /**
     * 预设示例测试
     */
    @GetMapping("/examples")
    public AjaxResult testExamples()
    {
        // 创建包含各种需要清理标记的示例文本
        String[] examples = {
            "\n\n**这是一个测试**\n\n### 标题\n_PAUSE_\n内容...",
            "**粗体文本** 和 ###标题### 以及 _PAUSE_ 标记",
            "\n\n\n多个换行符\n\n\n需要清理\n\n\n",
            "正常文本不需要处理",
            "**开始**内容###中间###_PAUSE_结束**"
        };
        
        AjaxResult result = AjaxResult.success("预设示例测试完成");
        
        for (int i = 0; i < examples.length; i++) {
            String original = examples[i];
            String processed = AiResponseProcessor.processAiResponse(original);
            
            result.put("example" + (i + 1) + "_original", original);
            result.put("example" + (i + 1) + "_processed", processed);
            result.put("example" + (i + 1) + "_stats", 
                      AiResponseProcessor.getProcessingStats(original, processed));
        }
        
        return result;
    }

    /**
     * 性能测试
     */
    @GetMapping("/performance")
    public AjaxResult testPerformance(@RequestParam(defaultValue = "1000") int iterations)
    {
        String testText = "\n\n**这是一个性能测试文本**\n\n### 包含各种标记 ###\n_PAUSE_\n" +
                         "用于测试处理性能\n\n**结束**";
        
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < iterations; i++) {
            AiResponseProcessor.processAiResponse(testText);
        }
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        AjaxResult result = AjaxResult.success("性能测试完成");
        result.put("iterations", iterations);
        result.put("duration", duration + "ms");
        result.put("averageTime", (double) duration / iterations + "ms");
        result.put("throughput", (int) (iterations * 1000.0 / duration) + " ops/sec");
        
        return result;
    }

    /**
     * 获取测试帮助信息
     */
    @GetMapping("/help")
    public AjaxResult getHelp()
    {
        AjaxResult result = AjaxResult.success("AI回答数据处理测试帮助");
        result.put("description", "AI回答数据处理功能测试接口");
        result.put("endpoints", new String[]{
            "POST /test/ai/processor/process - 测试AI回答数据处理",
            "POST /test/ai/processor/stream - 测试流式内容处理", 
            "POST /test/ai/processor/batch - 测试批量处理",
            "GET /test/ai/processor/examples - 预设示例测试",
            "GET /test/ai/processor/performance - 性能测试",
            "GET /test/ai/processor/help - 获取帮助信息"
        });
        result.put("usage", new String[]{
            "1. 使用 /examples 查看预设示例效果",
            "2. 使用 /process 测试自定义文本处理",
            "3. 使用 /performance 进行性能测试",
            "4. 使用 /batch 测试批量处理功能"
        });
        
        return result;
    }

    /**
     * 处理测试请求对象
     */
    public static class ProcessTestRequest
    {
        private String text;

        public String getText()
        {
            return text;
        }

        public void setText(String text)
        {
            this.text = text;
        }
    }

    /**
     * 批量处理测试请求对象
     */
    public static class BatchProcessTestRequest
    {
        private String[] texts;

        public String[] getTexts()
        {
            return texts;
        }

        public void setTexts(String[] texts)
        {
            this.texts = texts;
        }
    }
}
