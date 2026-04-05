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
import com.RouteMate.route.domain.RouteHotspotZone;
import com.RouteMate.route.service.IRouteHotspotZoneService;
import com.RouteMate.common.utils.poi.ExcelUtil;
import com.RouteMate.common.core.page.TableDataInfo;

/**
 * 热点数据Controller
 * 
 * @author RouteMate
 * @date 2026-04-01
 */
@RestController
@RequestMapping("/route/zone")
public class RouteHotspotZoneController extends BaseController
{
    @Autowired
    private IRouteHotspotZoneService routeHotspotZoneService;

    /**
     * 查询热点数据列表
     */
    @PreAuthorize("@ss.hasPermi('route:zone:list')")
    @GetMapping("/list")
    public TableDataInfo list(RouteHotspotZone routeHotspotZone)
    {
        startPage();
        List<RouteHotspotZone> list = routeHotspotZoneService.selectRouteHotspotZoneList(routeHotspotZone);
        return getDataTable(list);
    }

    /**
     * 导出热点数据列表
     */
    @PreAuthorize("@ss.hasPermi('route:zone:export')")
    @Log(title = "热点数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RouteHotspotZone routeHotspotZone)
    {
        List<RouteHotspotZone> list = routeHotspotZoneService.selectRouteHotspotZoneList(routeHotspotZone);
        ExcelUtil<RouteHotspotZone> util = new ExcelUtil<RouteHotspotZone>(RouteHotspotZone.class);
        util.exportExcel(response, list, "热点数据数据");
    }

    /**
     * 获取热点数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('route:zone:query')")
    @GetMapping(value = "/{hotspotId}")
    public AjaxResult getInfo(@PathVariable("hotspotId") Long hotspotId)
    {
        return success(routeHotspotZoneService.selectRouteHotspotZoneByHotspotId(hotspotId));
    }

    /**
     * 新增热点数据
     */
    @PreAuthorize("@ss.hasPermi('route:zone:add')")
    @Log(title = "热点数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RouteHotspotZone routeHotspotZone)
    {
        return toAjax(routeHotspotZoneService.insertRouteHotspotZone(routeHotspotZone));
    }

    /**
     * 修改热点数据
     */
    @PreAuthorize("@ss.hasPermi('route:zone:edit')")
    @Log(title = "热点数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RouteHotspotZone routeHotspotZone)
    {
        return toAjax(routeHotspotZoneService.updateRouteHotspotZone(routeHotspotZone));
    }

    /**
     * 删除热点数据
     */
    @PreAuthorize("@ss.hasPermi('route:zone:remove')")
    @Log(title = "热点数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{hotspotIds}")
    public AjaxResult remove(@PathVariable Long[] hotspotIds)
    {
        return toAjax(routeHotspotZoneService.deleteRouteHotspotZoneByHotspotIds(hotspotIds));
    }

    /**
     * 获取分析任务ID（关联分析任务表）选项列表（用于下拉框）
     */
    @PreAuthorize("@ss.hasPermi('route:zone:query')")
    @GetMapping("/analysisTaskId/options")
    public AjaxResult getAnalysisTaskIdOptions()
    {
        return success(routeHotspotZoneService.getAnalysisTaskIdOptions());
    }
}
