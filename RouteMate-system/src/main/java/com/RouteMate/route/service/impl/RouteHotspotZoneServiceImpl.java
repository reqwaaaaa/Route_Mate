package com.RouteMate.route.service.impl;

import java.util.List;
import com.RouteMate.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.RouteMate.route.mapper.RouteHotspotZoneMapper;
import com.RouteMate.route.domain.RouteHotspotZone;
import com.RouteMate.route.service.IRouteHotspotZoneService;

/**
 * 热点数据Service业务层处理
 * 
 * @author RouteMate
 * @date 2026-04-01
 */
@Service
public class RouteHotspotZoneServiceImpl implements IRouteHotspotZoneService 
{
    @Autowired
    private RouteHotspotZoneMapper routeHotspotZoneMapper;

    /**
     * 查询热点数据
     * 
     * @param hotspotId 热点数据主键
     * @return 热点数据
     */
    @Override
    public RouteHotspotZone selectRouteHotspotZoneByHotspotId(Long hotspotId)
    {
        return routeHotspotZoneMapper.selectRouteHotspotZoneByHotspotId(hotspotId);
    }

    /**
     * 查询热点数据列表
     * 
     * @param routeHotspotZone 热点数据
     * @return 热点数据
     */
    @Override
    public List<RouteHotspotZone> selectRouteHotspotZoneList(RouteHotspotZone routeHotspotZone)
    {
        return routeHotspotZoneMapper.selectRouteHotspotZoneList(routeHotspotZone);
    }

    /**
     * 新增热点数据
     * 
     * @param routeHotspotZone 热点数据
     * @return 结果
     */
    @Override
    public int insertRouteHotspotZone(RouteHotspotZone routeHotspotZone)
    {
        routeHotspotZone.setCreateTime(DateUtils.getNowDate());
        return routeHotspotZoneMapper.insertRouteHotspotZone(routeHotspotZone);
    }

    /**
     * 修改热点数据
     * 
     * @param routeHotspotZone 热点数据
     * @return 结果
     */
    @Override
    public int updateRouteHotspotZone(RouteHotspotZone routeHotspotZone)
    {
        routeHotspotZone.setUpdateTime(DateUtils.getNowDate());
        return routeHotspotZoneMapper.updateRouteHotspotZone(routeHotspotZone);
    }

    /**
     * 批量删除热点数据
     * 
     * @param hotspotIds 需要删除的热点数据主键
     * @return 结果
     */
    @Override
    public int deleteRouteHotspotZoneByHotspotIds(Long[] hotspotIds)
    {
        return routeHotspotZoneMapper.deleteRouteHotspotZoneByHotspotIds(hotspotIds);
    }

    /**
     * 删除热点数据信息
     * 
     * @param hotspotId 热点数据主键
     * @return 结果
     */
    @Override
    public int deleteRouteHotspotZoneByHotspotId(Long hotspotId)
    {
        return routeHotspotZoneMapper.deleteRouteHotspotZoneByHotspotId(hotspotId);
    }

    /**
     * 获取分析任务ID（关联分析任务表）选项列表
     * 
     * @return 分析任务ID（关联分析任务表）选项列表
     */
    @Override
    public List<?> getAnalysisTaskIdOptions()
    {
        // 查询关联表数据
        return routeHotspotZoneMapper.selectAnalysisTaskIdOptions();
    }
}
