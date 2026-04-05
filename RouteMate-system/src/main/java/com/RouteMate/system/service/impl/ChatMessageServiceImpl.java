package com.RouteMate.system.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.RouteMate.system.mapper.ChatMessageMapper;
import com.RouteMate.system.mapper.ChatSessionMapper;
import com.RouteMate.system.mapper.SysUserMapper;
import com.RouteMate.system.domain.ChatMessage;
import com.RouteMate.system.domain.ChatSession;
import com.RouteMate.system.service.IChatMessageService;
import com.RouteMate.common.core.domain.entity.SysUser;

/**
 * 聊天消息Service业务层处理
 * 
 * @author RouteMate
 * @date 2024-01-01
 */
@Service
public class ChatMessageServiceImpl implements IChatMessageService 
{
    @Autowired
    private ChatMessageMapper chatMessageMapper;
    
    @Autowired
    private ChatSessionMapper chatSessionMapper;
    
    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 查询聊天消息
     * 
     * @param id 聊天消息主键
     * @return 聊天消息
     */
    @Override
    public ChatMessage selectChatMessageById(Long id)
    {
        return chatMessageMapper.selectChatMessageById(id);
    }

    /**
     * 查询聊天消息列表
     * 
     * @param chatMessage 聊天消息
     * @return 聊天消息
     */
    @Override
    public List<ChatMessage> selectChatMessageList(ChatMessage chatMessage)
    {
        return chatMessageMapper.selectChatMessageList(chatMessage);
    }

    /**
     * 查询两个用户之间的聊天记录
     * 
     * @param userId1 用户1ID
     * @param userId2 用户2ID
     * @return 聊天消息集合
     */
    @Override
    public List<ChatMessage> selectChatMessagesBetweenUsers(String userId1, String userId2)
    {
        return chatMessageMapper.selectChatMessagesBetweenUsers(userId1, userId2);
    }

    /**
     * 查询用户的未读消息
     * 
     * @param userId 用户ID
     * @return 未读消息集合
     */
    @Override
    public List<ChatMessage> selectUnreadMessages(String userId)
    {
        return chatMessageMapper.selectUnreadMessages(userId);
    }

    /**
     * 统计用户的未读消息数量
     * 
     * @param userId 用户ID
     * @return 未读消息数量
     */
    @Override
    public int countUnreadMessages(String userId)
    {
        return chatMessageMapper.countUnreadMessages(userId);
    }

    /**
     * 发送消息（包含会话管理）
     * 
     * @param chatMessage 聊天消息
     * @return 结果
     */
    @Override
    @Transactional
    public int sendMessage(ChatMessage chatMessage)
    {
        // 设置默认值
        if (chatMessage.getTimestamp() == null) {
            chatMessage.setTimestamp(new Date());
        }
        if (chatMessage.getMessageType() == null) {
            chatMessage.setMessageType("text");
        }
        if (chatMessage.getReadStatus() == null) {
            chatMessage.setReadStatus("sent");
        }
        
        // 根据 senderId 和 receiverId 查询用户信息
        if (chatMessage.getSenderId() != null && chatMessage.getSenderName() == null) {
            SysUser sender = sysUserMapper.selectUserById(Long.parseLong(chatMessage.getSenderId()));
            if (sender != null) {
                chatMessage.setSenderName(sender.getNickName() != null ? sender.getNickName() : sender.getUserName());
            }
        }
        
        if (chatMessage.getReceiverId() != null && chatMessage.getReceiverName() == null) {
            SysUser receiver = sysUserMapper.selectUserById(Long.parseLong(chatMessage.getReceiverId()));
            if (receiver != null) {
                chatMessage.setReceiverName(receiver.getNickName() != null ? receiver.getNickName() : receiver.getUserName());
            }
        }
        
        // 插入消息
        int result = chatMessageMapper.insertChatMessage(chatMessage);
        
        if (result > 0) {
            // 更新或创建发送者的会话
            updateOrCreateSession(chatMessage.getSenderId(), chatMessage.getReceiverId(), 
                                chatMessage.getReceiverName(), chatMessage.getMessage(), chatMessage.getMessageType());
            
            // 更新或创建接收者的会话（增加未读数）
            updateOrCreateSessionWithUnread(chatMessage.getReceiverId(), chatMessage.getSenderId(), 
                                          chatMessage.getSenderName(), chatMessage.getMessage(), chatMessage.getMessageType());
        }
        
        return result;
    }

