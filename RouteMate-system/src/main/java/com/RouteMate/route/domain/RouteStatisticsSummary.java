package com.RouteMate.route.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.RouteMate.common.annotation.Excel;
import com.RouteMate.common.core.domain.BaseEntity;

/**
 * 统计数据对象 route_statistics_summary
 *
 * @author RouteMate
 * @date 2026-04-01
 */
public class RouteStatisticsSummary extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 统计ID */
    private Long statId;

    /** 统计类型（POI HOTSPOT USER TRAJECTORY） */
    @Excel(name = "统计类型", readConverterExp = "P=OI,H=OTSPOT,U=SER,T=RAJECTORY")
    private String statType;

    /** 统计键 */
    @Excel(name = "统计键")
    private String statKey;

    /** 统计值 */
    @Excel(name = "统计值")
    private BigDecimal statValue;

    /** 统计数量 */
    @Excel(name = "统计数量")
    private Long statCount;

    /** 统计日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "统计日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date statDate;

    /** 统计周期（DAILY WEEKLY MONTHLY） */
    @Excel(name = "统计周期", readConverterExp = "D=AILY,W=EEKLY,M=ONTHLY")
    private String statPeriod;

    /** 扩展数据（JSON格式） */
    @Excel(name = "扩展数据", readConverterExp = "J=SON格式")
    private String extraData;

    public void setStatId(Long statId)
    {
        this.statId = statId;
    }

    public Long getStatId()
    {
        return statId;
    }

    public void setStatType(String statType)
    {
        this.statType = statType;
    }

    public String getStatType()
    {
        return statType;
    }

    public void setStatKey(String statKey)
    {
        this.statKey = statKey;
    }

    public String getStatKey()
    {
        return statKey;
    }

    public void setStatValue(BigDecimal statValue)
    {
        this.statValue = statValue;
    }

    public BigDecimal getStatValue()
    {
        return statValue;
    }

    public void setStatCount(Long statCount)
    {
        this.statCount = statCount;
    }

    public Long getStatCount()
    {
        return statCount;
    }

    public void setStatDate(Date statDate)
    {
        this.statDate = statDate;
    }

    public Date getStatDate()
    {
        return statDate;
    }

    public void setStatPeriod(String statPeriod)
    {
        this.statPeriod = statPeriod;
    }

    public String getStatPeriod()
    {
        return statPeriod;
    }

    public void setExtraData(String extraData)
    {
        this.extraData = extraData;
    }

    public String getExtraData()
    {
        return extraData;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("statId", getStatId())
            .append("statType", getStatType())
            .append("statKey", getStatKey())
            .append("statValue", getStatValue())
            .append("statCount", getStatCount())
            .append("statDate", getStatDate())
            .append("statPeriod", getStatPeriod())
            .append("extraData", getExtraData())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
