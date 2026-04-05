package com.RouteMate.route.service;

import java.util.List;
import com.RouteMate.route.domain.RouteMessageRecord;

/**
 * 消息通知Service接口
 * 
 * @author RouteMate
 * @date 2026-04-01
 */
public interface IRouteMessageRecordService 
{
    /**
     * 查询消息通知
     * 
     * @param messageId 消息通知主键
     * @return 消息通知
     */
    public RouteMessageRecord selectRouteMessageRecordByMessageId(Long messageId);

    /**
     * 查询消息通知列表
     * 
     * @param routeMessageRecord 消息通知
     * @return 消息通知集合
     */
    public List<RouteMessageRecord> selectRouteMessageRecordList(RouteMessageRecord routeMessageRecord);

    /**
     * 新增消息通知
     * 
     * @param routeMessageRecord 消息通知
     * @return 结果
     */
    public int insertRouteMessageRecord(RouteMessageRecord routeMessageRecord);

    /**
     * 修改消息通知
     * 
     * @param routeMessageRecord 消息通知
     * @return 结果
     */
    public int updateRouteMessageRecord(RouteMessageRecord routeMessageRecord);

    /**
     * 批量删除消息通知
     * 
     * @param messageIds 需要删除的消息通知主键集合
     * @return 结果
     */
    public int deleteRouteMessageRecordByMessageIds(Long[] messageIds);

    /**
     * 删除消息通知信息
     * 
     * @param messageId 消息通知主键
     * @return 结果
     */
    public int deleteRouteMessageRecordByMessageId(Long messageId);

    /**
     * 获取发送者ID（关联sys_user.user_id）（关联用户信息表）选项列表
     * 
     * @return 发送者ID（关联sys_user.user_id）（关联用户信息表）选项列表
     */
    public List<?> getSenderIdOptions();

    /**
     * 获取接收者ID（关联sys_user.user_id，系统消息时为空）（关联用户信息表）选项列表
     * 
     * @return 接收者ID（关联sys_user.user_id，系统消息时为空）（关联用户信息表）选项列表
     */
    public List<?> getReceiverIdOptions();
}