    /**
     * 更新或创建会话
     */
    private void updateOrCreateSession(String userId, String contactId, String contactName, String lastMessage, String messageType)
    {
        ChatSession session = chatSessionMapper.selectChatSessionByUserAndContact(userId, contactId);
        if (session == null) {
            // 创建新会话
            session = new ChatSession();
            session.setUserId(userId);
            session.setContactId(contactId);
            session.setContactName(contactName);
            session.setLastMessage(lastMessage);
            session.setLastMessageType(messageType);
            session.setLastMessageTime(new Date());
            session.setUnreadCount(0);
            chatSessionMapper.insertChatSession(session);
        } else {
            // 更新现有会话
            session.setLastMessage(lastMessage);
            session.setLastMessageType(messageType);
            session.setLastMessageTime(new Date());
            chatSessionMapper.updateChatSession(session);
        }
    }

    /**
     * 更新或创建会话（增加未读数）
     */
    private void updateOrCreateSessionWithUnread(String userId, String contactId, String contactName, String lastMessage, String messageType)
    {
        ChatSession session = chatSessionMapper.selectChatSessionByUserAndContact(userId, contactId);
        if (session == null) {
            // 创建新会话
            session = new ChatSession();
            session.setUserId(userId);
            session.setContactId(contactId);
            session.setContactName(contactName);
            session.setLastMessage(lastMessage);
            session.setLastMessageType(messageType);
            session.setLastMessageTime(new Date());
            session.setUnreadCount(1);
            chatSessionMapper.insertChatSession(session);
        } else {
            // 更新现有会话并增加未读数
            session.setLastMessage(lastMessage);
            session.setLastMessageType(messageType);
            session.setLastMessageTime(new Date());
            chatSessionMapper.updateChatSession(session);
            chatSessionMapper.incrementUnreadCount(userId, contactId);
        }
    }

    /**
     * 新增聊天消息
     * 
     * @param chatMessage 聊天消息
     * @return 结果
     */
    @Override
    public int insertChatMessage(ChatMessage chatMessage)
    {
        return chatMessageMapper.insertChatMessage(chatMessage);
    }

    /**
     * 修改聊天消息
     * 
     * @param chatMessage 聊天消息
     * @return 结果
     */
    @Override
    public int updateChatMessage(ChatMessage chatMessage)
    {
        return chatMessageMapper.updateChatMessage(chatMessage);
    }

    /**
     * 标记消息为已读
     * 
     * @param senderId 发送者ID
     * @param receiverId 接收者ID
     * @return 结果
     */
    @Override
    @Transactional
    public int markMessagesAsRead(String senderId, String receiverId)
    {
        // 标记消息为已读
        int result = chatMessageMapper.markMessagesAsRead(senderId, receiverId);
        
        // 清零未读消息数
        chatSessionMapper.clearUnreadCount(receiverId, senderId);
        
        return result;
    }

    /**
     * 批量删除聊天消息
     * 
     * @param ids 需要删除的聊天消息主键
     * @return 结果
     */
    @Override
    public int deleteChatMessageByIds(Long[] ids)
    {
        return chatMessageMapper.deleteChatMessageByIds(ids);
    }

    /**
     * 删除聊天消息信息
     * 
     * @param id 聊天消息主键
     * @return 结果
     */
    @Override
    public int deleteChatMessageById(Long id)
    {
        return chatMessageMapper.deleteChatMessageById(id);
    }
}
