package com.RouteMate.route.service.impl;

import java.util.List;
import com.RouteMate.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.RouteMate.route.mapper.RouteMessageRecordMapper;
import com.RouteMate.route.domain.RouteMessageRecord;
import com.RouteMate.route.service.IRouteMessageRecordService;

/**
 * 消息通知Service业务层处理
 * 
 * @author RouteMate
 * @date 2026-04-01
 */
@Service
public class RouteMessageRecordServiceImpl implements IRouteMessageRecordService 
{
    @Autowired
    private RouteMessageRecordMapper routeMessageRecordMapper;

    /**
     * 查询消息通知
     * 
     * @param messageId 消息通知主键
     * @return 消息通知
     */
    @Override
    public RouteMessageRecord selectRouteMessageRecordByMessageId(Long messageId)
    {
        return routeMessageRecordMapper.selectRouteMessageRecordByMessageId(messageId);
    }

    /**
     * 查询消息通知列表
     * 
     * @param routeMessageRecord 消息通知
     * @return 消息通知
     */
    @Override
    public List<RouteMessageRecord> selectRouteMessageRecordList(RouteMessageRecord routeMessageRecord)
    {
        return routeMessageRecordMapper.selectRouteMessageRecordList(routeMessageRecord);
    }

    /**
     * 新增消息通知
     * 
     * @param routeMessageRecord 消息通知
     * @return 结果
     */
    @Override
    public int insertRouteMessageRecord(RouteMessageRecord routeMessageRecord)
    {
        routeMessageRecord.setCreateTime(DateUtils.getNowDate());
        return routeMessageRecordMapper.insertRouteMessageRecord(routeMessageRecord);
    }

    /**
     * 修改消息通知
     * 
     * @param routeMessageRecord 消息通知
     * @return 结果
     */
    @Override
    public int updateRouteMessageRecord(RouteMessageRecord routeMessageRecord)
    {
        routeMessageRecord.setUpdateTime(DateUtils.getNowDate());
        return routeMessageRecordMapper.updateRouteMessageRecord(routeMessageRecord);
    }

    /**
     * 批量删除消息通知
     * 
     * @param messageIds 需要删除的消息通知主键
     * @return 结果
     */
    @Override
    public int deleteRouteMessageRecordByMessageIds(Long[] messageIds)
    {
        return routeMessageRecordMapper.deleteRouteMessageRecordByMessageIds(messageIds);
    }

    /**
     * 删除消息通知信息
     * 
     * @param messageId 消息通知主键
     * @return 结果
     */
    @Override
    public int deleteRouteMessageRecordByMessageId(Long messageId)
    {
        return routeMessageRecordMapper.deleteRouteMessageRecordByMessageId(messageId);
    }

    /**
     * 获取发送者ID（关联sys_user.user_id）（关联用户信息表）选项列表
     * 
     * @return 发送者ID（关联sys_user.user_id）（关联用户信息表）选项列表
     */
    @Override
    public List<?> getSenderIdOptions()
    {
        // 查询关联表数据
        return routeMessageRecordMapper.selectSenderIdOptions();
    }

    /**
     * 获取接收者ID（关联sys_user.user_id，系统消息时为空）（关联用户信息表）选项列表
     * 
     * @return 接收者ID（关联sys_user.user_id，系统消息时为空）（关联用户信息表）选项列表
     */
    @Override
    public List<?> getReceiverIdOptions()
    {
        // 查询关联表数据
        return routeMessageRecordMapper.selectReceiverIdOptions();
    }
}
