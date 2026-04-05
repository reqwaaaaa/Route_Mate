package com.RouteMate.system.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.RouteMate.system.mapper.SysIndexMapper;
import com.RouteMate.system.service.ISysIndexService;
import com.RouteMate.system.service.ISysUserService;
import com.RouteMate.system.service.ISysUserOnlineService;

/**
 * 系统首页统计服务实现
 * 
 * @author Naiweilanlan
 */
@Service
public class SysIndexServiceImpl implements ISysIndexService 
{
    @Autowired
    private SysIndexMapper sysIndexMapper;

    @Autowired
    private ISysUserService userService;

    @Autowired 
    private ISysUserOnlineService userOnlineService;

    /**
     * 获取系统统计概览数据
     */
    @Override
    public Map<String, Object> getSystemOverview()
    {
        Map<String, Object> data = new HashMap<>();
        
        // 总用户数
        Long totalUsers = sysIndexMapper.getTotalUserCount();
        data.put("totalUsers", totalUsers);
        
        // 在线用户数
        Long onlineUsers = sysIndexMapper.getOnlineUserCount();
        data.put("onlineUsers", onlineUsers);
        
        // 今日新增用户
        Long todayNewUsers = sysIndexMapper.getTodayNewUserCount();
        data.put("todayNewUsers", todayNewUsers);
        
        // 本月新增用户
        Long monthNewUsers = sysIndexMapper.getMonthNewUserCount();
        data.put("monthNewUsers", monthNewUsers);
        
        // 系统总访问量
        Long totalVisits = sysIndexMapper.getTotalVisitCount();
        data.put("totalVisits", totalVisits);
        
        // 今日访问量
        Long todayVisits = sysIndexMapper.getTodayVisitCount();
        data.put("todayVisits", todayVisits);
        
        // 系统运行天数
        Long runningDays = sysIndexMapper.getSystemRunningDays();
        data.put("runningDays", runningDays);
        
        // 总菜单数
        Long totalMenus = sysIndexMapper.getTotalMenuCount();
        data.put("totalMenus", totalMenus);
        
        return data;
    }

    /**
     * 获取用户注册趋势数据（最近30天）
     */
    @Override
    public Map<String, Object> getUserRegistrationTrend()
    {
        Map<String, Object> data = new HashMap<>();
        List<String> dates = new ArrayList<>();
        List<Long> counts = new ArrayList<>();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        
        // 获取最近30天的数据
        for (int i = 29; i >= 0; i--) {
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, -i);
            String date = sdf.format(calendar.getTime());
            dates.add(date);
            
            Long count = sysIndexMapper.getUserRegistrationCountByDate(date);
            counts.add(count);
        }
        
        data.put("dates", dates);
        data.put("counts", counts);
        return data;
    }

    /**
     * 获取在线用户趋势数据（最近24小时）
     */
    @Override
    public Map<String, Object> getOnlineUserTrend()
    {
        Map<String, Object> data = new HashMap<>();
        List<String> hours = new ArrayList<>();
        List<Long> counts = new ArrayList<>();
        
        SimpleDateFormat sdf = new SimpleDateFormat("HH:00");
        Calendar calendar = Calendar.getInstance();
        
        // 获取最近24小时的数据
        for (int i = 23; i >= 0; i--) {
            calendar.setTime(new Date());
            calendar.add(Calendar.HOUR_OF_DAY, -i);
            String hour = sdf.format(calendar.getTime());
            hours.add(hour);
            
            Long count = sysIndexMapper.getOnlineUserCountByHour(calendar.getTime());
            counts.add(count);
        }
        
        data.put("hours", hours);
        data.put("counts", counts);
        return data;
    }

    /**
     * 获取系统模块使用统计
     */
    @Override
    public List<Map<String, Object>> getModuleUsageStats()
    {
        return sysIndexMapper.getModuleUsageStats();
    }

    /**
     * 获取POI类别统计
     */
    @Override
    public List<Map<String, Object>> getPoiStats()
    {
        return sysIndexMapper.getPoiStats();
    }

    /**
     * 获取热点排行统计
     */
    @Override
    public List<Map<String, Object>> getHotspotStats()
    {
        return sysIndexMapper.getHotspotStats();
    }

    /**
     * 获取用户活跃度统计
     */
    @Override
    public List<Map<String, Object>> getUserStats()
    {
        return sysIndexMapper.getUserStats();
    }

    /**
     * 获取出行时间分布统计
     */
    @Override
    public List<Map<String, Object>> getTrajectoryStats()
    {
        return sysIndexMapper.getTrajectoryStats();
    }
}