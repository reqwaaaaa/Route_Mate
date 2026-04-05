package com.RouteMate.route.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.RouteMate.route.domain.TrajectoryAnalysisRequest;
import com.RouteMate.route.domain.TrajectoryAnalysisResult;
import com.RouteMate.route.mapper.RouteTrajectoryMapper;
import com.RouteMate.route.service.ITrajectoryAnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 轨迹分析服务实现类
 */
@Service
public class TrajectoryAnalysisServiceImpl implements ITrajectoryAnalysisService {
    
    private static final Logger logger = LoggerFactory.getLogger(TrajectoryAnalysisServiceImpl.class);
    
    @Autowired
    private RouteTrajectoryMapper routeTrajectoryMapper;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Value("${analysis.service.url:http://localhost:5000}")
    private String analysisServiceUrl;
    
    // 线程池用于异步执行分析任务
    private final ExecutorService executorService = Executors.newFixedThreadPool(5);
    
    @Override
    @Transactional
    public TrajectoryAnalysisResult executeAnalysis(TrajectoryAnalysisRequest request) {
        logger.info("开始执行轨迹分析，轨迹ID: {}, 算法类型: {}", request.getTrajectoryId(), request.getAlgorithmType());
        
        try {
            // 1. 生成任务ID
            String taskId = UUID.randomUUID().toString().replace("-", "");
            
            // 2. 验证轨迹是否存在
            Map<String, Object> trajectoryInfo = routeTrajectoryMapper.getTrajectoryInfo(request.getTrajectoryId());
            if (trajectoryInfo == null) {
                throw new RuntimeException("轨迹不存在或已被删除");
            }
            
            // 3. 创建分析任务记录
            String taskName = request.getTaskName() != null ? request.getTaskName() : 
                            "轨迹分析_" + trajectoryInfo.get("trajectory_name");
            String algorithmParams = objectMapper.writeValueAsString(request.getAlgorithmParams());
            
            routeTrajectoryMapper.insertAnalysisTask(
                taskId, taskName, request.getUserId(), request.getTrajectoryId(),
                request.getAlgorithmType(), algorithmParams, "admin"
            );
            
            // 4. 获取轨迹数据
            String trajectoryDataJson = routeTrajectoryMapper.getTrajectoryData(request.getTrajectoryId());
            if (trajectoryDataJson == null || trajectoryDataJson.trim().isEmpty()) {
                throw new RuntimeException("轨迹数据为空");
            }
            
            // 5. 异步调用分析服务
            logger.info("准备启动异步分析任务: {}", taskId);
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    logger.info("异步任务线程开始执行: {}", taskId);
                    executeAnalysisAsync(taskId, trajectoryDataJson, request);
                } catch (Exception e) {
                    logger.error("异步分析任务异常: {}", taskId, e);
                    // 更新任务失败状态
                    try {
                        routeTrajectoryMapper.updateAnalysisTask(taskId, "3", 0, null, e.getMessage(), 0);
                        logger.info("已更新任务失败状态: {}", taskId);
                    } catch (Exception updateException) {
                        logger.error("更新任务失败状态时出错: {}", taskId, updateException);
                    }
                }
            }, executorService);
            
            // 添加回调来监控异步任务状态
            future.whenComplete((result, throwable) -> {
                if (throwable != null) {
                    logger.error("异步任务执行失败: {}", taskId, throwable);
                } else {
                    logger.info("异步任务执行完成: {}", taskId);
                }
            });
            
            logger.info("异步分析任务已提交: {}", taskId);
            
            // 6. 返回任务信息
            TrajectoryAnalysisResult result = new TrajectoryAnalysisResult();
            result.setTaskId(taskId);
            result.setTaskName(taskName);
            result.setTrajectoryId(request.getTrajectoryId());
            result.setAlgorithmType(request.getAlgorithmType());
            result.setTaskStatus("1"); // 处理中
            result.setProgressPercent(0);
            result.setCreateTime(new Date());
            
            return result;
            
        } catch (Exception e) {
            logger.error("执行轨迹分析失败", e);
            throw new RuntimeException("执行轨迹分析失败: " + e.getMessage());
        }
    }
    
    /**
     * 将时间对象转换为Date类型
     * 支持LocalDateTime和Date类型
     */
    private Date convertToDate(Object timeObj) {
        if (timeObj == null) {
            return null;
        }
        if (timeObj instanceof LocalDateTime) {
            return Date.from(((LocalDateTime) timeObj).atZone(ZoneId.systemDefault()).toInstant());
        } else if (timeObj instanceof Date) {
            return (Date) timeObj;
        }
        return null;
    }
    
    /**
     * 异步执行分析
     */
    private void executeAnalysisAsync(String taskId, String trajectoryDataJson, TrajectoryAnalysisRequest request) {
        logger.info("开始异步分析任务: {}", taskId);
        
        try {
            // 更新任务状态为处理中
            logger.info("更新任务状态为处理中: {}", taskId);
            routeTrajectoryMapper.updateAnalysisTask(taskId, "1", 10, null, null, null);
            
            // 解析轨迹数据
            logger.info("开始解析轨迹数据: {}", taskId);
            List<Map<String, Object>> trajectoryPoints = parseTrajectoryData(trajectoryDataJson);
            logger.info("轨迹数据解析完成，点数: {}", trajectoryPoints.size());
            
            // 更新进度
            routeTrajectoryMapper.updateAnalysisTask(taskId, "1", 30, null, null, null);
            logger.info("进度更新到30%: {}", taskId);
            
            // 调用Python分析服务
            logger.info("开始调用Python分析服务: {}", taskId);
            Map<String, Object> analysisResult = callPythonAnalysisService(trajectoryPoints, request.getAlgorithmType());
            logger.info("Python分析服务调用完成: {}", taskId);
            
            // 更新进度
            routeTrajectoryMapper.updateAnalysisTask(taskId, "1", 80, null, null, null);
            logger.info("进度更新到80%: {}", taskId);
            
            // 保存热点数据
            if (analysisResult.containsKey("hotspots")) {
                logger.info("开始保存热点数据: {}", taskId);
                saveHotspots(taskId, (List<Map<String, Object>>) analysisResult.get("hotspots"));
                logger.info("热点数据保存完成: {}", taskId);
            }
            
            // 更新任务完成状态
            String resultJson = objectMapper.writeValueAsString(analysisResult);
            routeTrajectoryMapper.updateAnalysisTask(taskId, "2", 100, resultJson, null, 300);
            logger.info("分析任务完成: {}", taskId);
            
        } catch (Exception e) {
            logger.error("异步分析任务失败: {}", taskId, e);
            // 更新任务失败状态
            try {
                routeTrajectoryMapper.updateAnalysisTask(taskId, "3", 0, null, e.getMessage(), 0);
                logger.info("已更新任务失败状态: {}", taskId);
            } catch (Exception updateException) {
                logger.error("更新任务失败状态时出错: {}", taskId, updateException);
            }
        }
    }
    
    /**
     * 解析轨迹数据
     */
    private List<Map<String, Object>> parseTrajectoryData(String trajectoryDataJson) throws Exception {
        try {
            // 尝试解析为轨迹点数组
            List<Map<String, Object>> points = objectMapper.readValue(trajectoryDataJson, new TypeReference<List<Map<String, Object>>>() {});
            
            // 映射字段名以确保与Python分析服务兼容
            return mapTrajectoryFields(points);
            
        } catch (Exception e) {
            // 如果解析失败，尝试解析为包含points字段的对象
            Map<String, Object> data = objectMapper.readValue(trajectoryDataJson, new TypeReference<Map<String, Object>>() {});
            if (data.containsKey("points")) {
                List<Map<String, Object>> points = (List<Map<String, Object>>) data.get("points");
                return mapTrajectoryFields(points);
            }
            throw new RuntimeException("轨迹数据格式不正确");
        }
    }
    
    /**
     * 映射轨迹字段名
     * 将数据库字段映射为Python分析服务期望的字段
     */
    private List<Map<String, Object>> mapTrajectoryFields(List<Map<String, Object>> points) {
        List<Map<String, Object>> mappedPoints = new ArrayList<>();
        
        logger.info("开始映射轨迹字段，原始数据点数: {}", points.size());
        
        // 如果有数据，打印第一个点的字段名用于调试
        if (!points.isEmpty()) {
            Map<String, Object> firstPoint = points.get(0);
            logger.info("原始轨迹数据字段: {}", firstPoint.keySet());
        }
        
        for (Map<String, Object> point : points) {
            Map<String, Object> mappedPoint = new HashMap<>();
            
            // 映射纬度字段
            if (point.containsKey("latitude")) {
                mappedPoint.put("latitude", point.get("latitude"));
            } else if (point.containsKey("lat")) {
                mappedPoint.put("latitude", point.get("lat"));
            } else if (point.containsKey("纬度")) {
                mappedPoint.put("latitude", point.get("纬度"));
            }
            
            // 映射经度字段
            if (point.containsKey("longitude")) {
                mappedPoint.put("longitude", point.get("longitude"));
            } else if (point.containsKey("lng")) {
                mappedPoint.put("longitude", point.get("lng"));
            } else if (point.containsKey("lon")) {
                mappedPoint.put("longitude", point.get("lon"));
            } else if (point.containsKey("经度")) {
                mappedPoint.put("longitude", point.get("经度"));
            }
            
            // 映射时间字段
            if (point.containsKey("timestamp")) {
                mappedPoint.put("timestamp", point.get("timestamp"));
            } else if (point.containsKey("time")) {
                mappedPoint.put("timestamp", point.get("time"));
            } else if (point.containsKey("时间")) {
                mappedPoint.put("timestamp", point.get("时间"));
            }
            
            // 映射精度字段（可选）
            if (point.containsKey("accuracy")) {
                mappedPoint.put("accuracy", point.get("accuracy"));
            } else if (point.containsKey("精度")) {
                mappedPoint.put("accuracy", point.get("精度"));
            }
            
            // 复制其他可能有用的字段
            for (Map.Entry<String, Object> entry : point.entrySet()) {
                String key = entry.getKey();
                if (!mappedPoint.containsKey(key) && 
                    !key.equals("latitude") && !key.equals("lat") && !key.equals("纬度") &&
                    !key.equals("longitude") && !key.equals("lng") && !key.equals("lon") && !key.equals("经度") &&
                    !key.equals("timestamp") && !key.equals("time") && !key.equals("时间")) {
                    mappedPoint.put(key, entry.getValue());
                }
            }
            
            mappedPoints.add(mappedPoint);
        }
        
        // 打印映射后的第一个点用于调试
        if (!mappedPoints.isEmpty()) {
            Map<String, Object> firstMappedPoint = mappedPoints.get(0);
            logger.info("映射后轨迹数据字段: {}", firstMappedPoint.keySet());
            logger.info("映射后第一个点数据: {}", firstMappedPoint);
        }
        
        // 验证映射后的数据是否包含必要字段
        for (int i = 0; i < mappedPoints.size(); i++) {
            Map<String, Object> point = mappedPoints.get(i);
            if (!point.containsKey("latitude") || !point.containsKey("longitude") || !point.containsKey("timestamp")) {
                logger.error("映射后的第{}个点缺少必要字段: {}", i, point.keySet());
                throw new RuntimeException("映射后的轨迹数据缺少必要字段: latitude, longitude, timestamp");
            }
        }
        
        logger.info("轨迹字段映射完成，验证通过");
        return mappedPoints;
    }
    
    /**
     * 调用Python分析服务
     */
    private Map<String, Object> callPythonAnalysisService(List<Map<String, Object>> trajectoryPoints, String algorithmType) throws Exception {
        logger.info("调用Python分析服务，数据点数: {}, 算法类型: {}", trajectoryPoints.size(), algorithmType);
        
        try {
            // 首先检查Python服务是否可用
            URL healthCheckUrl = new URL(analysisServiceUrl + "/health");
            HttpURLConnection healthConnection = (HttpURLConnection) healthCheckUrl.openConnection();
            healthConnection.setRequestMethod("GET");
            healthConnection.setConnectTimeout(5000);
            healthConnection.setReadTimeout(5000);
            
            int healthResponseCode = healthConnection.getResponseCode();
            if (healthResponseCode != 200) {
                logger.warn("Python分析服务健康检查失败，响应码: {}", healthResponseCode);
                throw new RuntimeException("Python分析服务不可用");
            }
            logger.info("Python分析服务健康检查通过");
            healthConnection.disconnect();
            
            // 准备请求数据
            Map<String, Object> requestData = new HashMap<>();
            requestData.put("raw_data_path", "database_data");
            requestData.put("algorithm", algorithmType);
            requestData.put("trajectory_data", trajectoryPoints);
            
            // 发送HTTP请求
            URL url = new URL(analysisServiceUrl + "/analysis/run");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(120000);
            connection.setDoOutput(true);
            
            // 发送请求数据
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = objectMapper.writeValueAsString(requestData).getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            
            // 获取响应
            int responseCode = connection.getResponseCode();
            logger.info("Python分析服务响应码: {}", responseCode);
            
            if (responseCode == 200) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    
                    logger.info("Python分析服务响应: {}", response.toString());
                    
                    Map<String, Object> result = objectMapper.readValue(response.toString(), new TypeReference<Map<String, Object>>() {});
                    
                    if (Boolean.TRUE.equals(result.get("success"))) {
                        return (Map<String, Object>) result.get("data");
                    } else {
                        throw new RuntimeException("Python分析服务返回错误: " + result.get("message"));
                    }
                }
            } else {
                throw new RuntimeException("Python分析服务调用失败，响应码: " + responseCode);
            }
            
        } catch (Exception e) {
            logger.error("调用Python分析服务异常", e);
            throw new RuntimeException("调用Python分析服务失败: " + e.getMessage());
        }
    }
    
    /**
     * 保存热点数据
     */
    private void saveHotspots(String taskId, List<Map<String, Object>> hotspots) {
        try {
            for (Map<String, Object> hotspot : hotspots) {
                routeTrajectoryMapper.insertHotspot(
                    (String) hotspot.get("hotspot_name"),
                    (String) hotspot.get("hotspot_type"),
                    (Double) hotspot.get("center_latitude"),
                    (Double) hotspot.get("center_longitude"),
                    (Double) hotspot.get("radius"),
                    (Integer) hotspot.get("visit_count"),
                    (Integer) hotspot.get("unique_users"),
                    (String) hotspot.get("peak_hour"),
                    (Integer) hotspot.get("avg_duration"),
                    objectMapper.writeValueAsString(hotspot.get("related_pois")),
                    taskId,
                    "system"
                );
            }
        } catch (Exception e) {
            logger.error("保存热点数据失败", e);
        }
    }
    
    @Override
    public TrajectoryAnalysisResult getAnalysisResult(String taskId) {
        logger.info("获取分析结果，任务ID: {}", taskId);
        
        try {
            Map<String, Object> taskInfo = routeTrajectoryMapper.getAnalysisTask(taskId);
            if (taskInfo == null) {
                throw new RuntimeException("任务不存在");
            }
            
            TrajectoryAnalysisResult result = new TrajectoryAnalysisResult();
            result.setTaskId(taskId);
            result.setTaskName((String) taskInfo.get("task_name"));
            result.setTrajectoryId(((Number) taskInfo.get("trajectory_id")).longValue());
            result.setAlgorithmType((String) taskInfo.get("algorithm_type"));
            result.setTaskStatus((String) taskInfo.get("task_status"));
            result.setProgressPercent((Integer) taskInfo.get("progress_percent"));
            result.setExecutionTime((Integer) taskInfo.get("execution_time"));
            result.setErrorMessage((String) taskInfo.get("error_message"));
            
            // 设置时间 - 使用辅助方法处理时间转换
            result.setCreateTime(convertToDate(taskInfo.get("create_time")));
            result.setStartTime(convertToDate(taskInfo.get("start_time")));
            result.setEndTime(convertToDate(taskInfo.get("end_time")));
            
            // 解析分析结果
            String resultData = (String) taskInfo.get("result_data");
            if (resultData != null && !resultData.trim().isEmpty()) {
                Map<String, Object> analysisData = objectMapper.readValue(resultData, new TypeReference<Map<String, Object>>() {});
                result.setAnalysisData(convertToAnalysisData(analysisData));
            }
            
            return result;
            
        } catch (Exception e) {
            logger.error("获取分析结果失败", e);
            throw new RuntimeException("获取分析结果失败: " + e.getMessage());
        }
    }
    
    /**
     * 转换分析数据格式
     */
    private TrajectoryAnalysisResult.AnalysisData convertToAnalysisData(Map<String, Object> data) {
        TrajectoryAnalysisResult.AnalysisData analysisData = new TrajectoryAnalysisResult.AnalysisData();
        
        // 转换热点数据
        if (data.containsKey("hotspots")) {
            List<Map<String, Object>> hotspots = (List<Map<String, Object>>) data.get("hotspots");
            List<TrajectoryAnalysisResult.HotspotInfo> hotspotInfos = new ArrayList<>();
            for (Map<String, Object> hotspot : hotspots) {
                TrajectoryAnalysisResult.HotspotInfo hotspotInfo = new TrajectoryAnalysisResult.HotspotInfo();
                hotspotInfo.setHotspotName((String) hotspot.get("hotspot_name"));
                hotspotInfo.setHotspotType((String) hotspot.get("hotspot_type"));
                hotspotInfo.setCenterLatitude((Double) hotspot.get("center_latitude"));
                hotspotInfo.setCenterLongitude((Double) hotspot.get("center_longitude"));
                hotspotInfo.setRadius((Double) hotspot.get("radius"));
                hotspotInfo.setVisitCount((Integer) hotspot.get("visit_count"));
                hotspotInfo.setUniqueUsers((Integer) hotspot.get("unique_users"));
                hotspotInfo.setPeakHour((String) hotspot.get("peak_hour"));
                hotspotInfo.setAvgDuration((Integer) hotspot.get("avg_duration"));
                hotspotInfos.add(hotspotInfo);
            }
            analysisData.setHotspots(hotspotInfos);
        }
        
        // 可以继续转换其他数据类型...
        
        return analysisData;
    }
    
    @Override
    public List<TrajectoryAnalysisResult> getUserAnalysisTasks(Long userId) {
        logger.info("获取用户分析任务列表，用户ID: {}", userId);
        
        try {
            List<Map<String, Object>> tasks = routeTrajectoryMapper.getUserAnalysisTasks(userId);
            List<TrajectoryAnalysisResult> results = new ArrayList<>();
            
            for (Map<String, Object> task : tasks) {
                TrajectoryAnalysisResult result = new TrajectoryAnalysisResult();
                result.setTaskId((String) task.get("task_id"));
                result.setTaskName((String) task.get("task_name"));
                result.setAlgorithmType((String) task.get("algorithm_type"));
                result.setTaskStatus((String) task.get("task_status"));
                result.setProgressPercent((Integer) task.get("progress_percent"));
                result.setExecutionTime((Integer) task.get("execution_time"));
                result.setCreateTime((Date) task.get("create_time"));
                result.setStartTime((Date) task.get("start_time"));
                result.setEndTime((Date) task.get("end_time"));
                results.add(result);
            }
            
            return results;
            
        } catch (Exception e) {
            logger.error("获取用户分析任务列表失败", e);
            throw new RuntimeException("获取用户分析任务列表失败: " + e.getMessage());
        }
    }
    
    @Override
    public boolean cancelAnalysisTask(String taskId) {
        logger.info("取消分析任务，任务ID: {}", taskId);
        
        try {
            int updated = routeTrajectoryMapper.updateAnalysisTask(taskId, "4", 0, null, "用户取消", 0);
            return updated > 0;
        } catch (Exception e) {
            logger.error("取消分析任务失败", e);
            return false;
        }
    }
    
    @Override
    public boolean deleteAnalysisTask(String taskId) {
        logger.info("删除分析任务，任务ID: {}", taskId);
        
        try {
            // 实际应该是设置删除标志，这里简化处理
            int updated = routeTrajectoryMapper.updateAnalysisTask(taskId, "3", 0, null, "任务已删除", 0);
            return updated > 0;
        } catch (Exception e) {
            logger.error("删除分析任务失败", e);
            return false;
        }
    }
}
