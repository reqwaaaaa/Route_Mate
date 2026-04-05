package com.RouteMate.system.service;

import java.util.List;
import java.util.Map;

/**
 * 系统首页统计服务接口
 * 
 * @author Naiweilanlan
 */
public interface ISysIndexService 
{
    /**
     * 获取系统统计概览数据
     * 
     * @return 概览数据
     */
    Map<String, Object> getSystemOverview();

    /**
     * 获取用户注册趋势数据（最近30天）
     * 
     * @return 注册趋势数据
     */
    Map<String, Object> getUserRegistrationTrend();

    /**
     * 获取在线用户趋势数据（最近24小时）
     * 
     * @return 在线用户趋势数据
     */
    Map<String, Object> getOnlineUserTrend();

    /**
     * 获取系统模块使用统计
     * 
     * @return 模块使用统计
     */
    List<Map<String, Object>> getModuleUsageStats();

    /**
     * 获取POI类别统计
     * 
     * @return POI类别统计
     */
    List<Map<String, Object>> getPoiStats();

    /**
     * 获取热点排行统计
     * 
     * @return 热点排行统计
     */
    List<Map<String, Object>> getHotspotStats();

    /**
     * 获取用户活跃度统计
     * 
     * @return 用户活跃度统计
     */
    List<Map<String, Object>> getUserStats();

    /**
     * 获取出行时间分布统计
     * 
     * @return 出行时间分布统计
     */
    List<Map<String, Object>> getTrajectoryStats();
} 