package com.RouteMate.framework.websocket;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

/**
 * WebSocket聊天服务端点
 * 
 * @author RouteMate
 */
@Component
@ServerEndpoint("/websocket/chat/{userId}")
public class ChatWebSocketServer {

    private static final Logger log = LoggerFactory.getLogger(ChatWebSocketServer.class);

    /** 静态变量，用来记录当前在线连接数 */
    private static final AtomicInteger onlineCount = new AtomicInteger(0);

    /** concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象 */
    private static final ConcurrentHashMap<String, ChatWebSocketServer> webSocketMap = new ConcurrentHashMap<>();

    /** 与某个客户端的连接会话，需要通过它来给客户端发送数据 */
    private Session session;

    /** 接收userId */
    private String userId = "";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;

        // 检查用户是否已经在线，如果在线则替换连接，不增加计数
        boolean isNewUser = !webSocketMap.containsKey(userId);
        webSocketMap.put(userId, this);

        // 只有新用户才增加在线计数
        if (isNewUser) {
            addOnlineCount();
        }

        log.info("用户连接:" + userId + ",当前在线人数为:" + getOnlineCount() + ",是否新用户:" + isNewUser);
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("用户:" + userId + ",网络异常!!!!!!");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        // 只有当前连接确实是该用户的活跃连接时才移除并减少计数
        if (userId != null && webSocketMap.containsKey(userId) && webSocketMap.get(userId) == this) {
            webSocketMap.remove(userId);
            subOnlineCount();
            log.info("用户退出:" + userId + ",当前在线人数为:" + getOnlineCount());
        } else {
            log.info("用户连接已被替换或不存在:" + userId + ",当前在线人数为:" + getOnlineCount());
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("用户消息:" + userId + ",报文:" + message);
        
        // 解析消息
        try {
            JSONObject jsonObject = JSON.parseObject(message);
            String toUserId = jsonObject.getString("toUserId");
            String messageContent = jsonObject.getString("message");
            String messageType = jsonObject.getString("messageType");
            
            if (toUserId != null && messageContent != null) {
                // 构造消息对象
                JSONObject messageObj = new JSONObject();
                messageObj.put("fromUserId", userId);
                messageObj.put("toUserId", toUserId);
                messageObj.put("message", messageContent);
                messageObj.put("messageType", messageType != null ? messageType : "text");
                messageObj.put("timestamp", System.currentTimeMillis());
                
                // 发送给指定用户
                sendToUser(toUserId, messageObj.toJSONString());
                
                // 发送确认给发送者
                JSONObject ackObj = new JSONObject();
                ackObj.put("type", "ack");
                ackObj.put("status", "sent");
                ackObj.put("message", "消息已发送");
                sendMessage(ackObj.toJSONString());
                
                // 这里可以调用业务层保存消息到数据库
                // 注意：在WebSocket中不能直接注入Spring Bean，需要通过ApplicationContext获取
                // saveChatMessage(userId, toUserId, messageContent, messageType);
            }
        } catch (Exception e) {
            log.error("消息解析失败:" + e.getMessage());
        }
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:" + this.userId + ",原因:" + error.getMessage());
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 发送自定义消息
     */
    public static void sendInfo(String message, @PathParam("userId") String userId) throws IOException {
        log.info("发送消息到:" + userId + "，报文:" + message);
        if (userId != null && webSocketMap.containsKey(userId)) {
            webSocketMap.get(userId).sendMessage(message);
        } else {
            log.error("用户" + userId + ",不在线！");
        }
    }

    /**
     * 发送消息给指定用户
     */
    public static boolean sendToUser(String userId, String message) {
        try {
            if (userId != null && webSocketMap.containsKey(userId)) {
                webSocketMap.get(userId).sendMessage(message);
                log.info("发送消息给用户:" + userId + "，消息:" + message);
                return true;
            } else {
                log.warn("用户" + userId + "不在线，消息未发送");
                // 这里可以实现离线消息存储
                return false;
            }
        } catch (IOException e) {
            log.error("发送消息给用户" + userId + "失败:" + e.getMessage());
            return false;
        }
    }

    /**
     * 广播消息
     */
    public static void sendToAll(String message) {
        for (String userId : webSocketMap.keySet()) {
            try {
                webSocketMap.get(userId).sendMessage(message);
            } catch (IOException e) {
                log.error("广播消息给用户" + userId + "失败:" + e.getMessage());
            }
        }
    }

    /**
     * 获取在线用户列表
     */
    public static String[] getOnlineUsers() {
        return webSocketMap.keySet().toArray(new String[0]);
    }

    /**
     * 检查用户是否在线
     */
    public static boolean isUserOnline(String userId) {
        return webSocketMap.containsKey(userId);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount.get();
    }

    public static synchronized void addOnlineCount() {
        onlineCount.incrementAndGet();
    }

    public static synchronized void subOnlineCount() {
        // 确保在线人数不会变为负数
        if (onlineCount.get() > 0) {
            onlineCount.decrementAndGet();
        } else {
            log.warn("在线人数已为0，无法继续减少");
        }
    }

    /**
     * 重置在线人数（基于实际连接数）
     */
    public static synchronized void resetOnlineCount() {
        int actualCount = webSocketMap.size();
        onlineCount.set(actualCount);
        log.info("重置在线人数为实际连接数:" + actualCount);
    }

    /**
     * 修复在线人数计数
     */
    public static synchronized void fixOnlineCount() {
        int actualCount = webSocketMap.size();
        int currentCount = onlineCount.get();
        if (actualCount != currentCount) {
            log.warn("在线人数不一致，实际连接数:" + actualCount + "，计数器值:" + currentCount + "，正在修复...");
            onlineCount.set(actualCount);
        }
    }
}
