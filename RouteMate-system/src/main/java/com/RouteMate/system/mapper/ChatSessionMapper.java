package com.RouteMate.system.mapper;

import java.util.List;
import com.RouteMate.system.domain.ChatSession;
import org.apache.ibatis.annotations.Param;

/**
 * 聊天会话Mapper接口
 * 
 * @author RouteMate
 * @date 2024-01-01
 */
public interface ChatSessionMapper 
{
    /**
     * 查询聊天会话
     * 
     * @param id 聊天会话主键
     * @return 聊天会话
     */
    public ChatSession selectChatSessionById(Long id);

    /**
     * 查询聊天会话列表
     * 
     * @param chatSession 聊天会话
     * @return 聊天会话集合
     */
    public List<ChatSession> selectChatSessionList(ChatSession chatSession);

    /**
     * 查询用户的聊天会话列表
     * 
     * @param userId 用户ID
     * @return 聊天会话集合
     */
    public List<ChatSession> selectChatSessionsByUserId(@Param("userId") String userId);

    /**
     * 查询或创建聊天会话
     * 
     * @param userId 用户ID
     * @param contactId 联系人ID
     * @return 聊天会话
     */
    public ChatSession selectChatSessionByUserAndContact(@Param("userId") String userId, @Param("contactId") String contactId);

    /**
     * 新增聊天会话
     * 
     * @param chatSession 聊天会话
     * @return 结果
     */
    public int insertChatSession(ChatSession chatSession);

    /**
     * 修改聊天会话
     * 
     * @param chatSession 聊天会话
     * @return 结果
     */
    public int updateChatSession(ChatSession chatSession);

    /**
     * 更新会话的最后消息信息
     * 
     * @param userId 用户ID
     * @param contactId 联系人ID
     * @param lastMessage 最后消息内容
     * @return 结果
     */
    public int updateLastMessage(@Param("userId") String userId, @Param("contactId") String contactId, @Param("lastMessage") String lastMessage);

    /**
     * 增加未读消息数
     * 
     * @param userId 用户ID
     * @param contactId 联系人ID
     * @return 结果
     */
    public int incrementUnreadCount(@Param("userId") String userId, @Param("contactId") String contactId);

    /**
     * 清零未读消息数
     * 
     * @param userId 用户ID
     * @param contactId 联系人ID
     * @return 结果
     */
    public int clearUnreadCount(@Param("userId") String userId, @Param("contactId") String contactId);

    /**
     * 删除聊天会话
     * 
     * @param id 聊天会话主键
     * @return 结果
     */
    public int deleteChatSessionById(Long id);

    /**
     * 批量删除聊天会话
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteChatSessionByIds(Long[] ids);
}
