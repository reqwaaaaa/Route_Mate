package com.RouteMate.framework.websocket.task;

import com.RouteMate.framework.websocket.ChatWebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * WebSocket在线人数检查任务
 * 
 * @author RouteMate
 */
@Component
public class WebSocketCountTask {

    private static final Logger log = LoggerFactory.getLogger(WebSocketCountTask.class);

    /**
     * 每5分钟检查一次在线人数是否正确
     */
    @Scheduled(fixedRate = 5 * 60 * 1000) // 5分钟
    public void checkOnlineCount() {
        try {
            ChatWebSocketServer.fixOnlineCount();
            log.debug("在线人数检查完成，当前在线人数: {}", ChatWebSocketServer.getOnlineCount());
        } catch (Exception e) {
            log.error("检查在线人数时发生错误", e);
        }
    }

    /**
     * 每小时清理无效连接
     */
    @Scheduled(fixedRate = 60 * 60 * 1000) // 1小时
    public void cleanInvalidConnections() {
        try {
            String[] onlineUsers = ChatWebSocketServer.getOnlineUsers();
            log.info("当前在线用户: {}, 总数: {}", String.join(",", onlineUsers), onlineUsers.length);
            
            // 这里可以添加清理无效连接的逻辑
            // 例如：检查连接是否还活跃，移除无效连接等
            
        } catch (Exception e) {
            log.error("清理无效连接时发生错误", e);
        }
    }
}
