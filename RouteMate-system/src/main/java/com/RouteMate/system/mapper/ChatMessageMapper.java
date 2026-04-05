package com.RouteMate.system.mapper;

import java.util.List;
import com.RouteMate.system.domain.ChatMessage;
import org.apache.ibatis.annotations.Param;

/**
 * 聊天消息Mapper接口
 * 
 * @author RouteMate
 * @date 2024-01-01
 */
public interface ChatMessageMapper 
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
    public List<ChatMessage> selectChatMessagesBetweenUsers(@Param("userId1") String userId1, @Param("userId2") String userId2);

    /**
     * 查询用户的未读消息
     * 
     * @param userId 用户ID
     * @return 未读消息集合
     */
    public List<ChatMessage> selectUnreadMessages(@Param("userId") String userId);

    /**
     * 统计用户的未读消息数量
     * 
     * @param userId 用户ID
     * @return 未读消息数量
     */
    public int countUnreadMessages(@Param("userId") String userId);

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
     * 批量标记消息为已读
     * 
     * @param senderId 发送者ID
     * @param receiverId 接收者ID
     * @return 结果
     */
    public int markMessagesAsRead(@Param("senderId") String senderId, @Param("receiverId") String receiverId);

    /**
     * 删除聊天消息
     * 
     * @param id 聊天消息主键
     * @return 结果
     */
    public int deleteChatMessageById(Long id);

    /**
     * 批量删除聊天消息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteChatMessageByIds(Long[] ids);
}
