package com.RouteMate.system.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

/**
 * 系统首页统计数据Mapper接口
 * 
 * @author Naiweilanlan
 */
public interface SysIndexMapper 
{
    /**
     * 获取总用户数
     * 
     * @return 总用户数
     */
    Long getTotalUserCount();

    /**
     * 获取在线用户数
     * 
     * @return 在线用户数
     */
    Long getOnlineUserCount();

    /**
     * 获取今日新增用户数
     * 
     * @return 今日新增用户数
     */
    Long getTodayNewUserCount();

    /**
     * 获取本月新增用户数
     * 
     * @return 本月新增用户数
     */
    Long getMonthNewUserCount();

    /**
     * 获取系统总访问量
     * 
     * @return 总访问量
     */
    Long getTotalVisitCount();

    /**
     * 获取今日访问量
     * 
     * @return 今日访问量
     */
    Long getTodayVisitCount();

    /**
     * 获取系统运行天数
     * 
     * @return 运行天数
     */
    Long getSystemRunningDays();

    /**
     * 获取总菜单数
     * 
     * @return 总菜单数
     */
    Long getTotalMenuCount();

    /**
     * 根据日期获取用户注册数量
     * 
     * @param date 日期
     * @return 注册数量
     */
    Long getUserRegistrationCountByDate(@Param("date") String date);

    /**
     * 根据小时获取在线用户数量
     * 
     * @param dateTime 时间
     * @return 在线用户数量
     */
    Long getOnlineUserCountByHour(@Param("dateTime") Date dateTime);

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