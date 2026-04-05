package com.RouteMate.route.mapper;

import java.util.List;
import com.RouteMate.route.domain.RouteMessageRecord;

/**
 * 消息通知Mapper接口
 * 
 * @author RouteMate
 * @date 2026-04-01
 */
public interface RouteMessageRecordMapper 
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
     * 删除消息通知
     * 
     * @param messageId 消息通知主键
     * @return 结果
     */
    public int deleteRouteMessageRecordByMessageId(Long messageId);

    /**
     * 批量删除消息通知
     * 
     * @param messageIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRouteMessageRecordByMessageIds(Long[] messageIds);

    /**
     * 查询发送者ID（关联sys_user.user_id）（关联用户信息表）选项列表
     * 
     * @return 发送者ID（关联sys_user.user_id）（关联用户信息表）选项列表
     */
    public List<?> selectSenderIdOptions();

    /**
     * 查询接收者ID（关联sys_user.user_id，系统消息时为空）（关联用户信息表）选项列表
     * 
     * @return 接收者ID（关联sys_user.user_id，系统消息时为空）（关联用户信息表）选项列表
     */
    public List<?> selectReceiverIdOptions();
}
