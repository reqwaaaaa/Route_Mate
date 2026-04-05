package com.RouteMate.route.service.impl;

import java.util.List;
import com.RouteMate.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.RouteMate.route.mapper.RoutePoiDetailMapper;
import com.RouteMate.route.domain.RoutePoiDetail;
import com.RouteMate.route.service.IRoutePoiDetailService;

/**
 * POI信息Service业务层处理
 * 
 * @author RouteMate
 * @date 2026-04-01
 */
@Service
public class RoutePoiDetailServiceImpl implements IRoutePoiDetailService 
{
    @Autowired
    private RoutePoiDetailMapper routePoiDetailMapper;

    /**
     * 查询POI信息
     * 
     * @param poiId POI信息主键
     * @return POI信息
     */
    @Override
    public RoutePoiDetail selectRoutePoiDetailByPoiId(Long poiId)
    {
        return routePoiDetailMapper.selectRoutePoiDetailByPoiId(poiId);
    }

    /**
     * 查询POI信息列表
     * 
     * @param routePoiDetail POI信息
     * @return POI信息
     */
    @Override
    public List<RoutePoiDetail> selectRoutePoiDetailList(RoutePoiDetail routePoiDetail)
    {
        return routePoiDetailMapper.selectRoutePoiDetailList(routePoiDetail);
    }

    /**
     * 新增POI信息
     * 
     * @param routePoiDetail POI信息
     * @return 结果
     */
    @Override
    public int insertRoutePoiDetail(RoutePoiDetail routePoiDetail)
    {
        routePoiDetail.setCreateTime(DateUtils.getNowDate());
        return routePoiDetailMapper.insertRoutePoiDetail(routePoiDetail);
    }

    /**
     * 修改POI信息
     * 
     * @param routePoiDetail POI信息
     * @return 结果
     */
    @Override
    public int updateRoutePoiDetail(RoutePoiDetail routePoiDetail)
    {
        routePoiDetail.setUpdateTime(DateUtils.getNowDate());
        return routePoiDetailMapper.updateRoutePoiDetail(routePoiDetail);
    }

    /**
     * 批量删除POI信息
     * 
     * @param poiIds 需要删除的POI信息主键
     * @return 结果
     */
    @Override
    public int deleteRoutePoiDetailByPoiIds(Long[] poiIds)
    {
        return routePoiDetailMapper.deleteRoutePoiDetailByPoiIds(poiIds);
    }

    /**
     * 删除POI信息信息
     * 
     * @param poiId POI信息主键
     * @return 结果
     */
    @Override
    public int deleteRoutePoiDetailByPoiId(Long poiId)
    {
        return routePoiDetailMapper.deleteRoutePoiDetailByPoiId(poiId);
    }

    /**
     * 获取贡献者ID（关联sys_user.user_id）（关联用户信息表）选项列表
     * 
     * @return 贡献者ID（关联sys_user.user_id）（关联用户信息表）选项列表
     */
    @Override
    public List<?> getContributorIdOptions()
    {
        // 查询关联表数据
        return routePoiDetailMapper.selectContributorIdOptions();
    }

    /**
     * 获取审核人ID（关联用户信息表）选项列表
     * 
     * @return 审核人ID（关联用户信息表）选项列表
     */
    @Override
    public List<?> getAuditByOptions()
    {
        // 查询关联表数据
        return routePoiDetailMapper.selectAuditByOptions();
    }
}
