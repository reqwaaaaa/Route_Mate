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
import com.RouteMate.route.domain.RouteStatisticsSummary;
import com.RouteMate.route.service.IRouteStatisticsSummaryService;
import com.RouteMate.common.utils.poi.ExcelUtil;
import com.RouteMate.common.core.page.TableDataInfo;

/**
 * 统计数据Controller
 * 
 * @author RouteMate
 * @date 2026-04-01
 */
@RestController
@RequestMapping("/route/summary")
public class RouteStatisticsSummaryController extends BaseController
{
    @Autowired
    private IRouteStatisticsSummaryService routeStatisticsSummaryService;

    /**
     * 查询统计数据列表
     */
    @PreAuthorize("@ss.hasPermi('route:summary:list')")
    @GetMapping("/list")
    public TableDataInfo list(RouteStatisticsSummary routeStatisticsSummary)
    {
        startPage();
        List<RouteStatisticsSummary> list = routeStatisticsSummaryService.selectRouteStatisticsSummaryList(routeStatisticsSummary);
        return getDataTable(list);
    }

    /**
     * 导出统计数据列表
     */
    @PreAuthorize("@ss.hasPermi('route:summary:export')")
    @Log(title = "统计数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RouteStatisticsSummary routeStatisticsSummary)
    {
        List<RouteStatisticsSummary> list = routeStatisticsSummaryService.selectRouteStatisticsSummaryList(routeStatisticsSummary);
        ExcelUtil<RouteStatisticsSummary> util = new ExcelUtil<RouteStatisticsSummary>(RouteStatisticsSummary.class);
        util.exportExcel(response, list, "统计数据数据");
    }

    /**
     * 获取统计数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('route:summary:query')")
    @GetMapping(value = "/{statId}")
    public AjaxResult getInfo(@PathVariable("statId") Long statId)
    {
        return success(routeStatisticsSummaryService.selectRouteStatisticsSummaryByStatId(statId));
    }

    /**
     * 新增统计数据
     */
    @PreAuthorize("@ss.hasPermi('route:summary:add')")
    @Log(title = "统计数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RouteStatisticsSummary routeStatisticsSummary)
    {
        return toAjax(routeStatisticsSummaryService.insertRouteStatisticsSummary(routeStatisticsSummary));
    }

    /**
     * 修改统计数据
     */
    @PreAuthorize("@ss.hasPermi('route:summary:edit')")
    @Log(title = "统计数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RouteStatisticsSummary routeStatisticsSummary)
    {
        return toAjax(routeStatisticsSummaryService.updateRouteStatisticsSummary(routeStatisticsSummary));
    }

    /**
     * 删除统计数据
     */
    @PreAuthorize("@ss.hasPermi('route:summary:remove')")
    @Log(title = "统计数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{statIds}")
    public AjaxResult remove(@PathVariable Long[] statIds)
    {
        return toAjax(routeStatisticsSummaryService.deleteRouteStatisticsSummaryByStatIds(statIds));
    }
}
