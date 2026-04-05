package com.RouteMate.route.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 轨迹点实体类
 * 用于存储GPS轨迹点数据
 */
public class TrajectoryPoint {
    
    private Long pointId;
    private Long trajectoryId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private BigDecimal altitude;
    private Float accuracy;
    private Float speed;
    private Float bearing;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timestamp;
    private String locationDesc;
    private Integer pointOrder;
    
    // Getters and Setters
    public Long getPointId() {
        return pointId;
    }
    
    public void setPointId(Long pointId) {
        this.pointId = pointId;
    }
    
    public Long getTrajectoryId() {
        return trajectoryId;
    }
    
    public void setTrajectoryId(Long trajectoryId) {
        this.trajectoryId = trajectoryId;
    }
    
    public BigDecimal getLatitude() {
        return latitude;
    }
    
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }
    
    public BigDecimal getLongitude() {
        return longitude;
    }
    
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
    
    public BigDecimal getAltitude() {
        return altitude;
    }
    
    public void setAltitude(BigDecimal altitude) {
        this.altitude = altitude;
    }
    
    public Float getAccuracy() {
        return accuracy;
    }
    
    public void setAccuracy(Float accuracy) {
        this.accuracy = accuracy;
    }
    
    public Float getSpeed() {
        return speed;
    }
    
    public void setSpeed(Float speed) {
        this.speed = speed;
    }
    
    public Float getBearing() {
        return bearing;
    }
    
    public void setBearing(Float bearing) {
        this.bearing = bearing;
    }
    
    public Date getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getLocationDesc() {
        return locationDesc;
    }
    
    public void setLocationDesc(String locationDesc) {
        this.locationDesc = locationDesc;
    }
    
    public Integer getPointOrder() {
        return pointOrder;
    }
    
    public void setPointOrder(Integer pointOrder) {
        this.pointOrder = pointOrder;
    }
}
