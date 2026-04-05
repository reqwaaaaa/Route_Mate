package com.RouteMate.route.service;

import com.RouteMate.route.domain.TrajectoryAnalysisRequest;
import com.RouteMate.route.domain.TrajectoryAnalysisResult;
import java.util.List;

/**
 * 轨迹分析服务接口
 */
public interface ITrajectoryAnalysisService {
    
    /**
     * 执行轨迹分析
     * @param request 分析请求
     * @return 分析结果
     */
    TrajectoryAnalysisResult executeAnalysis(TrajectoryAnalysisRequest request);
    
    /**
     * 获取分析结果
     * @param taskId 任务ID
     * @return 分析结果
     */
    TrajectoryAnalysisResult getAnalysisResult(String taskId);
    
    /**
     * 获取用户的分析任务列表
     * @param userId 用户ID
     * @return 任务列表
     */
    List<TrajectoryAnalysisResult> getUserAnalysisTasks(Long userId);
    
    /**
     * 取消分析任务
     * @param taskId 任务ID
     * @return 是否成功
     */
    boolean cancelAnalysisTask(String taskId);
    
    /**
     * 删除分析任务
     * @param taskId 任务ID
     * @return 是否成功
     */
    boolean deleteAnalysisTask(String taskId);
}
