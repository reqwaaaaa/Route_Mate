package com.RouteMate.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * AI大模型配置
 * 
 * @author RouteMate
 */
@Component
@ConfigurationProperties(prefix = "ai.llm")
public class AiConfig
{
    /** API基础URL */
    private String apiUrl = "https://ark.cn-beijing.volces.com/api/v3/chat/completions";

    /** API密钥 */
    private String apiKey;

    /** 默认模型 */
    private String defaultModel = "deepseek-r1-250120";

    /** 连接超时时间（毫秒） */
    private int connectTimeout = 30000;

    /** 读取超时时间（毫秒） */
    private int readTimeout = 60000;

    /** 最大重试次数 */
    private int maxRetries = 3;

    /** 是否启用流式输出 */
    private boolean streamEnabled = true;

    /** 默认系统提示词 */
    private String defaultSystemPrompt = "你是人工智能助手。";

    /** 最大tokens数 */
    private int maxTokens = 4096;

    /** 温度参数 */
    private double temperature = 0.7;

    /** top_p参数 */
    private double topP = 0.9;

    /** 是否启用重试机制 */
    private boolean retryEnabled = true;

    /** 重试间隔（毫秒） */
    private int retryInterval = 1000;

    public String getApiUrl()
    {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl)
    {
        this.apiUrl = apiUrl;
    }

    public String getApiKey()
    {
        return apiKey;
    }

    public void setApiKey(String apiKey)
    {
        this.apiKey = apiKey;
    }

    public String getDefaultModel()
    {
        return defaultModel;
    }

    public void setDefaultModel(String defaultModel)
    {
        this.defaultModel = defaultModel;
    }

    public int getConnectTimeout()
    {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout)
    {
        this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout()
    {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout)
    {
        this.readTimeout = readTimeout;
    }

    public int getMaxRetries()
    {
        return maxRetries;
    }

    public void setMaxRetries(int maxRetries)
    {
        this.maxRetries = maxRetries;
    }

    public boolean isStreamEnabled()
    {
        return streamEnabled;
    }

    public void setStreamEnabled(boolean streamEnabled)
    {
        this.streamEnabled = streamEnabled;
    }

    public String getDefaultSystemPrompt()
    {
        return defaultSystemPrompt;
    }

    public void setDefaultSystemPrompt(String defaultSystemPrompt)
    {
        this.defaultSystemPrompt = defaultSystemPrompt;
    }

    public int getMaxTokens()
    {
        return maxTokens;
    }

    public void setMaxTokens(int maxTokens)
    {
        this.maxTokens = maxTokens;
    }

    public double getTemperature()
    {
        return temperature;
    }

    public void setTemperature(double temperature)
    {
        this.temperature = temperature;
    }

    public double getTopP()
    {
        return topP;
    }

    public void setTopP(double topP)
    {
        this.topP = topP;
    }

    public boolean isRetryEnabled()
    {
        return retryEnabled;
    }

    public void setRetryEnabled(boolean retryEnabled)
    {
        this.retryEnabled = retryEnabled;
    }

    public int getRetryInterval()
    {
        return retryInterval;
    }

    public void setRetryInterval(int retryInterval)
    {
        this.retryInterval = retryInterval;
    }
}
