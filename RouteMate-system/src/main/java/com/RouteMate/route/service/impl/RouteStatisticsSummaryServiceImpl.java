package com.RouteMate.route.service.impl;

import java.util.List;
import com.RouteMate.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.RouteMate.route.mapper.RouteStatisticsSummaryMapper;
import com.RouteMate.route.domain.RouteStatisticsSummary;
import com.RouteMate.route.service.IRouteStatisticsSummaryService;

/**
 * 统计数据Service业务层处理
 * 
 * @author RouteMate
 * @date 2026-04-01
 */
@Service
public class RouteStatisticsSummaryServiceImpl implements IRouteStatisticsSummaryService 
{
    @Autowired
    private RouteStatisticsSummaryMapper routeStatisticsSummaryMapper;

    /**
     * 查询统计数据
     * 
     * @param statId 统计数据主键
     * @return 统计数据
     */
    @Override
    public RouteStatisticsSummary selectRouteStatisticsSummaryByStatId(Long statId)
    {
        return routeStatisticsSummaryMapper.selectRouteStatisticsSummaryByStatId(statId);
    }

    /**
     * 查询统计数据列表
     * 
     * @param routeStatisticsSummary 统计数据
     * @return 统计数据
     */
    @Override
    public List<RouteStatisticsSummary> selectRouteStatisticsSummaryList(RouteStatisticsSummary routeStatisticsSummary)
    {
        return routeStatisticsSummaryMapper.selectRouteStatisticsSummaryList(routeStatisticsSummary);
    }

    /**
     * 新增统计数据
     * 
     * @param routeStatisticsSummary 统计数据
     * @return 结果
     */
    @Override
    public int insertRouteStatisticsSummary(RouteStatisticsSummary routeStatisticsSummary)
    {
        routeStatisticsSummary.setCreateTime(DateUtils.getNowDate());
        return routeStatisticsSummaryMapper.insertRouteStatisticsSummary(routeStatisticsSummary);
    }

    /**
     * 修改统计数据
     * 
     * @param routeStatisticsSummary 统计数据
     * @return 结果
     */
    @Override
    public int updateRouteStatisticsSummary(RouteStatisticsSummary routeStatisticsSummary)
    {
        routeStatisticsSummary.setUpdateTime(DateUtils.getNowDate());
        return routeStatisticsSummaryMapper.updateRouteStatisticsSummary(routeStatisticsSummary);
    }

    /**
     * 批量删除统计数据
     * 
     * @param statIds 需要删除的统计数据主键
     * @return 结果
     */
    @Override
    public int deleteRouteStatisticsSummaryByStatIds(Long[] statIds)
    {
        return routeStatisticsSummaryMapper.deleteRouteStatisticsSummaryByStatIds(statIds);
    }

    /**
     * 删除统计数据信息
     * 
     * @param statId 统计数据主键
     * @return 结果
     */
    @Override
    public int deleteRouteStatisticsSummaryByStatId(Long statId)
    {
        return routeStatisticsSummaryMapper.deleteRouteStatisticsSummaryByStatId(statId);
    }
}
