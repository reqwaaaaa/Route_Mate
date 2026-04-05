package com.RouteMate.framework.websocket.service;

import com.alibaba.fastjson2.JSON;
import com.RouteMate.framework.websocket.ChatWebSocketServer;
import com.RouteMate.framework.websocket.domain.WebSocketMessage;
import com.RouteMate.system.domain.ChatMessage;
import com.RouteMate.system.service.IChatMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * WebSocket服务类
 * 
 * @author RouteMate
 */
@Service
public class WebSocketService {

    private static final Logger log = LoggerFactory.getLogger(WebSocketService.class);

    @Autowired
    private IChatMessageService chatMessageService;

    /**
     * 发送消息给指定用户（包含数据库保存）
     * @deprecated 建议使用 sendWebSocketMessage 方法，由控制器统一处理数据库保存
     */
    public boolean sendMessageToUser(String fromUserId, String fromUserName, String toUserId, String toUserName, String message, String messageType) {
        try {
            // 构造WebSocket消息
            WebSocketMessage wsMessage = new WebSocketMessage();
            wsMessage.setType("chat");
            wsMessage.setFromUserId(fromUserId);
            wsMessage.setFromUserName(fromUserName);
            wsMessage.setToUserId(toUserId);
            wsMessage.setToUserName(toUserName);
            wsMessage.setMessage(message);
            wsMessage.setMessageType(messageType != null ? messageType : "text");
            wsMessage.setTimestamp(new Date());
            wsMessage.setStatus("sent");

            // 保存消息到数据库
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setSenderId(fromUserId);
            chatMessage.setSenderName(fromUserName);
            chatMessage.setReceiverId(toUserId);
            chatMessage.setReceiverName(toUserName);
            chatMessage.setMessage(message);
            chatMessage.setMessageType(messageType != null ? messageType : "text");
            chatMessage.setTimestamp(new Date());
            chatMessage.setReadStatus("sent");

            int result = chatMessageService.sendMessage(chatMessage);
            if (result > 0) {
                // 发送WebSocket消息
                String jsonMessage = JSON.toJSONString(wsMessage);
                boolean sendSuccess = ChatWebSocketServer.sendToUser(toUserId, jsonMessage);

                // 发送确认给发送者
                WebSocketMessage ackMessage = new WebSocketMessage();
                ackMessage.setType("ack");
                ackMessage.setStatus("delivered");
                ackMessage.setMessage("消息已送达");
                ackMessage.setData(chatMessage.getId());
                ChatWebSocketServer.sendToUser(fromUserId, JSON.toJSONString(ackMessage));

                log.info("消息发送成功: {} -> {}", fromUserId, toUserId);
                return true;
            }
        } catch (Exception e) {
            log.error("发送消息失败: " + e.getMessage(), e);
        }
        return false;
    }

