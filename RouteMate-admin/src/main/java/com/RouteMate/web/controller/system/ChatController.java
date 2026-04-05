package com.RouteMate.web.controller.system;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.RouteMate.common.annotation.Log;
import com.RouteMate.common.core.controller.BaseController;
import com.RouteMate.common.core.domain.AjaxResult;
import com.RouteMate.common.enums.BusinessType;
import com.RouteMate.system.domain.ChatMessage;
import com.RouteMate.system.domain.ChatSession;
import com.RouteMate.system.service.IChatMessageService;
import com.RouteMate.system.service.IChatSessionService;
import com.RouteMate.common.utils.poi.ExcelUtil;
import com.RouteMate.common.core.page.TableDataInfo;
import com.RouteMate.framework.websocket.service.WebSocketService;

/**
 * 即时通讯Controller
 *
 * @author RouteMate
 * @date 2024-01-01
 */
@RestController
@RequestMapping("/system/chat")
public class ChatController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private IChatMessageService chatMessageService;

    @Autowired
    private IChatSessionService chatSessionService;

    @Autowired
    private WebSocketService webSocketService;

    /**
     * 查询聊天消息列表
     */
    @PreAuthorize("@ss.hasPermi('system:chat:list')")
    @GetMapping("/messages")
    public TableDataInfo listMessages(ChatMessage chatMessage)
    {
        startPage();
        List<ChatMessage> list = chatMessageService.selectChatMessageList(chatMessage);
        return getDataTable(list);
    }

    /**
     * 查询两个用户之间的聊天记录
     */
    @GetMapping("/messages/{userId1}/{userId2}")
    public AjaxResult getChatHistory(@PathVariable("userId1") String userId1, @PathVariable("userId2") String userId2)
    {
        List<ChatMessage> messages = chatMessageService.selectChatMessagesBetweenUsers(userId1, userId2);
        return success(messages);
    }

    /**
     * 查询用户的未读消息
     */
    @GetMapping("/unread/{userId}")
    public AjaxResult getUnreadMessages(@PathVariable("userId") String userId)
    {
        List<ChatMessage> messages = chatMessageService.selectUnreadMessages(userId);
        int count = chatMessageService.countUnreadMessages(userId);

        AjaxResult result = success();
        result.put("messages", messages);
        result.put("count", count);
        return result;
    }

    /**
     * 发送消息
     */
    @Log(title = "发送消息", businessType = BusinessType.INSERT)
    @PostMapping("/send")
    public AjaxResult sendMessage(@RequestBody ChatMessage chatMessage)
    {
        return toAjax(chatMessageService.sendMessage(chatMessage));
    }

    /**
     * 标记消息为已读
     */
    @Log(title = "标记消息已读", businessType = BusinessType.UPDATE)
    @PutMapping("/read/{senderId}/{receiverId}")
    public AjaxResult markAsRead(@PathVariable("senderId") String senderId, @PathVariable("receiverId") String receiverId)
    {
        int rows = chatMessageService.markMessagesAsRead(senderId, receiverId);
        // 即使没有消息需要标记（rows=0），也返回成功
        return success("标记已读成功");
    }

    /**
     * 获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:chat:query')")
    @GetMapping("/messages/{id}")
    public AjaxResult getMessageInfo(@PathVariable("id") Long id)
    {
        return success(chatMessageService.selectChatMessageById(id));
    }

    /**
     * 新增聊天消息
     */
    @PreAuthorize("@ss.hasPermi('system:chat:add')")
    @Log(title = "聊天消息", businessType = BusinessType.INSERT)
    @PostMapping("/messages")
    public AjaxResult addMessage(@RequestBody ChatMessage chatMessage)
    {
        return toAjax(chatMessageService.insertChatMessage(chatMessage));
    }

    /**
     * 修改聊天消息
     */
    @PreAuthorize("@ss.hasPermi('system:chat:edit')")
    @Log(title = "聊天消息", businessType = BusinessType.UPDATE)
    @PutMapping("/messages")
    public AjaxResult editMessage(@RequestBody ChatMessage chatMessage)
    {
        return toAjax(chatMessageService.updateChatMessage(chatMessage));
    }

    /**
     * 删除聊天消息
     */
    @PreAuthorize("@ss.hasPermi('system:chat:remove')")
    @Log(title = "聊天消息", businessType = BusinessType.DELETE)
	@DeleteMapping("/messages/{ids}")
    public AjaxResult removeMessages(@PathVariable Long[] ids)
    {
        return toAjax(chatMessageService.deleteChatMessageByIds(ids));
    }

    // ==================== 会话相关接口 ====================

    /**
     * 查询用户的聊天会话列表
     */
    @GetMapping("/sessions/{userId}")
    public AjaxResult getUserSessions(@PathVariable("userId") String userId)
    {
        List<ChatSession> sessions = chatSessionService.selectChatSessionsByUserId(userId);
        return success(sessions);
    }

    /**
     * 查询聊天会话列表
     */
    @PreAuthorize("@ss.hasPermi('system:chat:list')")
    @GetMapping("/sessions")
    public TableDataInfo listSessions(ChatSession chatSession)
    {
        startPage();
        List<ChatSession> list = chatSessionService.selectChatSessionList(chatSession);
        return getDataTable(list);
    }

    /**
     * 获取会话详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:chat:query')")
    @GetMapping("/sessions/info/{id}")
    public AjaxResult getSessionInfo(@PathVariable("id") Long id)
    {
        return success(chatSessionService.selectChatSessionById(id));
    }

    /**
     * 清零未读消息数
     */
    @PutMapping("/sessions/clear-unread/{userId}/{contactId}")
    public AjaxResult clearUnreadCount(@PathVariable("userId") String userId, @PathVariable("contactId") String contactId)
    {
        int rows = chatSessionService.clearUnreadCount(userId, contactId);
        // 即使未读数已经是0（rows=0），也返回成功
        return success("清零未读消息数成功");
    }

    /**
     * 新增聊天会话
     */
    @PreAuthorize("@ss.hasPermi('system:chat:add')")
    @Log(title = "聊天会话", businessType = BusinessType.INSERT)
    @PostMapping("/sessions")
    public AjaxResult addSession(@RequestBody ChatSession chatSession)
    {
        return toAjax(chatSessionService.insertChatSession(chatSession));
    }

    /**
     * 修改聊天会话
     */
    @PreAuthorize("@ss.hasPermi('system:chat:edit')")
    @Log(title = "聊天会话", businessType = BusinessType.UPDATE)
    @PutMapping("/sessions")
    public AjaxResult editSession(@RequestBody ChatSession chatSession)
    {
        return toAjax(chatSessionService.updateChatSession(chatSession));
    }

    /**
     * 删除聊天会话
     */
    @PreAuthorize("@ss.hasPermi('system:chat:remove')")
    @Log(title = "聊天会话", businessType = BusinessType.DELETE)
	@DeleteMapping("/sessions/{ids}")
    public AjaxResult removeSessions(@PathVariable Long[] ids)
    {
        return toAjax(chatSessionService.deleteChatSessionByIds(ids));
    }

    /**
     * 导出聊天消息列表
     */
    @PreAuthorize("@ss.hasPermi('system:chat:export')")
    @Log(title = "聊天消息", businessType = BusinessType.EXPORT)
    @PostMapping("/messages/export")
    public void exportMessages(HttpServletResponse response, ChatMessage chatMessage)
    {
        List<ChatMessage> list = chatMessageService.selectChatMessageList(chatMessage);
        ExcelUtil<ChatMessage> util = new ExcelUtil<ChatMessage>(ChatMessage.class);
        util.exportExcel(response, list, "聊天消息数据");
    }

    // ==================== WebSocket相关接口 ====================

    /**
     * 通过WebSocket发送消息
     */
    @Log(title = "WebSocket发送消息", businessType = BusinessType.INSERT)
    @PostMapping("/websocket/send")
    public AjaxResult sendWebSocketMessage(@RequestBody ChatMessage chatMessage)
    {
        try {
            // 1. 先保存消息到数据库
            chatMessage.setTimestamp(new Date());
            chatMessage.setReadStatus("sent"); // 设置为已发送状态（临时使用现有枚举值）
            int result = chatMessageService.insertChatMessage(chatMessage);

            if (result <= 0) {
                return error("消息保存失败");
            }

            // 2. 通过WebSocket发送消息（仅发送，不保存数据库）
            boolean success = webSocketService.sendWebSocketMessage(
                chatMessage.getSenderId(),
                chatMessage.getSenderName(),
                chatMessage.getReceiverId(),
                chatMessage.getReceiverName(),
                chatMessage.getMessage(),
                chatMessage.getMessageType()
            );

            // 3. 根据发送结果更新消息状态
            if (success) {
                chatMessage.setReadStatus("sent");
                chatMessageService.updateChatMessage(chatMessage);

                // 4. 更新或创建聊天会话
                updateChatSession(chatMessage);

                AjaxResult ajaxResult = success();
                ajaxResult.put("messageId", chatMessage.getId());
                ajaxResult.put("timestamp", chatMessage.getTimestamp());
                return ajaxResult;
            } else {
                // 发送失败，保持sent状态（因为数据库中没有failed枚举值）
                // 可以通过其他字段或日志记录发送失败信息
                log.warn("WebSocket消息发送失败，但消息已保存到数据库: messageId={}", chatMessage.getId());
                return error("WebSocket消息发送失败，但消息已保存");
            }
        } catch (Exception e) {
            log.error("发送WebSocket消息异常", e);
            return error("发送消息时发生异常: " + e.getMessage());
        }
    }

    /**
     * 获取在线用户列表
     */
    @GetMapping("/websocket/online-users")
    public AjaxResult getOnlineUsers()
    {
        String[] onlineUsers = webSocketService.getOnlineUsers();
        AjaxResult result = success();
        result.put("users", onlineUsers);
        result.put("count", onlineUsers.length);
        return result;
    }

    /**
     * 获取在线用户数量
     */
    @GetMapping("/websocket/online-count")
    public AjaxResult getOnlineUserCount()
    {
        int count = webSocketService.getOnlineUserCount();
        return success(count);
    }

    /**
     * 检查用户是否在线
     */
    @GetMapping("/websocket/user-online/{userId}")
    public AjaxResult isUserOnline(@PathVariable("userId") String userId)
    {
        boolean online = webSocketService.isUserOnline(userId);
        AjaxResult result = success();
        result.put("userId", userId);
        result.put("online", online);
        return result;
    }

    /**
     * 发送系统消息
     */
    @PreAuthorize("@ss.hasPermi('system:chat:add')")
    @Log(title = "发送系统消息", businessType = BusinessType.INSERT)
    @PostMapping("/websocket/system-message")
    public AjaxResult sendSystemMessage(@RequestBody SystemMessageRequest request)
    {
        if (request.getUserId() != null) {
            // 发送给指定用户
            webSocketService.sendSystemMessage(request.getUserId(), request.getMessage());
        } else {
            // 广播给所有用户
            webSocketService.broadcastMessage(request.getMessage(), "system");
        }
        return success("系统消息发送成功");
    }

    /**
     * 发送广播消息
     */
    @PreAuthorize("@ss.hasPermi('system:chat:add')")
    @Log(title = "发送广播消息", businessType = BusinessType.INSERT)
    @PostMapping("/websocket/broadcast")
    public AjaxResult broadcastMessage(@RequestBody BroadcastMessageRequest request)
    {
        webSocketService.broadcastMessage(request.getMessage(), request.getMessageType());
        return success("广播消息发送成功");
    }

    /**
     * 发送已读回执
     */
    @PostMapping("/websocket/read-receipt")
    public AjaxResult sendReadReceipt(@RequestBody ReadReceiptRequest request)
    {
        webSocketService.sendReadReceipt(request.getFromUserId(), request.getToUserId(), request.getMessageId());
        return success("已读回执发送成功");
    }

    /**
     * WebSocket连接测试
     */
    @GetMapping("/websocket/test")
    public AjaxResult testWebSocket()
    {
        AjaxResult result = success("WebSocket服务正常");
        result.put("onlineCount", webSocketService.getOnlineUserCount());
        result.put("onlineUsers", webSocketService.getOnlineUsers());
        return result;
    }

    /**
     * 修复在线人数计数
     */
    @PreAuthorize("@ss.hasPermi('system:chat:edit')")
    @PostMapping("/websocket/fix-count")
    public AjaxResult fixOnlineCount()
    {
        webSocketService.fixOnlineCount();
        return success("在线人数计数已修复");
    }

    /**
     * 重置在线人数计数
     */
    @PreAuthorize("@ss.hasPermi('system:chat:edit')")
    @PostMapping("/websocket/reset-count")
    public AjaxResult resetOnlineCount()
    {
        webSocketService.resetOnlineCount();
        return success("在线人数计数已重置");
    }

    /**
     * 获取详细的在线统计信息
     */
    @GetMapping("/websocket/statistics")
    public AjaxResult getOnlineStatistics()
    {
        WebSocketService.OnlineStatistics stats = webSocketService.getOnlineStatistics();
        AjaxResult result = success();
        result.put("statistics", stats);
        return result;
    }

    /**
     * 测试消息发送和保存功能
     */
    @PostMapping("/websocket/test-send")
    public AjaxResult testSendMessage(@RequestBody ChatMessage chatMessage)
    {
        try {
            // 验证必要参数
            if (chatMessage.getSenderId() == null || chatMessage.getReceiverId() == null ||
                chatMessage.getMessage() == null || chatMessage.getMessage().trim().isEmpty()) {
                return error("发送者ID、接收者ID和消息内容不能为空");
            }

            // 设置默认值
            if (chatMessage.getSenderName() == null) {
                chatMessage.setSenderName("测试发送者");
            }
            if (chatMessage.getReceiverName() == null) {
                chatMessage.setReceiverName("测试接收者");
            }
            if (chatMessage.getMessageType() == null) {
                chatMessage.setMessageType("text");
            }

            // 调用发送消息接口
            return sendWebSocketMessage(chatMessage);
        } catch (Exception e) {
            log.error("测试发送消息失败", e);
            return error("测试发送消息失败: " + e.getMessage());
        }
    }

    // ==================== 内部类 ====================

    /**
     * 系统消息请求对象
     */
    public static class SystemMessageRequest
    {
        private String userId;
        private String message;

        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }

    /**
     * 广播消息请求对象
     */
    public static class BroadcastMessageRequest
    {
        private String message;
        private String messageType;

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public String getMessageType() { return messageType; }
        public void setMessageType(String messageType) { this.messageType = messageType; }
    }

    /**
     * 已读回执请求对象
     */
    public static class ReadReceiptRequest
    {
        private String fromUserId;
        private String toUserId;
        private Long messageId;

        public String getFromUserId() { return fromUserId; }
        public void setFromUserId(String fromUserId) { this.fromUserId = fromUserId; }
        public String getToUserId() { return toUserId; }
        public void setToUserId(String toUserId) { this.toUserId = toUserId; }
        public Long getMessageId() { return messageId; }
        public void setMessageId(Long messageId) { this.messageId = messageId; }
    }

    // ==================== 私有辅助方法 ====================

    /**
     * 更新或创建聊天会话
     */
    private void updateChatSession(ChatMessage chatMessage) {
        try {
            // 更新发送者的会话
            updateUserSession(
                chatMessage.getSenderId(),
                chatMessage.getReceiverId(),
                chatMessage.getReceiverName(),
                chatMessage.getMessage(),
                chatMessage.getTimestamp()
            );

            // 更新接收者的会话（增加未读计数）
            updateUserSession(
                chatMessage.getReceiverId(),
                chatMessage.getSenderId(),
                chatMessage.getSenderName(),
                chatMessage.getMessage(),
                chatMessage.getTimestamp()
            );
        } catch (Exception e) {
            log.error("更新聊天会话失败", e);
        }
    }

    /**
     * 更新用户的聊天会话
     */
    private void updateUserSession(String userId, String contactId, String contactName,
                                 String lastMessage, Date lastMessageTime) {
        try {
            // 查询是否存在会话
            ChatSession existingSession = chatSessionService.selectChatSessionByUserAndContact(userId, contactId);

            if (existingSession != null) {
                // 更新现有会话
                existingSession.setLastMessage(lastMessage);
                existingSession.setLastMessageTime(lastMessageTime);

                // 如果是接收者的会话，增加未读计数
                if (userId.equals(contactId)) {
                    existingSession.setUnreadCount(existingSession.getUnreadCount() + 1);
                }

                chatSessionService.updateChatSession(existingSession);
            } else {
                // 创建新会话
                ChatSession newSession = new ChatSession();
                newSession.setUserId(userId);
                newSession.setContactId(contactId);
                newSession.setContactName(contactName);
                newSession.setLastMessage(lastMessage);
                newSession.setLastMessageTime(lastMessageTime);
                // 发送者的会话未读为0，接收者的会话未读为1
                if (userId.equals(contactId)) {
                    newSession.setUnreadCount(1); // 接收者会话，未读+1
                } else {
                    newSession.setUnreadCount(0); // 发送者会话，未读为0
                }
                newSession.setLastMessageTime(new Date());

                chatSessionService.insertChatSession(newSession);
            }
        } catch (Exception e) {
            log.error("更新用户会话失败: userId={}, contactId={}", userId, contactId, e);
        }
    }
}
