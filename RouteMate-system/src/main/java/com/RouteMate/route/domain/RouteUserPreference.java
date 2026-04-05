package com.RouteMate.route.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.RouteMate.common.annotation.Excel;
import com.RouteMate.common.core.domain.BaseEntity;

/**
 * 用户偏好对象 route_user_preference
 * 
 * @author RouteMate
 * @date 2026-04-01
 */
public class RouteUserPreference extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 偏好ID */
    private Long preferenceId;

    /** 用户ID（关联sys_user.user_id）（关联用户信息表） */
    @Excel(name = "用户ID", readConverterExp = "关=联sys_user.user_id")
    private Long userId;

    /** 用户ID（关联sys_user.user_id）（关联用户信息表）关联字段（关联sys_user表） */
    private String userName;

    /** 偏好类型（TRANSPORT POI TIME ROUTE） */
    @Excel(name = "偏好类型", readConverterExp = "T=RANSPORT,P=OI,T=IME,R=OUTE")
    private String preferenceType;

    /** 偏好键 */
    @Excel(name = "偏好键")
    private String preferenceKey;

    /** 偏好值 */
    @Excel(name = "偏好值")
    private String preferenceValue;

    /** 偏好分数（0-1） */
    @Excel(name = "偏好分数", readConverterExp = "0=-1")
    private BigDecimal preferenceScore;

    public void setPreferenceId(Long preferenceId) 
    {
        this.preferenceId = preferenceId;
    }

    public Long getPreferenceId() 
    {
        return preferenceId;
    }

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }

    public void setPreferenceType(String preferenceType) 
    {
        this.preferenceType = preferenceType;
    }

    public String getPreferenceType() 
    {
        return preferenceType;
    }

    public void setPreferenceKey(String preferenceKey) 
    {
        this.preferenceKey = preferenceKey;
    }

    public String getPreferenceKey() 
    {
        return preferenceKey;
    }

    public void setPreferenceValue(String preferenceValue) 
    {
        this.preferenceValue = preferenceValue;
    }

    public String getPreferenceValue() 
    {
        return preferenceValue;
    }

    public void setPreferenceScore(BigDecimal preferenceScore) 
    {
        this.preferenceScore = preferenceScore;
    }

    public BigDecimal getPreferenceScore() 
    {
        return preferenceScore;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("preferenceId", getPreferenceId())
            .append("userId", getUserId())
            .append("preferenceType", getPreferenceType())
            .append("preferenceKey", getPreferenceKey())
            .append("preferenceValue", getPreferenceValue())
            .append("preferenceScore", getPreferenceScore())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
