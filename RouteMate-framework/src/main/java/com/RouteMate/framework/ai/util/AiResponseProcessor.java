package com.RouteMate.framework.ai.util;

import com.RouteMate.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AI回答数据处理工具类
 *
 * @author RouteMate
 */
public class AiResponseProcessor
{
    private static final Logger log = LoggerFactory.getLogger(AiResponseProcessor.class);

    /**
     * 处理AI智能回答数据，清理多余的换行符和特定标记
     *
     * @param aiResponse AI返回的原始数据
     * @return 处理后的清洁数据
     */
    public static String processAiResponse(String aiResponse)
    {
        if (StringUtils.isEmpty(aiResponse)) {
            return "";
        }

        String processedText = aiResponse;

        // 移除开始的两个换行符
        processedText = processedText.replaceFirst("^\\n\\n", "");

        // 移除所有的 ** 标记
        processedText = processedText.replaceAll("\\*\\*", "");

        // 移除所有的 ### 标记
        processedText = processedText.replaceAll("###", "");

        // 移除所有的 _PAUSE_ 标记
        processedText = processedText.replaceAll("__PAUSE__", "");
        // 移除所有的 - 标记
        processedText = processedText.replaceAll("-", "");

        // 清理多余的连续换行符，最多保留一个换行
        processedText = processedText.replaceAll("\\n{2,}", "\n");

        // 移除开头和结尾的空白字符
        processedText = processedText.trim();

        log.debug("AI回答数据处理 - 原始长度: {}, 处理后长度: {}", aiResponse.length(), processedText.length());

        return processedText;
    }

    /**
     * 处理流式内容，清理特定标记但保留换行符
     *
     * @param content 流式内容片段
     * @return 处理后的内容
     */
    public static String processStreamContent(String content)
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
     * 批量处理AI回答数据
     *
     * @param responses AI回答数组
     * @return 处理后的回答数组
     */
    public static String[] batchProcessAiResponse(String[] responses)
    {
        if (responses == null || responses.length == 0) {
            return responses;
        }

        String[] processedResponses = new String[responses.length];
        for (int i = 0; i < responses.length; i++) {
            processedResponses[i] = processAiResponse(responses[i]);
        }

        return processedResponses;
    }

    /**
     * 检查文本是否包含需要清理的标记
     *
     * @param text 待检查的文本
     * @return 是否包含需要清理的标记
     */
    public static boolean containsCleanableMarkers(String text)
    {
        if (StringUtils.isEmpty(text)) {
            return false;
        }

        return text.contains("**") ||
               text.contains("###") ||
               text.contains("_PAUSE_") ||
               text.startsWith("\n\n") ||
               text.contains("\n\n\n");
    }

    /**
     * 获取处理统计信息
     *
     * @param originalText 原始文本
     * @param processedText 处理后文本
     * @return 统计信息字符串
     */
    public static String getProcessingStats(String originalText, String processedText)
    {
        if (originalText == null) originalText = "";
        if (processedText == null) processedText = "";

        int originalLength = originalText.length();
        int processedLength = processedText.length();
        int reducedBytes = originalLength - processedLength;
        double reductionPercentage = originalLength > 0 ? (double) reducedBytes / originalLength * 100 : 0;

        return String.format("原始长度: %d, 处理后长度: %d, 减少: %d字符 (%.1f%%)",
                           originalLength, processedLength, reducedBytes, reductionPercentage);
    }
}
