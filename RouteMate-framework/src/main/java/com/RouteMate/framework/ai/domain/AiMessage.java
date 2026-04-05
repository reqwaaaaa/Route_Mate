package com.RouteMate.framework.ai.domain;

/**
 * AI消息对象
 * 
 * @author RouteMate
 */
public class AiMessage
{
    /** 消息角色：system, user, assistant */
    private String role;

    /** 消息内容 */
    private String content;

    public AiMessage()
    {
    }

    public AiMessage(String role, String content)
    {
        this.role = role;
        this.content = content;
    }

    /**
     * 创建系统消息
     */
    public static AiMessage system(String content)
    {
        return new AiMessage("system", content);
    }

    /**
     * 创建用户消息
     */
    public static AiMessage user(String content)
    {
        return new AiMessage("user", content);
    }

    /**
     * 创建助手消息
     */
    public static AiMessage assistant(String content)
    {
        return new AiMessage("assistant", content);
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    @Override
    public String toString()
    {
        return "AiMessage{" +
                "role='" + role + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
