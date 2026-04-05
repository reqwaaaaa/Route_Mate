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
import com.RouteMate.route.domain.RouteAnalysisTask;
import com.RouteMate.route.service.IRouteAnalysisTaskService;
import com.RouteMate.common.utils.poi.ExcelUtil;
import com.RouteMate.common.core.page.TableDataInfo;

/**
 * 分析任务Controller
 *
 * @author RouteMate
 * @date 2026-04-01
 */
@RestController
@RequestMapping("/route/task")
public class RouteAnalysisTaskController extends BaseController
{
    @Autowired
    private IRouteAnalysisTaskService routeAnalysisTaskService;

    /**
     * 查询分析任务列表
     */
    @PreAuthorize("@ss.hasPermi('route:task:list')")
    @GetMapping("/list")
    public TableDataInfo list(RouteAnalysisTask routeAnalysisTask)
    {
        startPage();
        // 如果是管理员，清除用户ID限制，获取所有数据
        routeAnalysisTask.setUserId(SecurityUtils.getUserId());
        if (SecurityUtils.isAdmin(SecurityUtils.getUserId())) {
            routeAnalysisTask.setUserId(null);
        }
        List<RouteAnalysisTask> list = routeAnalysisTaskService.selectRouteAnalysisTaskList(routeAnalysisTask);
        return getDataTable(list);
    }

    /**
     * 导出分析任务列表
     */
    @PreAuthorize("@ss.hasPermi('route:task:export')")
    @Log(title = "分析任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RouteAnalysisTask routeAnalysisTask)
    {
        // 如果是管理员，清除用户ID限制，获取所有数据
        routeAnalysisTask.setUserId(SecurityUtils.getUserId());
        if (SecurityUtils.isAdmin(SecurityUtils.getUserId())) {
            routeAnalysisTask.setUserId(null);
        }
        List<RouteAnalysisTask> list = routeAnalysisTaskService.selectRouteAnalysisTaskList(routeAnalysisTask);
        ExcelUtil<RouteAnalysisTask> util = new ExcelUtil<RouteAnalysisTask>(RouteAnalysisTask.class);
        util.exportExcel(response, list, "分析任务数据");
    }

    /**
     * 获取分析任务详细信息
     */
    @PreAuthorize("@ss.hasPermi('route:task:query')")
    @GetMapping(value = "/{taskId}")
    public AjaxResult getInfo(@PathVariable("taskId") String taskId)
    {
        return success(routeAnalysisTaskService.selectRouteAnalysisTaskByTaskId(taskId));
    }

    /**
     * 新增分析任务
     */
    @PreAuthorize("@ss.hasPermi('route:task:add')")
    @Log(title = "分析任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RouteAnalysisTask routeAnalysisTask)
    {
        return toAjax(routeAnalysisTaskService.insertRouteAnalysisTask(routeAnalysisTask));
    }
    /**
     * 修改分析任务
     */
    @PreAuthorize("@ss.hasPermi('route:task:edit')")
    @Log(title = "分析任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RouteAnalysisTask routeAnalysisTask)
    {
        return toAjax(routeAnalysisTaskService.updateRouteAnalysisTask(routeAnalysisTask));
    }

    /**
     * 删除分析任务
     */
    @PreAuthorize("@ss.hasPermi('route:task:remove')")
    @Log(title = "分析任务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{taskIds}")
    public AjaxResult remove(@PathVariable String[] taskIds)
    {
        return toAjax(routeAnalysisTaskService.deleteRouteAnalysisTaskByTaskIds(taskIds));
    }

    /**
     * 获取用户ID（关联sys_user.user_id）（关联用户信息表）选项列表
     */
    @PreAuthorize("@ss.hasPermi('route:task:query')")
    @GetMapping("/userId/options")
    public AjaxResult getUserIdOptions()
    {
        Long currentUserId = SecurityUtils.getUserId();
        return success(routeAnalysisTaskService.getUserIdOptions(currentUserId));
    }

    /**
     * 获取轨迹ID（关联route_trajectory_info.trajectory_id）（关联出行轨迹信息表）选项列表（用于下拉框）
     */
    @PreAuthorize("@ss.hasPermi('route:task:query')")
    @GetMapping("/trajectoryId/options")
    public AjaxResult getTrajectoryIdOptions()
    {
        return success(routeAnalysisTaskService.getTrajectoryIdOptions());
    }
}
