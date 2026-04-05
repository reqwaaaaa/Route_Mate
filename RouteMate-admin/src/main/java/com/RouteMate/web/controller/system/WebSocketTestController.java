package com.RouteMate.web.controller.system;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.RouteMate.common.core.domain.AjaxResult;
import com.RouteMate.framework.websocket.ChatWebSocketServer;

/**
 * WebSocket测试控制器
 * 
 * @author RouteMate
 */
@RestController
@RequestMapping("/test/websocket")
public class WebSocketTestController {

    /**
     * 测试WebSocket服务状态
     */
    @GetMapping("/status")
    public AjaxResult getWebSocketStatus() {
        AjaxResult result = AjaxResult.success("WebSocket服务正常");
        result.put("onlineCount", ChatWebSocketServer.getOnlineCount());
        result.put("onlineUsers", ChatWebSocketServer.getOnlineUsers());
        return result;
    }

    /**
     * 获取在线用户数量
     */
    @GetMapping("/online-count")
    public AjaxResult getOnlineCount() {
        int count = ChatWebSocketServer.getOnlineCount();
        return AjaxResult.success("获取成功", count);
    }

    /**
     * 获取在线用户列表
     */
    @GetMapping("/online-users")
    public AjaxResult getOnlineUsers() {
        String[] users = ChatWebSocketServer.getOnlineUsers();
        AjaxResult result = AjaxResult.success("获取成功");
        result.put("users", users);
        result.put("count", users.length);
        return result;
    }

    /**
     * 发送测试消息
     */
    @GetMapping("/send-test")
    public AjaxResult sendTestMessage() {
        try {
            ChatWebSocketServer.sendToAll("这是一条测试消息");
            return AjaxResult.success("测试消息发送成功");
        } catch (Exception e) {
            return AjaxResult.error("测试消息发送失败: " + e.getMessage());
        }
    }
}
