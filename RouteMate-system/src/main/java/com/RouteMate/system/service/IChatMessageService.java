package com.RouteMate.system.service;

import java.util.List;
import com.RouteMate.system.domain.ChatMessage;

/**
 * 聊天消息Service接口
 * 
 * @author RouteMate
 * @date 2024-01-01
 */
public interface IChatMessageService 
{
    /**
     * 查询聊天消息
     * 
     * @param id 聊天消息主键
     * @return 聊天消息
     */
    public ChatMessage selectChatMessageById(Long id);

    /**
     * 查询聊天消息列表
     * 
     * @param chatMessage 聊天消息
     * @return 聊天消息集合
     */
    public List<ChatMessage> selectChatMessageList(ChatMessage chatMessage);

    /**
     * 查询两个用户之间的聊天记录
     * 
     * @param userId1 用户1ID
     * @param userId2 用户2ID
     * @return 聊天消息集合
     */
    public List<ChatMessage> selectChatMessagesBetweenUsers(String userId1, String userId2);

    /**
     * 查询用户的未读消息
     * 
     * @param userId 用户ID
     * @return 未读消息集合
     */
    public List<ChatMessage> selectUnreadMessages(String userId);

    /**
     * 统计用户的未读消息数量
     * 
     * @param userId 用户ID
     * @return 未读消息数量
     */
    public int countUnreadMessages(String userId);

    /**
     * 发送消息
     * 
     * @param chatMessage 聊天消息
     * @return 结果
     */
    public int sendMessage(ChatMessage chatMessage);

    /**
     * 新增聊天消息
     * 
     * @param chatMessage 聊天消息
     * @return 结果
     */
    public int insertChatMessage(ChatMessage chatMessage);

    /**
     * 修改聊天消息
     * 
     * @param chatMessage 聊天消息
     * @return 结果
     */
    public int updateChatMessage(ChatMessage chatMessage);

    /**
     * 标记消息为已读
     * 
     * @param senderId 发送者ID
     * @param receiverId 接收者ID
     * @return 结果
     */
    public int markMessagesAsRead(String senderId, String receiverId);

    /**
     * 批量删除聊天消息
     * 
     * @param ids 需要删除的聊天消息主键集合
     * @return 结果
     */
    public int deleteChatMessageByIds(Long[] ids);

    /**
     * 删除聊天消息信息
     * 
     * @param id 聊天消息主键
     * @return 结果
     */
    public int deleteChatMessageById(Long id);
}
