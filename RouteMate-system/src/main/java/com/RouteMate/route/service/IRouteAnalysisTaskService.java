package com.RouteMate.route.service;

import java.util.List;
import com.RouteMate.route.domain.RouteAnalysisTask;

/**
 * 分析任务Service接口
 * 
 * @author RouteMate
 * @date 2026-04-01
 */
public interface IRouteAnalysisTaskService 
{
    /**
     * 查询分析任务
     * 
     * @param taskId 分析任务主键
     * @return 分析任务
     */
    public RouteAnalysisTask selectRouteAnalysisTaskByTaskId(String taskId);

    /**
     * 查询分析任务列表
     * 
     * @param routeAnalysisTask 分析任务
     * @return 分析任务集合
     */
    public List<RouteAnalysisTask> selectRouteAnalysisTaskList(RouteAnalysisTask routeAnalysisTask);

    /**
     * 新增分析任务
     * 
     * @param routeAnalysisTask 分析任务
     * @return 结果
     */
    public int insertRouteAnalysisTask(RouteAnalysisTask routeAnalysisTask);

    /**
     * 修改分析任务
     * 
     * @param routeAnalysisTask 分析任务
     * @return 结果
     */
    public int updateRouteAnalysisTask(RouteAnalysisTask routeAnalysisTask);

    /**
     * 批量删除分析任务
     * 
     * @param taskIds 需要删除的分析任务主键集合
     * @return 结果
     */
    public int deleteRouteAnalysisTaskByTaskIds(String[] taskIds);

    /**
     * 删除分析任务信息
     * 
     * @param taskId 分析任务主键
     * @return 结果
     */
    public int deleteRouteAnalysisTaskByTaskId(String taskId);

    /**
     * 获取用户ID（关联sys_user.user_id）（关联用户信息表）选项列表
     * 
     * @param currentUserId 当前用户ID
     * @return 用户ID（关联sys_user.user_id）（关联用户信息表）选项列表
     */
    public List<?> getUserIdOptions(Long currentUserId);

    /**
     * 获取轨迹ID（关联route_trajectory_info.trajectory_id）（关联出行轨迹信息表）选项列表
     * 
     * @return 轨迹ID（关联route_trajectory_info.trajectory_id）（关联出行轨迹信息表）选项列表
     */
    public List<?> getTrajectoryIdOptions();
}
