package com.RouteMate.route.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 轨迹分析结果DTO
 */
public class TrajectoryAnalysisResult {
    
    private String taskId;
    private String taskName;
    private Long trajectoryId;
    private String algorithmType;
    private String taskStatus; // 0待处理 1处理中 2已完成 3失败 4已取消
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    
    private Integer progressPercent;
    private Integer executionTime;
    private String errorMessage;
    
    // 分析结果数据
    private AnalysisData analysisData;
    
    /**
     * 分析结果数据
     */
    public static class AnalysisData {
        private List<HotspotInfo> hotspots;
        private SemanticTrajectoryInfo semanticTrajectory;
        private List<POIInfo> pois;
        private PatternMiningResult patterns;
        private List<RouteRecommendation> recommendations;
        private StatisticsInfo statistics;
        
        // Getters and Setters
        public List<HotspotInfo> getHotspots() {
            return hotspots;
        }
        
        public void setHotspots(List<HotspotInfo> hotspots) {
            this.hotspots = hotspots;
        }
        
        public SemanticTrajectoryInfo getSemanticTrajectory() {
            return semanticTrajectory;
        }
        
        public void setSemanticTrajectory(SemanticTrajectoryInfo semanticTrajectory) {
            this.semanticTrajectory = semanticTrajectory;
        }
        
        public List<POIInfo> getPois() {
            return pois;
        }
        
        public void setPois(List<POIInfo> pois) {
            this.pois = pois;
        }
        
        public PatternMiningResult getPatterns() {
            return patterns;
        }
        
        public void setPatterns(PatternMiningResult patterns) {
            this.patterns = patterns;
        }
        
        public List<RouteRecommendation> getRecommendations() {
            return recommendations;
        }
        
        public void setRecommendations(List<RouteRecommendation> recommendations) {
            this.recommendations = recommendations;
        }
        
        public StatisticsInfo getStatistics() {
            return statistics;
        }
        
        public void setStatistics(StatisticsInfo statistics) {
            this.statistics = statistics;
        }
    }
    
    /**
     * 热点信息
     */
    public static class HotspotInfo {
        private String hotspotName;
        private String hotspotType;
        private Double centerLatitude;
        private Double centerLongitude;
        private Double radius;
        private Integer visitCount;
        private Integer uniqueUsers;
        private String peakHour;
        private Integer avgDuration;
        private List<String> relatedPois;
        
        // Getters and Setters
        public String getHotspotName() {
            return hotspotName;
        }
        
        public void setHotspotName(String hotspotName) {
            this.hotspotName = hotspotName;
        }
        
        public String getHotspotType() {
            return hotspotType;
        }
        
        public void setHotspotType(String hotspotType) {
            this.hotspotType = hotspotType;
        }
        
        public Double getCenterLatitude() {
            return centerLatitude;
        }
        
        public void setCenterLatitude(Double centerLatitude) {
            this.centerLatitude = centerLatitude;
        }
        
        public Double getCenterLongitude() {
            return centerLongitude;
        }
        
        public void setCenterLongitude(Double centerLongitude) {
            this.centerLongitude = centerLongitude;
        }
        
        public Double getRadius() {
            return radius;
        }
        
        public void setRadius(Double radius) {
            this.radius = radius;
        }
        
        public Integer getVisitCount() {
            return visitCount;
        }
        
        public void setVisitCount(Integer visitCount) {
            this.visitCount = visitCount;
        }
        
        public Integer getUniqueUsers() {
            return uniqueUsers;
        }
        
        public void setUniqueUsers(Integer uniqueUsers) {
            this.uniqueUsers = uniqueUsers;
        }
        
        public String getPeakHour() {
            return peakHour;
        }
        
        public void setPeakHour(String peakHour) {
            this.peakHour = peakHour;
        }
        
        public Integer getAvgDuration() {
            return avgDuration;
        }
        
        public void setAvgDuration(Integer avgDuration) {
            this.avgDuration = avgDuration;
        }
        
        public List<String> getRelatedPois() {
            return relatedPois;
        }
        
