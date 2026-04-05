package com.RouteMate.route.mapper;

import java.util.List;
import com.RouteMate.route.domain.RouteStatisticsSummary;

/**
 * 统计数据Mapper接口
 * 
 * @author RouteMate
 * @date 2026-04-01
 */
public interface RouteStatisticsSummaryMapper 
{
    /**
     * 查询统计数据
     * 
     * @param statId 统计数据主键
     * @return 统计数据
     */
    public RouteStatisticsSummary selectRouteStatisticsSummaryByStatId(Long statId);

    /**
     * 查询统计数据列表
     * 
     * @param routeStatisticsSummary 统计数据
     * @return 统计数据集合
     */
    public List<RouteStatisticsSummary> selectRouteStatisticsSummaryList(RouteStatisticsSummary routeStatisticsSummary);

    /**
     * 新增统计数据
     * 
     * @param routeStatisticsSummary 统计数据
     * @return 结果
     */
    public int insertRouteStatisticsSummary(RouteStatisticsSummary routeStatisticsSummary);

    /**
     * 修改统计数据
     * 
     * @param routeStatisticsSummary 统计数据
     * @return 结果
     */
    public int updateRouteStatisticsSummary(RouteStatisticsSummary routeStatisticsSummary);

    /**
     * 删除统计数据
     * 
     * @param statId 统计数据主键
     * @return 结果
     */
    public int deleteRouteStatisticsSummaryByStatId(Long statId);

    /**
     * 批量删除统计数据
     * 
     * @param statIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRouteStatisticsSummaryByStatIds(Long[] statIds);
}