    /**
     * 仅发送WebSocket消息（不保存数据库）
     */
    public boolean sendWebSocketMessage(String fromUserId, String fromUserName, String toUserId, String toUserName, String message, String messageType) {
        try {
            // 检查接收者是否在线
            if (!ChatWebSocketServer.isUserOnline(toUserId)) {
                log.warn("用户 {} 不在线，消息将保存但不能实时送达", toUserId);
                return true; // 返回true表示消息已处理（虽然用户不在线）
            }

            // 构建WebSocket消息
            WebSocketMessage wsMessage = new WebSocketMessage();
            wsMessage.setType("chat");
            wsMessage.setFromUserId(fromUserId);
            wsMessage.setFromUserName(fromUserName);
            wsMessage.setToUserId(toUserId);
            wsMessage.setToUserName(toUserName);
            wsMessage.setMessage(message);
            wsMessage.setMessageType(messageType != null ? messageType : "text");
            wsMessage.setTimestamp(new Date());
            wsMessage.setStatus("sent");

            // 发送WebSocket消息给接收者
            String jsonMessage = JSON.toJSONString(wsMessage);
            boolean sendResult = ChatWebSocketServer.sendToUser(toUserId, jsonMessage);

            if (sendResult) {
                // 发送确认给发送者
                WebSocketMessage ackMessage = new WebSocketMessage();
                ackMessage.setType("ack");
                ackMessage.setStatus("delivered");
                ackMessage.setMessage("消息已送达");
                ackMessage.setTimestamp(new Date());
                ChatWebSocketServer.sendToUser(fromUserId, JSON.toJSONString(ackMessage));

                log.info("WebSocket消息发送成功: {} -> {}", fromUserId, toUserId);
                return true;
            } else {
                log.warn("WebSocket消息发送失败: {} -> {}", fromUserId, toUserId);
                return false;
            }
        } catch (Exception e) {
            log.error("发送WebSocket消息异常: " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * 发送系统消息
     */
    public void sendSystemMessage(String userId, String message) {
        try {
            WebSocketMessage wsMessage = new WebSocketMessage();
            wsMessage.setType("system");
            wsMessage.setMessage(message);
            wsMessage.setTimestamp(new Date());
            
            String jsonMessage = JSON.toJSONString(wsMessage);
            ChatWebSocketServer.sendToUser(userId, jsonMessage);
            
            log.info("系统消息发送成功: {}", userId);
        } catch (Exception e) {
            log.error("发送系统消息失败: " + e.getMessage(), e);
        }
    }

    /**
     * 广播消息
     */
    public void broadcastMessage(String message, String messageType) {
        try {
            WebSocketMessage wsMessage = new WebSocketMessage();
            wsMessage.setType("broadcast");
            wsMessage.setMessage(message);
            wsMessage.setMessageType(messageType != null ? messageType : "text");
            wsMessage.setTimestamp(new Date());
            
            String jsonMessage = JSON.toJSONString(wsMessage);
            ChatWebSocketServer.sendToAll(jsonMessage);
            
            log.info("广播消息发送成功");
        } catch (Exception e) {
            log.error("发送广播消息失败: " + e.getMessage(), e);
        }
    }

    /**
     * 获取在线用户列表
     */
    public String[] getOnlineUsers() {
        return ChatWebSocketServer.getOnlineUsers();
    }

    /**
     * 获取在线用户数量
     */
    public int getOnlineUserCount() {
        return ChatWebSocketServer.getOnlineCount();
    }

    /**
     * 检查用户是否在线
     */
    public boolean isUserOnline(String userId) {
        return ChatWebSocketServer.isUserOnline(userId);
    }

    /**
     * 发送用户状态变更通知
     */
    public void notifyUserStatusChange(String userId, String status) {
        try {
            WebSocketMessage wsMessage = new WebSocketMessage();
            wsMessage.setType("user_status");
            wsMessage.setFromUserId(userId);
            wsMessage.setStatus(status);
            wsMessage.setTimestamp(new Date());
            
            String jsonMessage = JSON.toJSONString(wsMessage);
            // 通知所有在线用户
            ChatWebSocketServer.sendToAll(jsonMessage);
            
            log.info("用户状态变更通知发送成功: {} - {}", userId, status);
        } catch (Exception e) {
            log.error("发送用户状态变更通知失败: " + e.getMessage(), e);
        }
    }

    /**
     * 发送消息已读回执
     */
    public void sendReadReceipt(String fromUserId, String toUserId, Long messageId) {
        try {
            WebSocketMessage wsMessage = new WebSocketMessage();
            wsMessage.setType("read_receipt");
            wsMessage.setFromUserId(fromUserId);
            wsMessage.setToUserId(toUserId);
            wsMessage.setStatus("read");
            wsMessage.setData(messageId);
            wsMessage.setTimestamp(new Date());

            String jsonMessage = JSON.toJSONString(wsMessage);
            ChatWebSocketServer.sendToUser(toUserId, jsonMessage);

            log.info("已读回执发送成功: {} -> {}", fromUserId, toUserId);
        } catch (Exception e) {
            log.error("发送已读回执失败: " + e.getMessage(), e);
        }
    }

    /**
     * 修复在线人数计数
     */
    public void fixOnlineCount() {
        ChatWebSocketServer.fixOnlineCount();
    }

    /**
     * 重置在线人数计数
     */
    public void resetOnlineCount() {
        ChatWebSocketServer.resetOnlineCount();
    }

    /**
     * 获取详细的在线统计信息
     */
    public OnlineStatistics getOnlineStatistics() {
        String[] users = ChatWebSocketServer.getOnlineUsers();
        int count = ChatWebSocketServer.getOnlineCount();
        int actualCount = users.length;

        OnlineStatistics stats = new OnlineStatistics();
        stats.setOnlineUsers(users);
        stats.setCounterValue(count);
        stats.setActualCount(actualCount);
        stats.setConsistent(count == actualCount);

        return stats;
    }

    /**
     * 在线统计信息类
     */
    public static class OnlineStatistics {
        private String[] onlineUsers;
        private int counterValue;
        private int actualCount;
        private boolean consistent;

        // Getters and Setters
        public String[] getOnlineUsers() { return onlineUsers; }
        public void setOnlineUsers(String[] onlineUsers) { this.onlineUsers = onlineUsers; }
        public int getCounterValue() { return counterValue; }
        public void setCounterValue(int counterValue) { this.counterValue = counterValue; }
        public int getActualCount() { return actualCount; }
        public void setActualCount(int actualCount) { this.actualCount = actualCount; }
        public boolean isConsistent() { return consistent; }
        public void setConsistent(boolean consistent) { this.consistent = consistent; }
    }
}
