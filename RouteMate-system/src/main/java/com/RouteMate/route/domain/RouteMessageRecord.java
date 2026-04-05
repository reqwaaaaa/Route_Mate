package com.RouteMate.route.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.RouteMate.common.annotation.Excel;
import com.RouteMate.common.core.domain.BaseEntity;

/**
 * 消息通知对象 route_message_record
 * 
 * @author RouteMate
 * @date 2026-04-01
 */
public class RouteMessageRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 消息ID */
    private Long messageId;

    /** 发送者ID（关联sys_user.user_id）（关联用户信息表） */
    @Excel(name = "发送者ID", readConverterExp = "关=联sys_user.user_id")
    private Long senderId;

    /** 发送者ID（关联sys_user.user_id）（关联用户信息表）关联字段（关联sys_user表） */
    private String userName;

    /** 接收者ID（关联sys_user.user_id，系统消息时为空）（关联用户信息表） */
    @Excel(name = "接收者ID", readConverterExp = "关=联sys_user.user_id，系统消息时为空")
    private Long receiverId;

    /** 接收者ID（关联sys_user.user_id，系统消息时为空）（关联用户信息表）关联字段（关联sys_user表） */
    private String userName1;

    /** 消息标题 */
    @Excel(name = "消息标题")
    private String messageTitle;

    /** 消息内容 */
    @Excel(name = "消息内容")
    private String messageContent;

    /** 消息类型（SYSTEM系统通知，USER用户私信，RECOMMEND推荐通知） */
    @Excel(name = "消息类型", readConverterExp = "S=YSTEM系统通知，USER用户私信，RECOMMEND推荐通知")
    private String messageType;

    /** 消息图片（逗号分隔的URL） */
    @Excel(name = "消息图片", readConverterExp = "逗=号分隔的URL")
    private String messageImages;

    /** 关联对象ID（如轨迹ID、POI ID等） */
    @Excel(name = "关联对象ID", readConverterExp = "如=轨迹ID、POI,I=D等")
    private String relatedId;

    /** 关联对象类型（TRAJECTORY POI TASK） */
    @Excel(name = "关联对象类型", readConverterExp = "T=RAJECTORY,P=OI,T=ASK")
    private String relatedType;

    /** 阅读状态（0未读 1已读） */
    @Excel(name = "阅读状态", readConverterExp = "0=未读,1=已读")
    private String readStatus;

    /** 发送时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "发送时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;

    /** 阅读时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "阅读时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date readTime;

    /** 删除标志（0存在 1发送者删除 2接收者删除 3双方删除） */
    private String delFlag;

    public void setMessageId(Long messageId) 
    {
        this.messageId = messageId;
    }

    public Long getMessageId() 
    {
        return messageId;
    }

    public void setSenderId(Long senderId) 
    {
        this.senderId = senderId;
    }

    public Long getSenderId() 
    {
        return senderId;
    }

    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }

    public void setReceiverId(Long receiverId) 
    {
        this.receiverId = receiverId;
    }

    public Long getReceiverId() 
    {
        return receiverId;
    }

    public void setUserName1(String userName1) 
    {
        this.userName1 = userName1;
    }

    public String getUserName1() 
    {
        return userName1;
    }

    public void setMessageTitle(String messageTitle) 
    {
        this.messageTitle = messageTitle;
    }

    public String getMessageTitle() 
    {
        return messageTitle;
    }

    public void setMessageContent(String messageContent) 
    {
        this.messageContent = messageContent;
    }

    public String getMessageContent() 
    {
        return messageContent;
    }

    public void setMessageType(String messageType) 
    {
        this.messageType = messageType;
    }

    public String getMessageType() 
    {
        return messageType;
    }

    public void setMessageImages(String messageImages) 
    {
        this.messageImages = messageImages;
    }

    public String getMessageImages() 
    {
        return messageImages;
    }

    public void setRelatedId(String relatedId) 
    {
        this.relatedId = relatedId;
    }

    public String getRelatedId() 
    {
        return relatedId;
    }

    public void setRelatedType(String relatedType) 
    {
        this.relatedType = relatedType;
    }

    public String getRelatedType() 
    {
        return relatedType;
    }

    public void setReadStatus(String readStatus) 
    {
        this.readStatus = readStatus;
    }

    public String getReadStatus() 
    {
        return readStatus;
    }

    public void setSendTime(Date sendTime) 
    {
        this.sendTime = sendTime;
    }

    public Date getSendTime() 
    {
        return sendTime;
    }

    public void setReadTime(Date readTime) 
    {
        this.readTime = readTime;
    }

    public Date getReadTime() 
    {
        return readTime;
    }

    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("messageId", getMessageId())
            .append("senderId", getSenderId())
            .append("receiverId", getReceiverId())
            .append("messageTitle", getMessageTitle())
            .append("messageContent", getMessageContent())
            .append("messageType", getMessageType())
            .append("messageImages", getMessageImages())
            .append("relatedId", getRelatedId())
            .append("relatedType", getRelatedType())
            .append("readStatus", getReadStatus())
            .append("sendTime", getSendTime())
            .append("readTime", getReadTime())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
