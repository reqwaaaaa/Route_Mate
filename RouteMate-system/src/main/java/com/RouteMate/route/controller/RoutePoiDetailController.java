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
import com.RouteMate.route.domain.RoutePoiDetail;
import com.RouteMate.route.service.IRoutePoiDetailService;
import com.RouteMate.common.utils.poi.ExcelUtil;
import com.RouteMate.common.core.page.TableDataInfo;

/**
 * POI信息Controller
 * 
 * @author RouteMate
 * @date 2026-04-01
 */
@RestController
@RequestMapping("/route/detail")
public class RoutePoiDetailController extends BaseController
{
    @Autowired
    private IRoutePoiDetailService routePoiDetailService;

    /**
     * 查询POI信息列表
     */
    @PreAuthorize("@ss.hasPermi('route:detail:list')")
    @GetMapping("/list")
    public TableDataInfo list(RoutePoiDetail routePoiDetail)
    {
        startPage();
        List<RoutePoiDetail> list = routePoiDetailService.selectRoutePoiDetailList(routePoiDetail);
        return getDataTable(list);
    }

    /**
     * 导出POI信息列表
     */
    @PreAuthorize("@ss.hasPermi('route:detail:export')")
    @Log(title = "POI信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RoutePoiDetail routePoiDetail)
    {
        List<RoutePoiDetail> list = routePoiDetailService.selectRoutePoiDetailList(routePoiDetail);
        ExcelUtil<RoutePoiDetail> util = new ExcelUtil<RoutePoiDetail>(RoutePoiDetail.class);
        util.exportExcel(response, list, "POI信息数据");
    }

    /**
     * 获取POI信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('route:detail:query')")
    @GetMapping(value = "/{poiId}")
    public AjaxResult getInfo(@PathVariable("poiId") Long poiId)
    {
        return success(routePoiDetailService.selectRoutePoiDetailByPoiId(poiId));
    }

    /**
     * 新增POI信息
     */
    @PreAuthorize("@ss.hasPermi('route:detail:add')")
    @Log(title = "POI信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RoutePoiDetail routePoiDetail)
    {
        return toAjax(routePoiDetailService.insertRoutePoiDetail(routePoiDetail));
    }

    /**
     * 修改POI信息
     */
    @PreAuthorize("@ss.hasPermi('route:detail:edit')")
    @Log(title = "POI信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RoutePoiDetail routePoiDetail)
    {
        return toAjax(routePoiDetailService.updateRoutePoiDetail(routePoiDetail));
    }

    /**
     * 删除POI信息
     */
    @PreAuthorize("@ss.hasPermi('route:detail:remove')")
    @Log(title = "POI信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{poiIds}")
    public AjaxResult remove(@PathVariable Long[] poiIds)
    {
        return toAjax(routePoiDetailService.deleteRoutePoiDetailByPoiIds(poiIds));
    }

    /**
     * 获取贡献者ID（关联sys_user.user_id）（关联用户信息表）选项列表（用于下拉框）
     */
    @PreAuthorize("@ss.hasPermi('route:detail:query')")
    @GetMapping("/contributorId/options")
    public AjaxResult getContributorIdOptions()
    {
        return success(routePoiDetailService.getContributorIdOptions());
    }

    /**
     * 获取审核人ID（关联用户信息表）选项列表（用于下拉框）
     */
    @PreAuthorize("@ss.hasPermi('route:detail:query')")
    @GetMapping("/auditBy/options")
    public AjaxResult getAuditByOptions()
    {
        return success(routePoiDetailService.getAuditByOptions());
    }
}
