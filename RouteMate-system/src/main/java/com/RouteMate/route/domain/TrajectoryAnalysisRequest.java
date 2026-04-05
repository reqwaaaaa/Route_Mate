package com.RouteMate.route.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 轨迹分析请求DTO
 */
public class TrajectoryAnalysisRequest {
    
    @NotNull(message = "轨迹ID不能为空")
    private Long trajectoryId;
    
    @NotBlank(message = "算法类型不能为空")
    private String algorithmType; // sw_fp, gscm_sw, both, all
    
    private String taskName;
    private Long userId;
    
    // 算法参数
    private AlgorithmParams algorithmParams;
    
    /**
     * 算法参数配置
     */
    public static class AlgorithmParams {
        // DBSCAN参数
        private Double eps = 0.01; // 聚类半径
        private Integer minSamples = 5; // 最小样本数
        
        // NDTTT参数
        private Integer timeThreshold = 300; // 时间阈值(秒)
        private Double distanceThreshold = 100.0; // 距离阈值(米)
        
        // 模式挖掘参数
        private Double minSupport = 0.1; // 最小支持度
        private Double minConfidence = 0.8; // 最小置信度
        private Integer slidingWindow = 7; // 滑动窗口大小
        
        // 推荐参数
        private Double historyWeight = 0.6; // 历史权重
        private Double similarityWeight = 0.4; // 相似性权重
        
        // Getters and Setters
        public Double getEps() {
            return eps;
        }
        
        public void setEps(Double eps) {
            this.eps = eps;
        }
        
        public Integer getMinSamples() {
            return minSamples;
        }
        
        public void setMinSamples(Integer minSamples) {
            this.minSamples = minSamples;
        }
        
        public Integer getTimeThreshold() {
            return timeThreshold;
        }
        
        public void setTimeThreshold(Integer timeThreshold) {
            this.timeThreshold = timeThreshold;
        }
        
        public Double getDistanceThreshold() {
            return distanceThreshold;
        }
        
        public void setDistanceThreshold(Double distanceThreshold) {
            this.distanceThreshold = distanceThreshold;
        }
        
        public Double getMinSupport() {
            return minSupport;
        }
        
        public void setMinSupport(Double minSupport) {
            this.minSupport = minSupport;
        }
        
        public Double getMinConfidence() {
            return minConfidence;
        }
        
        public void setMinConfidence(Double minConfidence) {
            this.minConfidence = minConfidence;
        }
        
        public Integer getSlidingWindow() {
            return slidingWindow;
        }
        
        public void setSlidingWindow(Integer slidingWindow) {
            this.slidingWindow = slidingWindow;
        }
        
        public Double getHistoryWeight() {
            return historyWeight;
        }
        
        public void setHistoryWeight(Double historyWeight) {
            this.historyWeight = historyWeight;
        }
        
        public Double getSimilarityWeight() {
            return similarityWeight;
        }
        
        public void setSimilarityWeight(Double similarityWeight) {
            this.similarityWeight = similarityWeight;
        }
    }
    
    // Getters and Setters
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
    
    public String getTaskName() {
        return taskName;
    }
    
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public AlgorithmParams getAlgorithmParams() {
        return algorithmParams;
    }
    
    public void setAlgorithmParams(AlgorithmParams algorithmParams) {
        this.algorithmParams = algorithmParams;
    }
}
