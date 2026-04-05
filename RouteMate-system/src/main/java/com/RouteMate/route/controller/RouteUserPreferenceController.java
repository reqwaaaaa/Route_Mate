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
import com.RouteMate.route.domain.RouteUserPreference;
import com.RouteMate.route.service.IRouteUserPreferenceService;
import com.RouteMate.common.utils.poi.ExcelUtil;
import com.RouteMate.common.core.page.TableDataInfo;

/**
 * 用户偏好Controller
 * 
 * @author RouteMate
 * @date 2026-04-01
 */
@RestController
@RequestMapping("/route/preference")
public class RouteUserPreferenceController extends BaseController
{
    @Autowired
    private IRouteUserPreferenceService routeUserPreferenceService;

    /**
     * 查询用户偏好列表
     */
    @PreAuthorize("@ss.hasPermi('route:preference:list')")
    @GetMapping("/list")
    public TableDataInfo list(RouteUserPreference routeUserPreference)
    {
        startPage();
        List<RouteUserPreference> list = routeUserPreferenceService.selectRouteUserPreferenceList(routeUserPreference);
        return getDataTable(list);
    }

    /**
     * 导出用户偏好列表
     */
    @PreAuthorize("@ss.hasPermi('route:preference:export')")
    @Log(title = "用户偏好", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RouteUserPreference routeUserPreference)
    {
        List<RouteUserPreference> list = routeUserPreferenceService.selectRouteUserPreferenceList(routeUserPreference);
        ExcelUtil<RouteUserPreference> util = new ExcelUtil<RouteUserPreference>(RouteUserPreference.class);
        util.exportExcel(response, list, "用户偏好数据");
    }

    /**
     * 获取用户偏好详细信息
     */
    @PreAuthorize("@ss.hasPermi('route:preference:query')")
    @GetMapping(value = "/{preferenceId}")
    public AjaxResult getInfo(@PathVariable("preferenceId") Long preferenceId)
    {
        return success(routeUserPreferenceService.selectRouteUserPreferenceByPreferenceId(preferenceId));
    }

    /**
     * 新增用户偏好
     */
    @PreAuthorize("@ss.hasPermi('route:preference:add')")
    @Log(title = "用户偏好", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RouteUserPreference routeUserPreference)
    {
        return toAjax(routeUserPreferenceService.insertRouteUserPreference(routeUserPreference));
    }

    /**
     * 修改用户偏好
     */
    @PreAuthorize("@ss.hasPermi('route:preference:edit')")
    @Log(title = "用户偏好", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RouteUserPreference routeUserPreference)
    {
        return toAjax(routeUserPreferenceService.updateRouteUserPreference(routeUserPreference));
    }

    /**
     * 删除用户偏好
     */
    @PreAuthorize("@ss.hasPermi('route:preference:remove')")
    @Log(title = "用户偏好", businessType = BusinessType.DELETE)
	@DeleteMapping("/{preferenceIds}")
    public AjaxResult remove(@PathVariable Long[] preferenceIds)
    {
        return toAjax(routeUserPreferenceService.deleteRouteUserPreferenceByPreferenceIds(preferenceIds));
    }

    /**
     * 获取用户ID（关联sys_user.user_id）（关联用户信息表）选项列表（用于下拉框）
     */
    @PreAuthorize("@ss.hasPermi('route:preference:query')")
    @GetMapping("/userId/options")
    public AjaxResult getUserIdOptions()
    {
        return success(routeUserPreferenceService.getUserIdOptions());
    }
}
