package com.RouteMate.route.mapper;

import java.util.List;
import com.RouteMate.route.domain.RouteTrajectoryInfo;

/**
 * 出行轨迹信息Mapper接口
 * 
 * @author RouteMate
 * @date 2026-04-01
 */
public interface RouteTrajectoryInfoMapper 
{
    /**
     * 查询出行轨迹信息
     * 
     * @param trajectoryId 出行轨迹信息主键
     * @return 出行轨迹信息
     */
    public RouteTrajectoryInfo selectRouteTrajectoryInfoByTrajectoryId(Long trajectoryId);

    /**
     * 查询出行轨迹信息列表
     * 
     * @param routeTrajectoryInfo 出行轨迹信息
     * @return 出行轨迹信息集合
     */
    public List<RouteTrajectoryInfo> selectRouteTrajectoryInfoList(RouteTrajectoryInfo routeTrajectoryInfo);

    /**
     * 新增出行轨迹信息
     * 
     * @param routeTrajectoryInfo 出行轨迹信息
     * @return 结果
     */
    public int insertRouteTrajectoryInfo(RouteTrajectoryInfo routeTrajectoryInfo);

    /**
     * 修改出行轨迹信息
     * 
     * @param routeTrajectoryInfo 出行轨迹信息
     * @return 结果
     */
    public int updateRouteTrajectoryInfo(RouteTrajectoryInfo routeTrajectoryInfo);

    /**
     * 删除出行轨迹信息
     * 
     * @param trajectoryId 出行轨迹信息主键
     * @return 结果
     */
    public int deleteRouteTrajectoryInfoByTrajectoryId(Long trajectoryId);

    /**
     * 批量删除出行轨迹信息
     * 
     * @param trajectoryIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRouteTrajectoryInfoByTrajectoryIds(Long[] trajectoryIds);

    /**
     * 查询用户ID（关联sys_user.user_id）（关联用户信息表）选项列表
     * 
     * @param params 参数Map
     * @return 用户ID（关联sys_user.user_id）（关联用户信息表）选项列表
     */
    public List<?> selectUserIdOptions(java.util.Map<String, Object> params);
}
