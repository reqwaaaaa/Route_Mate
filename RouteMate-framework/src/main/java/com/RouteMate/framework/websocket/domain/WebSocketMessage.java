package com.RouteMate.framework.websocket.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * WebSocket消息实体
 * 
 * @author RouteMate
 */
public class WebSocketMessage {

    /** 消息类型 */
    private String type;

    /** 发送者ID */
    private String fromUserId;

    /** 发送者名称 */
    private String fromUserName;

    /** 接收者ID */
    private String toUserId;

    /** 接收者名称 */
    private String toUserName;

    /** 消息内容 */
    private String message;

    /** 消息类型：text/image/file/system */
    private String messageType;

    /** 时间戳 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timestamp;

    /** 消息状态 */
    private String status;

    /** 额外数据 */
    private Object data;

    public WebSocketMessage() {
        this.timestamp = new Date();
    }

    public WebSocketMessage(String type, String message) {
        this();
        this.type = type;
        this.message = message;
    }

    public WebSocketMessage(String fromUserId, String toUserId, String message) {
        this();
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.message = message;
        this.type = "chat";
        this.messageType = "text";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "WebSocketMessage{" +
                "type='" + type + '\'' +
                ", fromUserId='" + fromUserId + '\'' +
                ", fromUserName='" + fromUserName + '\'' +
                ", toUserId='" + toUserId + '\'' +
                ", toUserName='" + toUserName + '\'' +
                ", message='" + message + '\'' +
                ", messageType='" + messageType + '\'' +
                ", timestamp=" + timestamp +
                ", status='" + status + '\'' +
                ", data=" + data +
                '}';
    }
}
