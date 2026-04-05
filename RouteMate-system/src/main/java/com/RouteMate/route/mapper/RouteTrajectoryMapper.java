package com.RouteMate.route.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import java.util.List;
import java.util.Map;

/**
 * 轨迹数据Mapper接口
 */
@Mapper
public interface RouteTrajectoryMapper {
    
    /**
     * 根据轨迹ID获取轨迹信息
     */
    @Select("SELECT trajectory_id, trajectory_name, trajectory_desc, start_location, end_location, " +
            "total_distance, total_duration, point_count, trajectory_data, trajectory_status " +
            "FROM route_trajectory_info " +
            "WHERE trajectory_id = #{trajectoryId} AND del_flag = '0'")
    Map<String, Object> getTrajectoryInfo(@Param("trajectoryId") Long trajectoryId);
    
    /**
     * 根据轨迹ID获取轨迹点数据
     * 从trajectory_data字段解析JSON数据
     */
    @Select("SELECT trajectory_data FROM route_trajectory_info " +
            "WHERE trajectory_id = #{trajectoryId} AND del_flag = '0'")
    String getTrajectoryData(@Param("trajectoryId") Long trajectoryId);
    
    /**
     * 获取用户的所有轨迹
     */
    @Select("SELECT trajectory_id, trajectory_name, start_location, end_location, " +
            "total_distance, total_duration, point_count, create_time " +
            "FROM route_trajectory_info " +
            "WHERE user_id = #{userId} AND del_flag = '0' " +
            "ORDER BY create_time DESC")
    List<Map<String, Object>> getUserTrajectories(@Param("userId") Long userId);
    
    /**
     * 获取轨迹统计信息
     */
    @Select("SELECT COUNT(*) as trajectory_count, " +
            "SUM(total_distance) as total_distance, " +
            "AVG(total_distance) as avg_distance, " +
            "SUM(total_duration) as total_duration " +
            "FROM route_trajectory_info " +
            "WHERE user_id = #{userId} AND del_flag = '0'")
    Map<String, Object> getTrajectoryStatistics(@Param("userId") Long userId);
    
    /**
     * 插入分析任务
     */
    @Insert("INSERT INTO route_analysis_task " +
            "(task_id, task_name, user_id, trajectory_id, algorithm_type, algorithm_params, " +
            "task_status, progress_percent, start_time, create_by, create_time) " +
            "VALUES (#{taskId}, #{taskName}, #{userId}, #{trajectoryId}, #{algorithmType}, " +
            "#{algorithmParams}, '1', 0, NOW(), #{createBy}, NOW())")
    int insertAnalysisTask(@Param("taskId") String taskId,
                          @Param("taskName") String taskName,
                          @Param("userId") Long userId,
                          @Param("trajectoryId") Long trajectoryId,
                          @Param("algorithmType") String algorithmType,
                          @Param("algorithmParams") String algorithmParams,
                          @Param("createBy") String createBy);
    
    /**
     * 更新分析任务状态
     */
    @Update("UPDATE route_analysis_task SET " +
            "task_status = #{taskStatus}, " +
            "progress_percent = #{progressPercent}, " +
            "end_time = CASE WHEN #{taskStatus} IN ('2', '3') THEN NOW() ELSE end_time END, " +
            "result_data = #{resultData}, " +
            "error_message = #{errorMessage}, " +
            "execution_time = #{executionTime}, " +
            "update_time = NOW() " +
            "WHERE task_id = #{taskId}")
    int updateAnalysisTask(@Param("taskId") String taskId,
                          @Param("taskStatus") String taskStatus,
                          @Param("progressPercent") Integer progressPercent,
                          @Param("resultData") String resultData,
                          @Param("errorMessage") String errorMessage,
                          @Param("executionTime") Integer executionTime);
    
    /**
     * 获取分析任务信息
     */
    @Select("SELECT task_id, task_name, user_id, trajectory_id, algorithm_type, algorithm_params, " +
            "task_status, progress_percent, start_time, end_time, result_data, error_message, " +
            "execution_time, create_time " +
            "FROM route_analysis_task " +
            "WHERE task_id = #{taskId} AND del_flag = '0'")
    Map<String, Object> getAnalysisTask(@Param("taskId") String taskId);
    
    /**
     * 获取用户的分析任务列表
     */
    @Select("SELECT task_id, task_name, algorithm_type, task_status, progress_percent, " +
            "start_time, end_time, execution_time, create_time " +
            "FROM route_analysis_task " +
            "WHERE user_id = #{userId} AND del_flag = '0' " +
            "ORDER BY create_time DESC")
    List<Map<String, Object>> getUserAnalysisTasks(@Param("userId") Long userId);
    
    /**
     * 保存热点数据
     */
    @Insert("INSERT INTO route_hotspot_zone " +
            "(hotspot_name, hotspot_type, center_latitude, center_longitude, radius, " +
            "visit_count, unique_users, peak_hour, avg_duration, related_pois, " +
            "analysis_task_id, create_by, create_time) " +
            "VALUES (#{hotspotName}, #{hotspotType}, #{centerLatitude}, #{centerLongitude}, " +
            "#{radius}, #{visitCount}, #{uniqueUsers}, #{peakHour}, #{avgDuration}, " +
            "#{relatedPois}, #{analysisTaskId}, #{createBy}, NOW())")
    int insertHotspot(@Param("hotspotName") String hotspotName,
                     @Param("hotspotType") String hotspotType,
                     @Param("centerLatitude") Double centerLatitude,
                     @Param("centerLongitude") Double centerLongitude,
                     @Param("radius") Double radius,
                     @Param("visitCount") Integer visitCount,
                     @Param("uniqueUsers") Integer uniqueUsers,
                     @Param("peakHour") String peakHour,
                     @Param("avgDuration") Integer avgDuration,
                     @Param("relatedPois") String relatedPois,
                     @Param("analysisTaskId") String analysisTaskId,
                     @Param("createBy") String createBy);
    
    /**
     * 获取热点数据
     */
    @Select("SELECT hotspot_id, hotspot_name, hotspot_type, center_latitude, center_longitude, " +
            "radius, visit_count, unique_users, peak_hour, avg_duration, related_pois, " +
            "create_time " +
            "FROM route_hotspot_zone " +
            "WHERE analysis_task_id = #{taskId} " +
            "ORDER BY visit_count DESC")
    List<Map<String, Object>> getHotspotsByTask(@Param("taskId") String taskId);
}
