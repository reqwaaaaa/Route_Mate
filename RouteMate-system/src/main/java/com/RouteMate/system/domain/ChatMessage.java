package com.RouteMate.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.RouteMate.common.annotation.Excel;
import com.RouteMate.common.core.domain.BaseEntity;

/**
 * 聊天消息对象 chat_messages
 * 
 * @author RouteMate
 * @date 2024-01-01
 */
public class ChatMessage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 消息ID */
    private Long id;

    /** 发送者ID */
    @Excel(name = "发送者ID")
    private String senderId;

    /** 发送者名称 */
    @Excel(name = "发送者名称")
    private String senderName;

    /** 接收者ID */
    @Excel(name = "接收者ID")
    private String receiverId;

    /** 接收者名称 */
    @Excel(name = "接收者名称")
    private String receiverName;

    /** 发送者头像 */
    private String senderAvatar;

    /** 接收者头像 */
    private String receiverAvatar;

    /** 消息内容 */
    @Excel(name = "消息内容")
    private String message;

    /** 消息类型 */
    @Excel(name = "消息类型")
    private String messageType;

    /** 发送时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "发送时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date timestamp;

    /** 消息状态 */
    @Excel(name = "消息状态")
    private String readStatus;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    
    public void setSenderId(String senderId) 
    {
        this.senderId = senderId;
    }

    public String getSenderId() 
    {
        return senderId;
    }
    
    public void setSenderName(String senderName) 
    {
        this.senderName = senderName;
    }

    public String getSenderName() 
    {
        return senderName;
    }
    
    public void setReceiverId(String receiverId) 
    {
        this.receiverId = receiverId;
    }

    public String getReceiverId() 
    {
        return receiverId;
    }
    
    public void setReceiverName(String receiverName) 
    {
        this.receiverName = receiverName;
    }

    public String getReceiverName() 
    {
        return receiverName;
    }
    
    public void setSenderAvatar(String senderAvatar) 
    {
        this.senderAvatar = senderAvatar;
    }

    public String getSenderAvatar() 
    {
        return senderAvatar;
    }
    
    public void setReceiverAvatar(String receiverAvatar) 
    {
        this.receiverAvatar = receiverAvatar;
    }

    public String getReceiverAvatar() 
    {
        return receiverAvatar;
    }
    
    public void setMessage(String message) 
    {
        this.message = message;
    }

    public String getMessage() 
    {
        return message;
    }
    
    public void setMessageType(String messageType) 
    {
        this.messageType = messageType;
    }

    public String getMessageType() 
    {
        return messageType;
    }
    
    public void setTimestamp(Date timestamp) 
    {
        this.timestamp = timestamp;
    }

    public Date getTimestamp() 
    {
        return timestamp;
    }
    
    public void setReadStatus(String readStatus) 
    {
        this.readStatus = readStatus;
    }

    public String getReadStatus() 
    {
        return readStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("senderId", getSenderId())
            .append("senderName", getSenderName())
            .append("senderAvatar", getSenderAvatar())
            .append("receiverId", getReceiverId())
            .append("receiverName", getReceiverName())
            .append("receiverAvatar", getReceiverAvatar())
            .append("message", getMessage())
            .append("messageType", getMessageType())
            .append("timestamp", getTimestamp())
            .append("readStatus", getReadStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
