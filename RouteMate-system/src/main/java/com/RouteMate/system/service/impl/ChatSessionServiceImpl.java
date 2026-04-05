package com.RouteMate.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.RouteMate.system.mapper.ChatSessionMapper;
import com.RouteMate.system.domain.ChatSession;
import com.RouteMate.system.service.IChatSessionService;

/**
 * 聊天会话Service业务层处理
 * 
 * @author RouteMate
 * @date 2024-01-01
 */
@Service
public class ChatSessionServiceImpl implements IChatSessionService 
{
    @Autowired
    private ChatSessionMapper chatSessionMapper;

    /**
     * 查询聊天会话
     * 
     * @param id 聊天会话主键
     * @return 聊天会话
     */
    @Override
    public ChatSession selectChatSessionById(Long id)
    {
        return chatSessionMapper.selectChatSessionById(id);
    }

    /**
     * 查询聊天会话列表
     * 
     * @param chatSession 聊天会话
     * @return 聊天会话
     */
    @Override
    public List<ChatSession> selectChatSessionList(ChatSession chatSession)
    {
        return chatSessionMapper.selectChatSessionList(chatSession);
    }

    /**
     * 查询用户的聊天会话列表
     * 
     * @param userId 用户ID
     * @return 聊天会话集合
     */
    @Override
    public List<ChatSession> selectChatSessionsByUserId(String userId)
    {
        return chatSessionMapper.selectChatSessionsByUserId(userId);
    }

    /**
     * 查询或创建聊天会话
     * 
     * @param userId 用户ID
     * @param contactId 联系人ID
     * @return 聊天会话
     */
    @Override
    public ChatSession selectChatSessionByUserAndContact(String userId, String contactId)
    {
        return chatSessionMapper.selectChatSessionByUserAndContact(userId, contactId);
    }

    /**
     * 新增聊天会话
     * 
     * @param chatSession 聊天会话
     * @return 结果
     */
    @Override
    public int insertChatSession(ChatSession chatSession)
    {
        return chatSessionMapper.insertChatSession(chatSession);
    }

    /**
     * 修改聊天会话
     * 
     * @param chatSession 聊天会话
     * @return 结果
     */
    @Override
    public int updateChatSession(ChatSession chatSession)
    {
        return chatSessionMapper.updateChatSession(chatSession);
    }

    /**
     * 清零未读消息数
     * 
     * @param userId 用户ID
     * @param contactId 联系人ID
     * @return 结果
     */
    @Override
    public int clearUnreadCount(String userId, String contactId)
    {
        return chatSessionMapper.clearUnreadCount(userId, contactId);
    }

    /**
     * 批量删除聊天会话
     * 
     * @param ids 需要删除的聊天会话主键
     * @return 结果
     */
    @Override
    public int deleteChatSessionByIds(Long[] ids)
    {
        return chatSessionMapper.deleteChatSessionByIds(ids);
    }

    /**
     * 删除聊天会话信息
     * 
     * @param id 聊天会话主键
     * @return 结果
     */
    @Override
    public int deleteChatSessionById(Long id)
    {
        return chatSessionMapper.deleteChatSessionById(id);
    }
}
