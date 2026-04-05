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
import com.RouteMate.route.domain.RouteMessageRecord;
import com.RouteMate.route.service.IRouteMessageRecordService;
import com.RouteMate.common.utils.poi.ExcelUtil;
import com.RouteMate.common.core.page.TableDataInfo;

/**
 * 消息通知Controller
 * 
 * @author RouteMate
 * @date 2026-04-01
 */
@RestController
@RequestMapping("/route/record")
public class RouteMessageRecordController extends BaseController
{
    @Autowired
    private IRouteMessageRecordService routeMessageRecordService;

    /**
     * 查询消息通知列表
     */
    @PreAuthorize("@ss.hasPermi('route:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(RouteMessageRecord routeMessageRecord)
    {
        startPage();
        List<RouteMessageRecord> list = routeMessageRecordService.selectRouteMessageRecordList(routeMessageRecord);
        return getDataTable(list);
    }

    /**
     * 导出消息通知列表
     */
    @PreAuthorize("@ss.hasPermi('route:record:export')")
    @Log(title = "消息通知", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RouteMessageRecord routeMessageRecord)
    {
        List<RouteMessageRecord> list = routeMessageRecordService.selectRouteMessageRecordList(routeMessageRecord);
        ExcelUtil<RouteMessageRecord> util = new ExcelUtil<RouteMessageRecord>(RouteMessageRecord.class);
        util.exportExcel(response, list, "消息通知数据");
    }

    /**
     * 获取消息通知详细信息
     */
    @PreAuthorize("@ss.hasPermi('route:record:query')")
    @GetMapping(value = "/{messageId}")
    public AjaxResult getInfo(@PathVariable("messageId") Long messageId)
    {
        return success(routeMessageRecordService.selectRouteMessageRecordByMessageId(messageId));
    }

    /**
     * 新增消息通知
     */
    @PreAuthorize("@ss.hasPermi('route:record:add')")
    @Log(title = "消息通知", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RouteMessageRecord routeMessageRecord)
    {
        return toAjax(routeMessageRecordService.insertRouteMessageRecord(routeMessageRecord));
    }

    /**
     * 修改消息通知
     */
    @PreAuthorize("@ss.hasPermi('route:record:edit')")
    @Log(title = "消息通知", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RouteMessageRecord routeMessageRecord)
    {
        return toAjax(routeMessageRecordService.updateRouteMessageRecord(routeMessageRecord));
    }

    /**
     * 删除消息通知
     */
    @PreAuthorize("@ss.hasPermi('route:record:remove')")
    @Log(title = "消息通知", businessType = BusinessType.DELETE)
	@DeleteMapping("/{messageIds}")
    public AjaxResult remove(@PathVariable Long[] messageIds)
    {
        return toAjax(routeMessageRecordService.deleteRouteMessageRecordByMessageIds(messageIds));
    }

    /**
     * 获取发送者ID（关联sys_user.user_id）（关联用户信息表）选项列表（用于下拉框）
     */
    @PreAuthorize("@ss.hasPermi('route:record:query')")
    @GetMapping("/senderId/options")
    public AjaxResult getSenderIdOptions()
    {
        return success(routeMessageRecordService.getSenderIdOptions());
    }

    /**
     * 获取接收者ID（关联sys_user.user_id，系统消息时为空）（关联用户信息表）选项列表（用于下拉框）
     */
    @PreAuthorize("@ss.hasPermi('route:record:query')")
    @GetMapping("/receiverId/options")
    public AjaxResult getReceiverIdOptions()
    {
        return success(routeMessageRecordService.getReceiverIdOptions());
    }
}