        public void setRelatedPois(List<String> relatedPois) {
            this.relatedPois = relatedPois;
        }
    }
    
    /**
     * 语义轨迹信息
     */
    public static class SemanticTrajectoryInfo {
        private List<StayPoint> stayPoints;
        private List<String> semanticSequence;
        private String nextDestination;
        private Double confidence;
        
        // Getters and Setters
        public List<StayPoint> getStayPoints() {
            return stayPoints;
        }
        
        public void setStayPoints(List<StayPoint> stayPoints) {
            this.stayPoints = stayPoints;
        }
        
        public List<String> getSemanticSequence() {
            return semanticSequence;
        }
        
        public void setSemanticSequence(List<String> semanticSequence) {
            this.semanticSequence = semanticSequence;
        }
        
        public String getNextDestination() {
            return nextDestination;
        }
        
        public void setNextDestination(String nextDestination) {
            this.nextDestination = nextDestination;
        }
        
        public Double getConfidence() {
            return confidence;
        }
        
        public void setConfidence(Double confidence) {
            this.confidence = confidence;
        }
    }
    
    /**
     * 停留点
     */
    public static class StayPoint {
        private Double latitude;
        private Double longitude;
        private String locationType;
        private String startTime;
        private String endTime;
        private Integer duration;
        
        // Getters and Setters
        public Double getLatitude() {
            return latitude;
        }
        
        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }
        
