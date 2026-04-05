package com.RouteMate.route.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.RouteMate.common.annotation.Excel;
import com.RouteMate.common.core.domain.BaseEntity;

/**
 * POI信息对象 route_poi_detail
 * 
 * @author RouteMate
 * @date 2026-04-01
 */
public class RoutePoiDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** POI ID */
    private Long poiId;

    /** POI名称 */
    @Excel(name = "POI名称")
    private String poiName;

    /** POI类型 */
    @Excel(name = "POI类型")
    private String poiType;

    /** POI分类 */
    @Excel(name = "POI分类")
    private String poiCategory;

    /** 纬度 */
    @Excel(name = "纬度")
    private BigDecimal latitude;

    /** 经度 */
    @Excel(name = "经度")
    private BigDecimal longitude;

    /** 详细地址 */
    @Excel(name = "详细地址")
    private String address;

    /** POI描述 */
    @Excel(name = "POI描述")
    private String poiDesc;

    /** POI图片（逗号分隔的URL） */
    @Excel(name = "POI图片", readConverterExp = "逗=号分隔的URL")
    private String poiImages;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String contactPhone;

    /** 营业时间 */
    @Excel(name = "营业时间")
    private String businessHours;

    /** 评分（0-5分） */
    @Excel(name = "评分", readConverterExp = "0=-5分")
    private BigDecimal rating;

    /** 贡献者ID（关联sys_user.user_id）（关联用户信息表） */
    @Excel(name = "贡献者ID", readConverterExp = "关=联sys_user.user_id")
    private Long contributorId;

    /** 贡献者ID（关联sys_user.user_id）（关联用户信息表）关联字段（关联sys_user表） */
    private String userName;

    /** 数据来源（USER用户贡献，AMAP高德地图） */
    @Excel(name = "数据来源", readConverterExp = "U=SER用户贡献，AMAP高德地图")
    private String dataSource;

    /** 审核状态（0待审核 1通过 2拒绝） */
    @Excel(name = "审核状态", readConverterExp = "0=待审核,1=通过,2=拒绝")
    private String auditStatus;

    /** 审核人ID（关联用户信息表） */
    @Excel(name = "审核人ID", readConverterExp = "关=联用户信息表")
    private Long auditBy;

    /** 审核人ID（关联用户信息表）关联字段（关联sys_user表） */
    private String nickName;

    /** 审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    /** 审核备注 */
    @Excel(name = "审核备注")
    private String auditRemark;

    /** 访问次数 */
    @Excel(name = "访问次数")
    private Long visitCount;

    /** 点赞次数 */
    @Excel(name = "点赞次数")
    private Long likeCount;

    /** 删除标志（0存在 2删除） */
    private String delFlag;

    public void setPoiId(Long poiId) 
    {
        this.poiId = poiId;
    }

    public Long getPoiId() 
    {
        return poiId;
    }

    public void setPoiName(String poiName) 
    {
        this.poiName = poiName;
    }

    public String getPoiName() 
    {
        return poiName;
    }

    public void setPoiType(String poiType) 
    {
        this.poiType = poiType;
    }

    public String getPoiType() 
    {
        return poiType;
    }

    public void setPoiCategory(String poiCategory) 
    {
        this.poiCategory = poiCategory;
    }

    public String getPoiCategory() 
    {
        return poiCategory;
    }

    public void setLatitude(BigDecimal latitude) 
    {
        this.latitude = latitude;
    }

    public BigDecimal getLatitude() 
    {
        return latitude;
    }

    public void setLongitude(BigDecimal longitude) 
    {
        this.longitude = longitude;
    }

    public BigDecimal getLongitude() 
    {
        return longitude;
    }

    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }

    public void setPoiDesc(String poiDesc) 
    {
        this.poiDesc = poiDesc;
    }

    public String getPoiDesc() 
    {
        return poiDesc;
    }

    public void setPoiImages(String poiImages) 
    {
        this.poiImages = poiImages;
    }

    public String getPoiImages() 
    {
        return poiImages;
    }

    public void setContactPhone(String contactPhone) 
    {
        this.contactPhone = contactPhone;
    }

    public String getContactPhone() 
    {
        return contactPhone;
    }

    public void setBusinessHours(String businessHours) 
    {
        this.businessHours = businessHours;
    }

    public String getBusinessHours() 
    {
        return businessHours;
    }

    public void setRating(BigDecimal rating) 
    {
        this.rating = rating;
    }

    public BigDecimal getRating() 
    {
        return rating;
    }

    public void setContributorId(Long contributorId) 
    {
        this.contributorId = contributorId;
    }

    public Long getContributorId() 
    {
        return contributorId;
    }

    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }

    public void setDataSource(String dataSource) 
    {
        this.dataSource = dataSource;
    }

    public String getDataSource() 
    {
        return dataSource;
    }

    public void setAuditStatus(String auditStatus) 
    {
        this.auditStatus = auditStatus;
    }

    public String getAuditStatus() 
    {
        return auditStatus;
    }

    public void setAuditBy(Long auditBy) 
    {
        this.auditBy = auditBy;
    }

    public Long getAuditBy() 
    {
        return auditBy;
    }

    public void setNickName(String nickName) 
    {
        this.nickName = nickName;
    }

    public String getNickName() 
    {
        return nickName;
    }

    public void setAuditTime(Date auditTime) 
    {
        this.auditTime = auditTime;
    }

    public Date getAuditTime() 
    {
        return auditTime;
    }

    public void setAuditRemark(String auditRemark) 
    {
        this.auditRemark = auditRemark;
    }

    public String getAuditRemark() 
    {
        return auditRemark;
    }

    public void setVisitCount(Long visitCount) 
    {
        this.visitCount = visitCount;
    }

    public Long getVisitCount() 
    {
        return visitCount;
    }

    public void setLikeCount(Long likeCount) 
    {
        this.likeCount = likeCount;
    }

    public Long getLikeCount() 
    {
        return likeCount;
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
            .append("poiId", getPoiId())
            .append("poiName", getPoiName())
            .append("poiType", getPoiType())
            .append("poiCategory", getPoiCategory())
            .append("latitude", getLatitude())
            .append("longitude", getLongitude())
            .append("address", getAddress())
            .append("poiDesc", getPoiDesc())
            .append("poiImages", getPoiImages())
            .append("contactPhone", getContactPhone())
            .append("businessHours", getBusinessHours())
            .append("rating", getRating())
            .append("contributorId", getContributorId())
            .append("dataSource", getDataSource())
            .append("auditStatus", getAuditStatus())
            .append("auditBy", getAuditBy())
            .append("auditTime", getAuditTime())
            .append("auditRemark", getAuditRemark())
            .append("visitCount", getVisitCount())
            .append("likeCount", getLikeCount())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
