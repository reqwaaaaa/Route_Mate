package com.RouteMate.route.service;

import java.util.List;
import com.RouteMate.route.domain.RouteHotspotZone;

/**
 * 热点数据Service接口
 * 
 * @author RouteMate
 * @date 2026-04-01
 */
public interface IRouteHotspotZoneService 
{
    /**
     * 查询热点数据
     * 
     * @param hotspotId 热点数据主键
     * @return 热点数据
     */
    public RouteHotspotZone selectRouteHotspotZoneByHotspotId(Long hotspotId);

    /**
     * 查询热点数据列表
     * 
     * @param routeHotspotZone 热点数据
     * @return 热点数据集合
     */
    public List<RouteHotspotZone> selectRouteHotspotZoneList(RouteHotspotZone routeHotspotZone);

    /**
     * 新增热点数据
     * 
     * @param routeHotspotZone 热点数据
     * @return 结果
     */
    public int insertRouteHotspotZone(RouteHotspotZone routeHotspotZone);

    /**
     * 修改热点数据
     * 
     * @param routeHotspotZone 热点数据
     * @return 结果
     */
    public int updateRouteHotspotZone(RouteHotspotZone routeHotspotZone);

    /**
     * 批量删除热点数据
     * 
     * @param hotspotIds 需要删除的热点数据主键集合
     * @return 结果
     */
    public int deleteRouteHotspotZoneByHotspotIds(Long[] hotspotIds);

    /**
     * 删除热点数据信息
     * 
     * @param hotspotId 热点数据主键
     * @return 结果
     */
    public int deleteRouteHotspotZoneByHotspotId(Long hotspotId);

    /**
     * 获取分析任务ID（关联分析任务表）选项列表
     * 
     * @return 分析任务ID（关联分析任务表）选项列表
     */
    public List<?> getAnalysisTaskIdOptions();
}
