package com.RouteMate.route.service.impl;

import java.util.List;
import com.RouteMate.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.RouteMate.route.mapper.RouteUserPreferenceMapper;
import com.RouteMate.route.domain.RouteUserPreference;
import com.RouteMate.route.service.IRouteUserPreferenceService;

/**
 * 用户偏好Service业务层处理
 * 
 * @author RouteMate
 * @date 2026-04-01
 */
@Service
public class RouteUserPreferenceServiceImpl implements IRouteUserPreferenceService 
{
    @Autowired
    private RouteUserPreferenceMapper routeUserPreferenceMapper;

    /**
     * 查询用户偏好
     * 
     * @param preferenceId 用户偏好主键
     * @return 用户偏好
     */
    @Override
    public RouteUserPreference selectRouteUserPreferenceByPreferenceId(Long preferenceId)
    {
        return routeUserPreferenceMapper.selectRouteUserPreferenceByPreferenceId(preferenceId);
    }

    /**
     * 查询用户偏好列表
     * 
     * @param routeUserPreference 用户偏好
     * @return 用户偏好
     */
    @Override
    public List<RouteUserPreference> selectRouteUserPreferenceList(RouteUserPreference routeUserPreference)
    {
        return routeUserPreferenceMapper.selectRouteUserPreferenceList(routeUserPreference);
    }

    /**
     * 新增用户偏好
     * 
     * @param routeUserPreference 用户偏好
     * @return 结果
     */
    @Override
    public int insertRouteUserPreference(RouteUserPreference routeUserPreference)
    {
        routeUserPreference.setCreateTime(DateUtils.getNowDate());
        return routeUserPreferenceMapper.insertRouteUserPreference(routeUserPreference);
    }

    /**
     * 修改用户偏好
     * 
     * @param routeUserPreference 用户偏好
     * @return 结果
     */
    @Override
    public int updateRouteUserPreference(RouteUserPreference routeUserPreference)
    {
        routeUserPreference.setUpdateTime(DateUtils.getNowDate());
        return routeUserPreferenceMapper.updateRouteUserPreference(routeUserPreference);
    }

    /**
     * 批量删除用户偏好
     * 
     * @param preferenceIds 需要删除的用户偏好主键
     * @return 结果
     */
    @Override
    public int deleteRouteUserPreferenceByPreferenceIds(Long[] preferenceIds)
    {
        return routeUserPreferenceMapper.deleteRouteUserPreferenceByPreferenceIds(preferenceIds);
    }

    /**
     * 删除用户偏好信息
     * 
     * @param preferenceId 用户偏好主键
     * @return 结果
     */
    @Override
    public int deleteRouteUserPreferenceByPreferenceId(Long preferenceId)
    {
        return routeUserPreferenceMapper.deleteRouteUserPreferenceByPreferenceId(preferenceId);
    }

    /**
     * 获取用户ID（关联sys_user.user_id）（关联用户信息表）选项列表
     * 
     * @return 用户ID（关联sys_user.user_id）（关联用户信息表）选项列表
     */
    @Override
    public List<?> getUserIdOptions()
    {
        // 查询关联表数据
        return routeUserPreferenceMapper.selectUserIdOptions();
    }
}
