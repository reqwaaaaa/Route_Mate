package com.RouteMate.route.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.RouteMate.common.annotation.Excel;
import com.RouteMate.common.core.domain.BaseEntity;

/**
 * 出行轨迹信息对象 route_trajectory_info
 * 
 * @author RouteMate
 * @date 2026-04-01
 */
public class RouteTrajectoryInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 轨迹ID */
    private Long trajectoryId;

    /** 用户ID（关联sys_user.user_id）（关联用户信息表） */
    @Excel(name = "用户ID", readConverterExp = "关=联sys_user.user_id")
    private Long userId;

    /** 用户ID（关联sys_user.user_id）（关联用户信息表）关联字段（关联sys_user表） */
    private String userName;

    /** 轨迹名称 */
    @Excel(name = "轨迹名称")
    private String trajectoryName;

    /** 轨迹描述 */
    @Excel(name = "轨迹描述")
    private String trajectoryDesc;

    /** 起点位置 */
    @Excel(name = "起点位置")
    private String startLocation;

    /** 终点位置 */
    @Excel(name = "终点位置")
    private String endLocation;

    /** 总距离（公里） */
    @Excel(name = "总距离", readConverterExp = "公=里")
    private BigDecimal totalDistance;

    /** 总时长（分钟） */
    @Excel(name = "总时长", readConverterExp = "分=钟")
    private Long totalDuration;

    /** 轨迹点数量 */
    @Excel(name = "轨迹点数量")
    private Long pointCount;

    /** 轨迹点数据（JSON格式） */
    @Excel(name = "轨迹点数据", readConverterExp = "J=SON格式")
    private String trajectoryData;

    /** 轨迹图片（逗号分隔的URL） */
    @Excel(name = "轨迹图片", readConverterExp = "逗=号分隔的URL")
    private String trajectoryImages;

    /** 轨迹状态（0正常 1暂停 2完成） */
    @Excel(name = "轨迹状态", readConverterExp = "0=正常,1=暂停,2=完成")
    private String trajectoryStatus;

    /** 是否公开（0否 1是） */
    @Excel(name = "是否公开", readConverterExp = "0=否,1=是")
    private String isPublic;

    /** 删除标志（0存在 2删除） */
    private String delFlag;

    public void setTrajectoryId(Long trajectoryId) 
    {
        this.trajectoryId = trajectoryId;
    }

    public Long getTrajectoryId() 
    {
        return trajectoryId;
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

    public void setTrajectoryName(String trajectoryName) 
    {
        this.trajectoryName = trajectoryName;
    }

    public String getTrajectoryName() 
    {
        return trajectoryName;
    }

    public void setTrajectoryDesc(String trajectoryDesc) 
    {
        this.trajectoryDesc = trajectoryDesc;
    }

    public String getTrajectoryDesc() 
    {
        return trajectoryDesc;
    }

    public void setStartLocation(String startLocation) 
    {
        this.startLocation = startLocation;
    }

    public String getStartLocation() 
    {
        return startLocation;
    }

    public void setEndLocation(String endLocation) 
    {
        this.endLocation = endLocation;
    }

    public String getEndLocation() 
    {
        return endLocation;
    }

    public void setTotalDistance(BigDecimal totalDistance) 
    {
        this.totalDistance = totalDistance;
    }

    public BigDecimal getTotalDistance() 
    {
        return totalDistance;
    }

    public void setTotalDuration(Long totalDuration) 
    {
        this.totalDuration = totalDuration;
    }

    public Long getTotalDuration() 
    {
        return totalDuration;
    }

    public void setPointCount(Long pointCount) 
    {
        this.pointCount = pointCount;
    }

    public Long getPointCount() 
    {
        return pointCount;
    }

    public void setTrajectoryData(String trajectoryData) 
    {
        this.trajectoryData = trajectoryData;
    }

    public String getTrajectoryData() 
    {
        return trajectoryData;
    }

    public void setTrajectoryImages(String trajectoryImages) 
    {
        this.trajectoryImages = trajectoryImages;
    }

    public String getTrajectoryImages() 
    {
        return trajectoryImages;
    }

    public void setTrajectoryStatus(String trajectoryStatus) 
    {
        this.trajectoryStatus = trajectoryStatus;
    }

    public String getTrajectoryStatus() 
    {
        return trajectoryStatus;
    }

    public void setIsPublic(String isPublic) 
    {
        this.isPublic = isPublic;
    }

    public String getIsPublic() 
    {
        return isPublic;
    }

    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("trajectoryId", getTrajectoryId())
            .append("userId", getUserId())
            .append("trajectoryName", getTrajectoryName())
            .append("trajectoryDesc", getTrajectoryDesc())
            .append("startLocation", getStartLocation())
            .append("endLocation", getEndLocation())
            .append("totalDistance", getTotalDistance())
            .append("totalDuration", getTotalDuration())
            .append("pointCount", getPointCount())
            .append("trajectoryData", getTrajectoryData())
            .append("trajectoryImages", getTrajectoryImages())
            .append("trajectoryStatus", getTrajectoryStatus())
            .append("isPublic", getIsPublic())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
