package com.RouteMate.framework.ai.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * AI聊天响应对象
 * 
 * @author RouteMate
 */
public class AiChatResponse
{
    /** 响应ID */
    private String id;

    /** 对象类型 */
    private String object;

    /** 创建时间 */
    private Long created;

    /** 模型名称 */
    private String model;

    /** 选择列表 */
    private List<Choice> choices;

    /** 使用情况 */
    private Usage usage;

    /** 系统指纹 */
    @JsonProperty("system_fingerprint")
    private String systemFingerprint;

    public static class Choice
    {
        /** 索引 */
        private Integer index;

        /** 消息 */
        private AiMessage message;

        /** 增量消息（流式） */
        private AiMessage delta;

        /** 完成原因 */
        @JsonProperty("finish_reason")
        private String finishReason;

        public Integer getIndex()
        {
            return index;
        }

        public void setIndex(Integer index)
        {
            this.index = index;
        }

        public AiMessage getMessage()
        {
            return message;
        }

        public void setMessage(AiMessage message)
        {
            this.message = message;
        }

        public AiMessage getDelta()
        {
            return delta;
        }

        public void setDelta(AiMessage delta)
        {
            this.delta = delta;
        }

        public String getFinishReason()
        {
            return finishReason;
        }

        public void setFinishReason(String finishReason)
        {
            this.finishReason = finishReason;
        }

        @Override
        public String toString()
        {
            return "Choice{" +
                    "index=" + index +
                    ", message=" + message +
                    ", delta=" + delta +
                    ", finishReason='" + finishReason + '\'' +
                    '}';
        }
    }

    public static class Usage
    {
        /** 提示tokens */
        @JsonProperty("prompt_tokens")
        private Integer promptTokens;

        /** 完成tokens */
        @JsonProperty("completion_tokens")
        private Integer completionTokens;

        /** 总tokens */
        @JsonProperty("total_tokens")
        private Integer totalTokens;

        public Integer getPromptTokens()
        {
            return promptTokens;
        }

        public void setPromptTokens(Integer promptTokens)
        {
            this.promptTokens = promptTokens;
        }

        public Integer getCompletionTokens()
        {
            return completionTokens;
        }

        public void setCompletionTokens(Integer completionTokens)
        {
            this.completionTokens = completionTokens;
        }

        public Integer getTotalTokens()
        {
            return totalTokens;
        }

        public void setTotalTokens(Integer totalTokens)
        {
            this.totalTokens = totalTokens;
        }

        @Override
        public String toString()
        {
            return "Usage{" +
                    "promptTokens=" + promptTokens +
                    ", completionTokens=" + completionTokens +
                    ", totalTokens=" + totalTokens +
                    '}';
        }
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getObject()
    {
        return object;
    }

    public void setObject(String object)
    {
        this.object = object;
    }

    public Long getCreated()
    {
        return created;
    }

    public void setCreated(Long created)
    {
        this.created = created;
    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public List<Choice> getChoices()
    {
        return choices;
    }

    public void setChoices(List<Choice> choices)
    {
        this.choices = choices;
    }

    public Usage getUsage()
    {
        return usage;
    }

    public void setUsage(Usage usage)
    {
        this.usage = usage;
    }

    public String getSystemFingerprint()
    {
        return systemFingerprint;
    }

    public void setSystemFingerprint(String systemFingerprint)
    {
        this.systemFingerprint = systemFingerprint;
    }

    @Override
    public String toString()
    {
        return "AiChatResponse{" +
                "id='" + id + '\'' +
                ", object='" + object + '\'' +
                ", created=" + created +
                ", model='" + model + '\'' +
                ", choices=" + choices +
                ", usage=" + usage +
                ", systemFingerprint='" + systemFingerprint + '\'' +
                '}';
    }
}
