package com.RouteMate.framework.ai.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * AI聊天请求对象
 * 
 * @author RouteMate
 */
public class AiChatRequest
{
    /** 模型名称 */
    private String model;

    /** 消息列表 */
    private List<AiMessage> messages;

    /** 是否流式输出 */
    private Boolean stream;

    /** 最大tokens数 */
    @JsonProperty("max_tokens")
    private Integer maxTokens;

    /** 温度参数 */
    private Double temperature;

    /** top_p参数 */
    @JsonProperty("top_p")
    private Double topP;

    /** 频率惩罚 */
    @JsonProperty("frequency_penalty")
    private Double frequencyPenalty;

    /** 存在惩罚 */
    @JsonProperty("presence_penalty")
    private Double presencePenalty;

    public AiChatRequest()
    {
    }

    public AiChatRequest(String model, List<AiMessage> messages)
    {
        this.model = model;
        this.messages = messages;
    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public List<AiMessage> getMessages()
    {
        return messages;
    }

    public void setMessages(List<AiMessage> messages)
    {
        this.messages = messages;
    }

    public Boolean getStream()
    {
        return stream;
    }

    public void setStream(Boolean stream)
    {
        this.stream = stream;
    }

    public Integer getMaxTokens()
    {
        return maxTokens;
    }

    public void setMaxTokens(Integer maxTokens)
    {
        this.maxTokens = maxTokens;
    }

    public Double getTemperature()
    {
        return temperature;
    }

    public void setTemperature(Double temperature)
    {
        this.temperature = temperature;
    }

    public Double getTopP()
    {
        return topP;
    }

    public void setTopP(Double topP)
    {
        this.topP = topP;
    }

    public Double getFrequencyPenalty()
    {
        return frequencyPenalty;
    }

    public void setFrequencyPenalty(Double frequencyPenalty)
    {
        this.frequencyPenalty = frequencyPenalty;
    }

    public Double getPresencePenalty()
    {
        return presencePenalty;
    }

    public void setPresencePenalty(Double presencePenalty)
    {
        this.presencePenalty = presencePenalty;
    }

    @Override
    public String toString()
    {
        return "AiChatRequest{" +
                "model='" + model + '\'' +
                ", messages=" + messages +
                ", stream=" + stream +
                ", maxTokens=" + maxTokens +
                ", temperature=" + temperature +
                ", topP=" + topP +
                ", frequencyPenalty=" + frequencyPenalty +
                ", presencePenalty=" + presencePenalty +
                '}';
    }
}
