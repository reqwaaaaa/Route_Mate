package com.RouteMate.route.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.RouteMate.common.annotation.Excel;
import com.RouteMate.common.core.domain.BaseEntity;

/**
 * 热点数据对象 route_hotspot_zone
 * 
 * @author RouteMate
 * @date 2026-04-01
 */
public class RouteHotspotZone extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 热点ID */
    private Long hotspotId;

    /** 热点名称 */
    @Excel(name = "热点名称")
    private String hotspotName;

    /** 热点类型 */
    @Excel(name = "热点类型")
    private String hotspotType;

    /** 中心纬度 */
    @Excel(name = "中心纬度")
    private BigDecimal centerLatitude;

    /** 中心经度 */
    @Excel(name = "中心经度")
    private BigDecimal centerLongitude;

    /** 半径（米） */
    @Excel(name = "半径", readConverterExp = "米=")
    private BigDecimal radius;

    /** 访问次数 */
    @Excel(name = "访问次数")
    private Long visitCount;

    /** 独立用户数 */
    @Excel(name = "独立用户数")
    private Long uniqueUsers;

    /** 高峰时段 */
    @Excel(name = "高峰时段")
    private String peakHour;

    /** 平均停留时长（分钟） */
    @Excel(name = "平均停留时长", readConverterExp = "分=钟")
    private Long avgDuration;

    /** 关联POI（逗号分隔的ID） */
    @Excel(name = "关联POI", readConverterExp = "逗=号分隔的ID")
    private String relatedPois;

    /** 热点图片（逗号分隔的URL） */
    @Excel(name = "热点图片", readConverterExp = "逗=号分隔的URL")
    private String hotspotImages;

    /** 分析任务ID（关联分析任务表） */
    @Excel(name = "分析任务ID", readConverterExp = "关=联分析任务表")
    private String analysisTaskId;

    /** 分析任务ID（关联分析任务表）关联字段（关联route_analysis_task表） */
    private String taskName;

    public void setHotspotId(Long hotspotId) 
    {
        this.hotspotId = hotspotId;
    }

    public Long getHotspotId() 
    {
        return hotspotId;
    }

    public void setHotspotName(String hotspotName) 
    {
        this.hotspotName = hotspotName;
    }

    public String getHotspotName() 
    {
        return hotspotName;
    }

    public void setHotspotType(String hotspotType) 
    {
        this.hotspotType = hotspotType;
    }

    public String getHotspotType() 
    {
        return hotspotType;
    }

    public void setCenterLatitude(BigDecimal centerLatitude) 
    {
        this.centerLatitude = centerLatitude;
    }

    public BigDecimal getCenterLatitude() 
    {
        return centerLatitude;
    }

    public void setCenterLongitude(BigDecimal centerLongitude) 
    {
        this.centerLongitude = centerLongitude;
    }

    public BigDecimal getCenterLongitude() 
    {
        return centerLongitude;
    }

    public void setRadius(BigDecimal radius) 
    {
        this.radius = radius;
    }

    public BigDecimal getRadius() 
    {
        return radius;
    }

    public void setVisitCount(Long visitCount) 
    {
        this.visitCount = visitCount;
    }

    public Long getVisitCount() 
    {
        return visitCount;
    }

    public void setUniqueUsers(Long uniqueUsers) 
    {
        this.uniqueUsers = uniqueUsers;
    }

    public Long getUniqueUsers() 
    {
        return uniqueUsers;
    }

    public void setPeakHour(String peakHour) 
    {
        this.peakHour = peakHour;
    }

    public String getPeakHour() 
    {
        return peakHour;
    }

    public void setAvgDuration(Long avgDuration) 
    {
        this.avgDuration = avgDuration;
    }

    public Long getAvgDuration() 
    {
        return avgDuration;
    }

    public void setRelatedPois(String relatedPois) 
    {
        this.relatedPois = relatedPois;
    }

    public String getRelatedPois() 
    {
        return relatedPois;
    }

    public void setHotspotImages(String hotspotImages) 
    {
        this.hotspotImages = hotspotImages;
    }

    public String getHotspotImages() 
    {
        return hotspotImages;
    }

    public void setAnalysisTaskId(String analysisTaskId) 
    {
        this.analysisTaskId = analysisTaskId;
    }

    public String getAnalysisTaskId() 
    {
        return analysisTaskId;
    }

    public void setTaskName(String taskName) 
    {
        this.taskName = taskName;
    }

    public String getTaskName() 
    {
        return taskName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("hotspotId", getHotspotId())
            .append("hotspotName", getHotspotName())
            .append("hotspotType", getHotspotType())
            .append("centerLatitude", getCenterLatitude())
            .append("centerLongitude", getCenterLongitude())
            .append("radius", getRadius())
            .append("visitCount", getVisitCount())
            .append("uniqueUsers", getUniqueUsers())
            .append("peakHour", getPeakHour())
            .append("avgDuration", getAvgDuration())
            .append("relatedPois", getRelatedPois())
            .append("hotspotImages", getHotspotImages())
            .append("analysisTaskId", getAnalysisTaskId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
