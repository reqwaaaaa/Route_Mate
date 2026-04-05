package com.RouteMate.route.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.RouteMate.common.annotation.Log;
import com.RouteMate.common.core.controller.BaseController;
import com.RouteMate.common.core.domain.AjaxResult;
import com.RouteMate.common.enums.BusinessType;
import com.RouteMate.common.utils.SecurityUtils;
import com.RouteMate.route.domain.RouteTrajectoryInfo;
import com.RouteMate.route.service.IRouteTrajectoryInfoService;
import com.RouteMate.common.utils.poi.ExcelUtil;
import com.RouteMate.common.core.page.TableDataInfo;

/**
 * 出行轨迹信息Controller
 *
 * @author RouteMate
 * @date 2026-04-01
 */
@RestController
@RequestMapping("/route/info")
public class RouteTrajectoryInfoController extends BaseController
{
    @Autowired
    private IRouteTrajectoryInfoService routeTrajectoryInfoService;

    /**
     * 查询出行轨迹信息列表
     */
    @PreAuthorize("@ss.hasPermi('route:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(RouteTrajectoryInfo routeTrajectoryInfo)
    {
        startPage();
        routeTrajectoryInfo.setUserId(SecurityUtils.getUserId());
        // 如果是管理员，清除用户ID限制，获取所有数据
        if (SecurityUtils.isAdmin(SecurityUtils.getUserId())) {
            routeTrajectoryInfo.setUserId(null);
        }
        List<RouteTrajectoryInfo> list = routeTrajectoryInfoService.selectRouteTrajectoryInfoList(routeTrajectoryInfo);
        return getDataTable(list);
    }

    /**
     * 导出出行轨迹信息列表
     */
    @PreAuthorize("@ss.hasPermi('route:info:export')")
    @Log(title = "出行轨迹信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RouteTrajectoryInfo routeTrajectoryInfo)
    {
        routeTrajectoryInfo.setUserId(SecurityUtils.getUserId());
        // 如果是管理员，清除用户ID限制，获取所有数据
        if (SecurityUtils.isAdmin(SecurityUtils.getUserId())) {
            routeTrajectoryInfo.setUserId(null);
        }
        List<RouteTrajectoryInfo> list = routeTrajectoryInfoService.selectRouteTrajectoryInfoList(routeTrajectoryInfo);
        ExcelUtil<RouteTrajectoryInfo> util = new ExcelUtil<RouteTrajectoryInfo>(RouteTrajectoryInfo.class);
        util.exportExcel(response, list, "出行轨迹信息数据");
    }

    /**
     * 获取出行轨迹信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('route:info:query')")
    @GetMapping(value = "/{trajectoryId}")
    public AjaxResult getInfo(@PathVariable("trajectoryId") Long trajectoryId)
    {
        return success(routeTrajectoryInfoService.selectRouteTrajectoryInfoByTrajectoryId(trajectoryId));
    }

    /**
     * 新增出行轨迹信息
     */
    @PreAuthorize("@ss.hasPermi('route:info:add')")
    @Log(title = "出行轨迹信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RouteTrajectoryInfo routeTrajectoryInfo)
    {
        return toAjax(routeTrajectoryInfoService.insertRouteTrajectoryInfo(routeTrajectoryInfo));
    }

    /**
     * 修改出行轨迹信息
     */
    @PreAuthorize("@ss.hasPermi('route:info:edit')")
    @Log(title = "出行轨迹信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RouteTrajectoryInfo routeTrajectoryInfo)
    {
        return toAjax(routeTrajectoryInfoService.updateRouteTrajectoryInfo(routeTrajectoryInfo));
    }

    /**
     * 删除出行轨迹信息
     */
    @PreAuthorize("@ss.hasPermi('route:info:remove')")
    @Log(title = "出行轨迹信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{trajectoryIds}")
    public AjaxResult remove(@PathVariable Long[] trajectoryIds)
    {
        return toAjax(routeTrajectoryInfoService.deleteRouteTrajectoryInfoByTrajectoryIds(trajectoryIds));
    }

    /**
     * 获取用户ID（关联sys_user.user_id）（关联用户信息表）选项列表
     */
    @PreAuthorize("@ss.hasPermi('route:info:query')")
    @GetMapping("/userId/options")
    public AjaxResult getUserIdOptions()
    {
        Long currentUserId = SecurityUtils.getUserId();
        return success(routeTrajectoryInfoService.getUserIdOptions(currentUserId));
    }
}