        public Double getLongitude() {
            return longitude;
        }
        
        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }
        
        public String getLocationType() {
            return locationType;
        }
        
        public void setLocationType(String locationType) {
            this.locationType = locationType;
        }
        
        public String getStartTime() {
            return startTime;
        }
        
        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }
        
        public String getEndTime() {
            return endTime;
        }
        
        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }
        
        public Integer getDuration() {
            return duration;
        }
        
        public void setDuration(Integer duration) {
            this.duration = duration;
        }
    }
    
    /**
     * POI信息
     */
    public static class POIInfo {
        private String poiName;
        private String poiType;
        private Double latitude;
        private Double longitude;
        private String address;
        private Double rating;
        private Integer visitFrequency;
        
        // Getters and Setters
        public String getPoiName() {
            return poiName;
        }
        
        public void setPoiName(String poiName) {
            this.poiName = poiName;
        }
        
        public String getPoiType() {
            return poiType;
        }
        
        public void setPoiType(String poiType) {
            this.poiType = poiType;
        }
        
        public Double getLatitude() {
            return latitude;
        }
        
        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }
        
        public Double getLongitude() {
            return longitude;
        }
        
        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }
        
        public String getAddress() {
            return address;
        }
        
        public void setAddress(String address) {
            this.address = address;
        }
        
        public Double getRating() {
            return rating;
        }
        
        public void setRating(Double rating) {
            this.rating = rating;
        }
        
        public Integer getVisitFrequency() {
            return visitFrequency;
        }
        
        public void setVisitFrequency(Integer visitFrequency) {
            this.visitFrequency = visitFrequency;
        }
    }
    
    /**
     * 模式挖掘结果
     */
    public static class PatternMiningResult {
        private List<PatternInfo> swFpPatterns;
        private List<PatternInfo> gscmPatterns;
        
        // Getters and Setters
        public List<PatternInfo> getSwFpPatterns() {
            return swFpPatterns;
        }
        
        public void setSwFpPatterns(List<PatternInfo> swFpPatterns) {
            this.swFpPatterns = swFpPatterns;
        }
        
        public List<PatternInfo> getGscmPatterns() {
            return gscmPatterns;
        }
        
        public void setGscmPatterns(List<PatternInfo> gscmPatterns) {
            this.gscmPatterns = gscmPatterns;
        }
    }
    
    /**
     * 模式信息
     */
    public static class PatternInfo {
        private List<String> pattern;
        private Double support;
        private Double confidence;
        private String algorithm;
        
        // Getters and Setters
        public List<String> getPattern() {
            return pattern;
        }
        
        public void setPattern(List<String> pattern) {
            this.pattern = pattern;
        }
        
        public Double getSupport() {
            return support;
        }
        
        public void setSupport(Double support) {
            this.support = support;
        }
        
        public Double getConfidence() {
            return confidence;
        }
        
        public void setConfidence(Double confidence) {
            this.confidence = confidence;
        }
        
        public String getAlgorithm() {
            return algorithm;
        }
        
        public void setAlgorithm(String algorithm) {
            this.algorithm = algorithm;
        }
    }
    
    /**
     * 路径推荐
     */
    public static class RouteRecommendation {
        private List<String> routePoints;
        private Double distance;
        private Integer estimatedTime;
        private Double score;
        private String explanation;
        private List<String> features;
        
        // Getters and Setters
        public List<String> getRoutePoints() {
            return routePoints;
        }
        
        public void setRoutePoints(List<String> routePoints) {
            this.routePoints = routePoints;
        }
        
        public Double getDistance() {
            return distance;
        }
        
        public void setDistance(Double distance) {
            this.distance = distance;
        }
        
        public Integer getEstimatedTime() {
            return estimatedTime;
        }
        
        public void setEstimatedTime(Integer estimatedTime) {
            this.estimatedTime = estimatedTime;
        }
        
        public Double getScore() {
            return score;
        }
        
        public void setScore(Double score) {
            this.score = score;
        }
        
        public String getExplanation() {
            return explanation;
        }
        
        public void setExplanation(String explanation) {
            this.explanation = explanation;
        }
        
        public List<String> getFeatures() {
            return features;
        }
        
        public void setFeatures(List<String> features) {
            this.features = features;
        }
    }
    
    /**
     * 统计信息
     */
    public static class StatisticsInfo {
        private Integer totalPoints;
        private Integer totalHotspots;
        private Integer totalPatterns;
        private Integer dataQualityScore;
        private Double analysisDuration;
        
        // Getters and Setters
        public Integer getTotalPoints() {
            return totalPoints;
        }
        
        public void setTotalPoints(Integer totalPoints) {
            this.totalPoints = totalPoints;
        }
        
        public Integer getTotalHotspots() {
            return totalHotspots;
        }
        
        public void setTotalHotspots(Integer totalHotspots) {
            this.totalHotspots = totalHotspots;
        }
        
        public Integer getTotalPatterns() {
            return totalPatterns;
        }
        
        public void setTotalPatterns(Integer totalPatterns) {
            this.totalPatterns = totalPatterns;
        }
        
        public Integer getDataQualityScore() {
            return dataQualityScore;
        }
        
        public void setDataQualityScore(Integer dataQualityScore) {
            this.dataQualityScore = dataQualityScore;
        }
        
        public Double getAnalysisDuration() {
            return analysisDuration;
        }
        
        public void setAnalysisDuration(Double analysisDuration) {
            this.analysisDuration = analysisDuration;
        }
    }
    
    // Getters and Setters
    public String getTaskId() {
        return taskId;
    }
    
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    
    public String getTaskName() {
        return taskName;
    }
    
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    
    public Long getTrajectoryId() {
        return trajectoryId;
    }
    
    public void setTrajectoryId(Long trajectoryId) {
        this.trajectoryId = trajectoryId;
    }
    
    public String getAlgorithmType() {
        return algorithmType;
    }
    
    public void setAlgorithmType(String algorithmType) {
        this.algorithmType = algorithmType;
    }
    
    public String getTaskStatus() {
        return taskStatus;
    }
    
    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public Date getStartTime() {
        return startTime;
    }
    
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    
    public Date getEndTime() {
        return endTime;
    }
    
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    public Integer getProgressPercent() {
        return progressPercent;
    }
    
    public void setProgressPercent(Integer progressPercent) {
        this.progressPercent = progressPercent;
    }
    
    public Integer getExecutionTime() {
        return executionTime;
    }
    
    public void setExecutionTime(Integer executionTime) {
        this.executionTime = executionTime;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public AnalysisData getAnalysisData() {
        return analysisData;
    }
    
    public void setAnalysisData(AnalysisData analysisData) {
        this.analysisData = analysisData;
    }
}
