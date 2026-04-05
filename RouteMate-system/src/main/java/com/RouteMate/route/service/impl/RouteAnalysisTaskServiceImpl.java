package com.RouteMate.route.service.impl;

import java.util.List;
import com.RouteMate.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.RouteMate.route.mapper.RouteAnalysisTaskMapper;
import com.RouteMate.route.domain.RouteAnalysisTask;
import com.RouteMate.route.service.IRouteAnalysisTaskService;

/**
 * 分析任务Service业务层处理
 * 
 * @author RouteMate
 * @date 2026-04-01
 */
@Service
public class RouteAnalysisTaskServiceImpl implements IRouteAnalysisTaskService 
{
    @Autowired
    private RouteAnalysisTaskMapper routeAnalysisTaskMapper;

    /**
     * 查询分析任务
     * 
     * @param taskId 分析任务主键
     * @return 分析任务
     */
    @Override
    public RouteAnalysisTask selectRouteAnalysisTaskByTaskId(String taskId)
    {
        return routeAnalysisTaskMapper.selectRouteAnalysisTaskByTaskId(taskId);
    }

    /**
     * 查询分析任务列表
     * 
     * @param routeAnalysisTask 分析任务
     * @return 分析任务
     */
    @Override
    public List<RouteAnalysisTask> selectRouteAnalysisTaskList(RouteAnalysisTask routeAnalysisTask)
    {
        return routeAnalysisTaskMapper.selectRouteAnalysisTaskList(routeAnalysisTask);
    }

    /**
     * 新增分析任务
     * 
     * @param routeAnalysisTask 分析任务
     * @return 结果
     */
    @Override
    public int insertRouteAnalysisTask(RouteAnalysisTask routeAnalysisTask)
    {
        routeAnalysisTask.setCreateTime(DateUtils.getNowDate());
        return routeAnalysisTaskMapper.insertRouteAnalysisTask(routeAnalysisTask);
    }

    /**
     * 修改分析任务
     * 
     * @param routeAnalysisTask 分析任务
     * @return 结果
     */
    @Override
    public int updateRouteAnalysisTask(RouteAnalysisTask routeAnalysisTask)
    {
        routeAnalysisTask.setUpdateTime(DateUtils.getNowDate());
        return routeAnalysisTaskMapper.updateRouteAnalysisTask(routeAnalysisTask);
    }

    /**
     * 批量删除分析任务
     * 
     * @param taskIds 需要删除的分析任务主键
     * @return 结果
     */
    @Override
    public int deleteRouteAnalysisTaskByTaskIds(String[] taskIds)
    {
        return routeAnalysisTaskMapper.deleteRouteAnalysisTaskByTaskIds(taskIds);
    }

    /**
     * 删除分析任务信息
     * 
     * @param taskId 分析任务主键
     * @return 结果
     */
    @Override
    public int deleteRouteAnalysisTaskByTaskId(String taskId)
    {
        return routeAnalysisTaskMapper.deleteRouteAnalysisTaskByTaskId(taskId);
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
        return routeAnalysisTaskMapper.selectUserIdOptions(params);
    }

    /**
     * 获取轨迹ID（关联route_trajectory_info.trajectory_id）（关联出行轨迹信息表）选项列表
     * 
     * @return 轨迹ID（关联route_trajectory_info.trajectory_id）（关联出行轨迹信息表）选项列表
     */
    @Override
    public List<?> getTrajectoryIdOptions()
    {
        // 查询关联表数据
        return routeAnalysisTaskMapper.selectTrajectoryIdOptions();
    }
}
