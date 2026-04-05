package com.RouteMate.route.mapper;

import java.util.List;
import com.RouteMate.route.domain.RoutePoiDetail;

/**
 * POI信息Mapper接口
 * 
 * @author RouteMate
 * @date 2026-04-01
 */
public interface RoutePoiDetailMapper 
{
    /**
     * 查询POI信息
     * 
     * @param poiId POI信息主键
     * @return POI信息
     */
    public RoutePoiDetail selectRoutePoiDetailByPoiId(Long poiId);

    /**
     * 查询POI信息列表
     * 
     * @param routePoiDetail POI信息
     * @return POI信息集合
     */
    public List<RoutePoiDetail> selectRoutePoiDetailList(RoutePoiDetail routePoiDetail);

    /**
     * 新增POI信息
     * 
     * @param routePoiDetail POI信息
     * @return 结果
     */
    public int insertRoutePoiDetail(RoutePoiDetail routePoiDetail);

    /**
     * 修改POI信息
     * 
     * @param routePoiDetail POI信息
     * @return 结果
     */
    public int updateRoutePoiDetail(RoutePoiDetail routePoiDetail);

    /**
     * 删除POI信息
     * 
     * @param poiId POI信息主键
     * @return 结果
     */
    public int deleteRoutePoiDetailByPoiId(Long poiId);

    /**
     * 批量删除POI信息
     * 
     * @param poiIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRoutePoiDetailByPoiIds(Long[] poiIds);

    /**
     * 查询贡献者ID（关联sys_user.user_id）（关联用户信息表）选项列表
     * 
     * @return 贡献者ID（关联sys_user.user_id）（关联用户信息表）选项列表
     */
    public List<?> selectContributorIdOptions();

    /**
     * 查询审核人ID（关联用户信息表）选项列表
     * 
     * @return 审核人ID（关联用户信息表）选项列表
     */
    public List<?> selectAuditByOptions();
}
