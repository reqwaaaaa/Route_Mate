package com.RouteMate.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.RouteMate.common.annotation.Excel;
import com.RouteMate.common.core.domain.BaseEntity;

/**
 * 聊天会话对象 chat_sessions
 * 
 * @author RouteMate
 * @date 2024-01-01
 */
public class ChatSession extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 会话ID */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private String userId;

    /** 联系人ID */
    @Excel(name = "联系人ID")
    private String contactId;

    /** 联系人名称 */
    @Excel(name = "联系人名称")
    private String contactName;

    /** 联系人头像 */
    @Excel(name = "联系人头像")
    private String contactAvatar;

    /** 最后一条消息 */
    @Excel(name = "最后一条消息")
    private String lastMessage;

    /** 最后消息类型 */
    @Excel(name = "最后消息类型")
    private String lastMessageType;

    /** 最后消息时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "最后消息时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date lastMessageTime;

    /** 未读消息数 */
    @Excel(name = "未读消息数")
    private Integer unreadCount;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    
    public void setUserId(String userId) 
    {
        this.userId = userId;
    }

    public String getUserId() 
    {
        return userId;
    }
    
    public void setContactId(String contactId) 
    {
        this.contactId = contactId;
    }

    public String getContactId() 
    {
        return contactId;
    }
    
    public void setContactName(String contactName) 
    {
        this.contactName = contactName;
    }

    public String getContactName() 
    {
        return contactName;
    }
    
    public void setContactAvatar(String contactAvatar) 
    {
        this.contactAvatar = contactAvatar;
    }

    public String getContactAvatar() 
    {
        return contactAvatar;
    }
    
    public void setLastMessage(String lastMessage) 
    {
        this.lastMessage = lastMessage;
    }

    public String getLastMessage() 
    {
        return lastMessage;
    }
    
    public void setLastMessageType(String lastMessageType) 
    {
        this.lastMessageType = lastMessageType;
    }

    public String getLastMessageType() 
    {
        return lastMessageType;
    }
    
    public void setLastMessageTime(Date lastMessageTime) 
    {
        this.lastMessageTime = lastMessageTime;
    }

    public Date getLastMessageTime() 
    {
        return lastMessageTime;
    }
    
    public void setUnreadCount(Integer unreadCount) 
    {
        this.unreadCount = unreadCount;
    }

    public Integer getUnreadCount() 
    {
        return unreadCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("contactId", getContactId())
            .append("contactName", getContactName())
            .append("contactAvatar", getContactAvatar())
            .append("lastMessage", getLastMessage())
            .append("lastMessageType", getLastMessageType())
            .append("lastMessageTime", getLastMessageTime())
            .append("unreadCount", getUnreadCount())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
