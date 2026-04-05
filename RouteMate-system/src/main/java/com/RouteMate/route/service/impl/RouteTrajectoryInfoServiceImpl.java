package com.RouteMate.route.service.impl;

import java.util.List;
import com.RouteMate.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.RouteMate.route.mapper.RouteTrajectoryInfoMapper;
import com.RouteMate.route.domain.RouteTrajectoryInfo;
import com.RouteMate.route.service.IRouteTrajectoryInfoService;

/**
 * 出行轨迹信息Service业务层处理
 * 
 * @author RouteMate
 * @date 2026-04-01
 */
@Service
public class RouteTrajectoryInfoServiceImpl implements IRouteTrajectoryInfoService 
{
    @Autowired
    private RouteTrajectoryInfoMapper routeTrajectoryInfoMapper;

    /**
     * 查询出行轨迹信息
     * 
     * @param trajectoryId 出行轨迹信息主键
     * @return 出行轨迹信息
     */
    @Override
    public RouteTrajectoryInfo selectRouteTrajectoryInfoByTrajectoryId(Long trajectoryId)
    {
        return routeTrajectoryInfoMapper.selectRouteTrajectoryInfoByTrajectoryId(trajectoryId);
    }

    /**
     * 查询出行轨迹信息列表
     * 
     * @param routeTrajectoryInfo 出行轨迹信息
     * @return 出行轨迹信息
     */
    @Override
    public List<RouteTrajectoryInfo> selectRouteTrajectoryInfoList(RouteTrajectoryInfo routeTrajectoryInfo)
    {
        return routeTrajectoryInfoMapper.selectRouteTrajectoryInfoList(routeTrajectoryInfo);
    }

    /**
     * 新增出行轨迹信息
     * 
     * @param routeTrajectoryInfo 出行轨迹信息
     * @return 结果
     */
    @Override
    public int insertRouteTrajectoryInfo(RouteTrajectoryInfo routeTrajectoryInfo)
    {
        routeTrajectoryInfo.setCreateTime(DateUtils.getNowDate());
        return routeTrajectoryInfoMapper.insertRouteTrajectoryInfo(routeTrajectoryInfo);
    }

    /**
     * 修改出行轨迹信息
     * 
     * @param routeTrajectoryInfo 出行轨迹信息
     * @return 结果
     */
    @Override
    public int updateRouteTrajectoryInfo(RouteTrajectoryInfo routeTrajectoryInfo)
    {
        routeTrajectoryInfo.setUpdateTime(DateUtils.getNowDate());
        return routeTrajectoryInfoMapper.updateRouteTrajectoryInfo(routeTrajectoryInfo);
    }

    /**
     * 批量删除出行轨迹信息
     * 
     * @param trajectoryIds 需要删除的出行轨迹信息主键
     * @return 结果
     */
    @Override
    public int deleteRouteTrajectoryInfoByTrajectoryIds(Long[] trajectoryIds)
    {
        return routeTrajectoryInfoMapper.deleteRouteTrajectoryInfoByTrajectoryIds(trajectoryIds);
    }

    /**
     * 删除出行轨迹信息信息
     * 
     * @param trajectoryId 出行轨迹信息主键
     * @return 结果
     */
    @Override
    public int deleteRouteTrajectoryInfoByTrajectoryId(Long trajectoryId)
    {
        return routeTrajectoryInfoMapper.deleteRouteTrajectoryInfoByTrajectoryId(trajectoryId);
    }

    /**
     * 获取用户ID（关联sys_user.user_id）（关联用户信息表）选项列表
     * 
     * @param currentUserId 当前用户ID
     * @return 用户ID（关联sys_user.user_id）（关联用户信息表）选项列表
     */
    @Override
    public List<?> getUserIdOptions(Long currentUserId)
    {
        // 构建参数Map
        java.util.Map<String, Object> params = new java.util.HashMap<>();
        params.put("currentUserId", currentUserId);
        // 查询关联表数据
        return routeTrajectoryInfoMapper.selectUserIdOptions(params);
    }
}
