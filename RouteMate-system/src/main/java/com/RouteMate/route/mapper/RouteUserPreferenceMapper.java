package com.RouteMate.route.mapper;

import java.util.List;
import com.RouteMate.route.domain.RouteUserPreference;

/**
 * 用户偏好Mapper接口
 * 
 * @author RouteMate
 * @date 2026-04-01
 */
public interface RouteUserPreferenceMapper 
{
    /**
     * 查询用户偏好
     * 
     * @param preferenceId 用户偏好主键
     * @return 用户偏好
     */
    public RouteUserPreference selectRouteUserPreferenceByPreferenceId(Long preferenceId);

    /**
     * 查询用户偏好列表
     * 
     * @param routeUserPreference 用户偏好
     * @return 用户偏好集合
     */
    public List<RouteUserPreference> selectRouteUserPreferenceList(RouteUserPreference routeUserPreference);

    /**
     * 新增用户偏好
     * 
     * @param routeUserPreference 用户偏好
     * @return 结果
     */
    public int insertRouteUserPreference(RouteUserPreference routeUserPreference);

    /**
     * 修改用户偏好
     * 
     * @param routeUserPreference 用户偏好
     * @return 结果
     */
    public int updateRouteUserPreference(RouteUserPreference routeUserPreference);

    /**
     * 删除用户偏好
     * 
     * @param preferenceId 用户偏好主键
     * @return 结果
     */
    public int deleteRouteUserPreferenceByPreferenceId(Long preferenceId);

    /**
     * 批量删除用户偏好
     * 
     * @param preferenceIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRouteUserPreferenceByPreferenceIds(Long[] preferenceIds);

    /**
     * 查询用户ID（关联sys_user.user_id）（关联用户信息表）选项列表
     * 
     * @return 用户ID（关联sys_user.user_id）（关联用户信息表）选项列表
     */
    public List<?> selectUserIdOptions();
}
