package com.RouteMate.route.controller;

import com.RouteMate.common.annotation.Log;
import com.RouteMate.common.core.controller.BaseController;
import com.RouteMate.common.core.domain.AjaxResult;
import com.RouteMate.common.core.page.TableDataInfo;
import com.RouteMate.common.enums.BusinessType;
import com.RouteMate.common.utils.poi.ExcelUtil;
import com.RouteMate.route.domain.TrajectoryAnalysisRequest;
import com.RouteMate.route.domain.TrajectoryAnalysisResult;
import com.RouteMate.route.service.ITrajectoryAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 轨迹分析控制器
 */
@RestController
@RequestMapping("/route/analysis")
public class TrajectoryAnalysisController extends BaseController {

    @Autowired
    private ITrajectoryAnalysisService trajectoryAnalysisService;

    /**
     * 执行轨迹分析
     */

    @Log(title = "轨迹分析", businessType = BusinessType.INSERT)
    @PostMapping("/execute")
    public AjaxResult executeAnalysis(@RequestBody TrajectoryAnalysisRequest request) {
        try {
            // 设置当前用户ID
            if (request.getUserId() == null) {
                request.setUserId(getUserId());
            }

            TrajectoryAnalysisResult result = trajectoryAnalysisService.executeAnalysis(request);
            return AjaxResult.success(result);
        } catch (Exception e) {
            return AjaxResult.error("执行分析失败: " + e.getMessage());
        }
    }

    /**
     * 获取分析结果
     */

    @GetMapping("/result/{taskId}")
    public AjaxResult getAnalysisResult(@PathVariable String taskId) {
        try {
            TrajectoryAnalysisResult result = trajectoryAnalysisService.getAnalysisResult(taskId);
            return AjaxResult.success(result);
        } catch (Exception e) {
            return AjaxResult.error("获取分析结果失败: " + e.getMessage());
        }
    }

    /**
     * 获取分析任务列表
     */

    @GetMapping("/list")
    public TableDataInfo list() {
        try {
            startPage();
            List<TrajectoryAnalysisResult> list = trajectoryAnalysisService.getUserAnalysisTasks(getUserId());
            return getDataTable(list);
        } catch (Exception e) {
            return getDataTable(null);
        }
    }

    /**
     * 获取分析任务详情
     */

    @GetMapping("/detail/{taskId}")
    public AjaxResult getDetail(@PathVariable String taskId) {
        try {
            TrajectoryAnalysisResult result = trajectoryAnalysisService.getAnalysisResult(taskId);
            return AjaxResult.success(result);
        } catch (Exception e) {
            return AjaxResult.error("获取任务详情失败: " + e.getMessage());
        }
    }

    /**
     * 取消分析任务
     */

    @Log(title = "取消分析任务", businessType = BusinessType.UPDATE)
    @PostMapping("/cancel/{taskId}")
    public AjaxResult cancelTask(@PathVariable String taskId) {
        try {
            boolean success = trajectoryAnalysisService.cancelAnalysisTask(taskId);
            if (success) {
                return AjaxResult.success("任务已取消");
            } else {
                return AjaxResult.error("取消任务失败");
            }
        } catch (Exception e) {
            return AjaxResult.error("取消任务失败: " + e.getMessage());
        }
    }

    /**
     * 删除分析任务
     */

    @Log(title = "删除分析任务", businessType = BusinessType.DELETE)
    @DeleteMapping("/{taskId}")
    public AjaxResult remove(@PathVariable String taskId) {
        try {
            boolean success = trajectoryAnalysisService.deleteAnalysisTask(taskId);
            if (success) {
                return AjaxResult.success("删除成功");
            } else {
                return AjaxResult.error("删除失败");
            }
        } catch (Exception e) {
            return AjaxResult.error("删除失败: " + e.getMessage());
        }
    }

    /**
     * 导出分析任务列表
     */

    @Log(title = "轨迹分析", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response) {
        try {
            List<TrajectoryAnalysisResult> list = trajectoryAnalysisService.getUserAnalysisTasks(getUserId());
            ExcelUtil<TrajectoryAnalysisResult> util = new ExcelUtil<>(TrajectoryAnalysisResult.class);
            util.exportExcel(response, list, "轨迹分析任务数据");
        } catch (Exception e) {
            logger.error("导出分析任务列表失败", e);
        }
    }

    /**
     * 获取分析进度
     */

    @GetMapping("/progress/{taskId}")
    public AjaxResult getProgress(@PathVariable String taskId) {
        try {
            TrajectoryAnalysisResult result = trajectoryAnalysisService.getAnalysisResult(taskId);
            return AjaxResult.success().put("progress", result.getProgressPercent())
                                  .put("status", result.getTaskStatus())
                                  .put("message", result.getErrorMessage());
        } catch (Exception e) {
            return AjaxResult.error("获取进度失败: " + e.getMessage());
        }
    }

    /**
     * 获取热点数据
     */

    @GetMapping("/hotspots/{taskId}")
    public AjaxResult getHotspots(@PathVariable String taskId) {
        try {
            TrajectoryAnalysisResult result = trajectoryAnalysisService.getAnalysisResult(taskId);
            if (result.getAnalysisData() != null && result.getAnalysisData().getHotspots() != null) {
                return AjaxResult.success(result.getAnalysisData().getHotspots());
            } else {
                return AjaxResult.success("暂无热点数据");
            }
        } catch (Exception e) {
            return AjaxResult.error("获取热点数据失败: " + e.getMessage());
        }
    }

    /**
     * 获取推荐路径
     */
    @GetMapping("/recommendations/{taskId}")
    public AjaxResult getRecommendations(@PathVariable String taskId) {
        try {
            TrajectoryAnalysisResult result = trajectoryAnalysisService.getAnalysisResult(taskId);
            if (result.getAnalysisData() != null && result.getAnalysisData().getRecommendations() != null) {
                return AjaxResult.success(result.getAnalysisData().getRecommendations());
            } else {
                return AjaxResult.success("暂无推荐数据");
            }
        } catch (Exception e) {
            return AjaxResult.error("获取推荐数据失败: " + e.getMessage());
        }
    }
}
