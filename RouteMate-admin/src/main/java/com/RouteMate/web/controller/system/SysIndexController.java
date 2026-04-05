package com.RouteMate.web.controller.system;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.RouteMate.common.annotation.Anonymous;
import com.RouteMate.common.config.NaiweilanlanConfig;
import com.RouteMate.common.core.controller.BaseController;
import com.RouteMate.common.core.domain.AjaxResult;
import com.RouteMate.common.utils.StringUtils;
import com.RouteMate.system.service.ISysIndexService;

/**
 * 首页
 *
 * @author Naiweilanlan
 */
@RestController
public class SysIndexController extends BaseController
{
    /** 系统基础配置 */
    @Autowired
    private NaiweilanlanConfig NaiweilanlanConfig;

    @Autowired
    private ISysIndexService sysIndexService;

    /**
     * 访问首页，提示语
     */
    @RequestMapping("/")
    public String index()
    {
        return StringUtils.format("欢迎使用{}后台管理框架，当前版本：v{}，请通过前端地址访问。", NaiweilanlanConfig.getName(), NaiweilanlanConfig.getVersion());
    }

    /**
     * 获取系统统计概览数据
     */
    @Anonymous
    @GetMapping("/system/index/overview")
    public AjaxResult getOverview()
    {
        Map<String, Object> data = sysIndexService.getSystemOverview();
        return success(data);
    }

    /**
     * 获取用户注册趋势数据（最近30天）
     */
    @Anonymous
    @GetMapping("/system/index/userTrend")
    public AjaxResult getUserTrend()
    {
        Map<String, Object> data = sysIndexService.getUserRegistrationTrend();
        return success(data);
    }

    /**
     * 获取在线用户趋势数据（最近24小时）
     */
    @Anonymous
    @GetMapping("/system/index/onlineTrend")
    public AjaxResult getOnlineTrend()
    {
        Map<String, Object> data = sysIndexService.getOnlineUserTrend();
        return success(data);
    }

    /**
     * 获取系统模块使用统计
     */
    @Anonymous
    @GetMapping("/system/index/moduleUsage")
    public AjaxResult getModuleUsage()
    {
        List<Map<String, Object>> data = sysIndexService.getModuleUsageStats();
        return success(data);
    }

    /**
     * 获取POI类别统计（饼图）
     */
    @Anonymous
    @GetMapping("/stats/poi")
    public AjaxResult getPoiStats()
    {
        List<Map<String, Object>> data = sysIndexService.getPoiStats();
        return success(data);
    }

    /**
     * 获取热点排行（柱状图）
     */
    @Anonymous
    @GetMapping("/stats/hotspot")
    public AjaxResult getHotspotStats()
    {
        List<Map<String, Object>> data = sysIndexService.getHotspotStats();
        return success(data);
    }

    /**
     * 获取用户活跃度统计
     */
    @Anonymous
    @GetMapping("/stats/user")
    public AjaxResult getUserStats()
    {
        List<Map<String, Object>> data = sysIndexService.getUserStats();
        return success(data);
    }

    /**
     * 获取出行时间分布统计
     */
    @Anonymous
    @GetMapping("/stats/trajectory")
    public AjaxResult getTrajectoryStats()
    {
        List<Map<String, Object>> data = sysIndexService.getTrajectoryStats();
        return success(data);
    }
}
