package com.RouteMate.route.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.RouteMate.common.annotation.Excel;
import com.RouteMate.common.core.domain.BaseEntity;

/**
 * 分析任务对象 route_analysis_task
 * 
 * @author RouteMate
 * @date 2026-04-01
 */
public class RouteAnalysisTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 任务ID（UUID） */
    private String taskId;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String taskName;

    /** 用户ID（关联sys_user.user_id）（关联用户信息表） */
    @Excel(name = "用户ID", readConverterExp = "关=联sys_user.user_id")
    private Long userId;

    /** 用户ID（关联sys_user.user_id）（关联用户信息表）关联字段（关联sys_user表） */
    private String userName;

    /** 轨迹ID（关联route_trajectory_info.trajectory_id）（关联出行轨迹信息表） */
    @Excel(name = "轨迹ID", readConverterExp = "关=联route_trajectory_info.trajectory_id")
    private Long trajectoryId;

    /** 轨迹ID（关联route_trajectory_info.trajectory_id）（关联出行轨迹信息表）关联字段（关联route_trajectory_info表） */
    private String trajectoryName;

    /** 轨迹ID（关联route_trajectory_info.trajectory_id）（关联出行轨迹信息表）关联字段（关联route_trajectory_info表） */
    private String startLocation;

    /** 轨迹ID（关联route_trajectory_info.trajectory_id）（关联出行轨迹信息表）关联字段（关联route_trajectory_info表） */
    private String endLocation;

    /** 轨迹ID（关联route_trajectory_info.trajectory_id）（关联出行轨迹信息表）关联字段（关联route_trajectory_info表） */
    private String trajectoryData;

    /** 算法类型（SW_FP GSCM NDTTT DBSCAN） */
    @Excel(name = "算法类型", readConverterExp = "S=W_FP,G=SCM,N=DTTT,D=BSCAN")
    private String algorithmType;

    /** 算法参数（JSON格式） */
    @Excel(name = "算法参数", readConverterExp = "J=SON格式")
    private String algorithmParams;

    /** 任务状态（0待处理 1处理中 2已完成 3失败 4已取消） */
    @Excel(name = "任务状态", readConverterExp = "0=待处理,1=处理中,2=已完成,3=失败,4=已取消")
    private String taskStatus;

    /** 进度百分比 */
    @Excel(name = "进度百分比")
    private Long progressPercent;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 结果数据（JSON格式） */
    @Excel(name = "结果数据", readConverterExp = "J=SON格式")
    private String resultData;

    /** 错误信息 */
    @Excel(name = "错误信息")
    private String errorMessage;

    /** 执行时长（秒） */
    @Excel(name = "执行时长", readConverterExp = "秒=")
    private Long executionTime;

    /** 删除标志（0存在 2删除） */
    private String delFlag;

    public void setTaskId(String taskId) 
    {
        this.taskId = taskId;
    }

    public String getTaskId() 
    {
        return taskId;
    }

    public void setTaskName(String taskName) 
    {
        this.taskName = taskName;
    }

    public String getTaskName() 
    {
        return taskName;
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

    public void setTrajectoryId(Long trajectoryId) 
    {
        this.trajectoryId = trajectoryId;
    }

    public Long getTrajectoryId() 
    {
        return trajectoryId;
    }

    public void setTrajectoryName(String trajectoryName) 
    {
        this.trajectoryName = trajectoryName;
    }

    public String getTrajectoryName() 
    {
        return trajectoryName;
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

    public void setTrajectoryData(String trajectoryData) 
    {
        this.trajectoryData = trajectoryData;
    }

    public String getTrajectoryData() 
    {
        return trajectoryData;
    }

    public void setAlgorithmType(String algorithmType) 
    {
        this.algorithmType = algorithmType;
    }

    public String getAlgorithmType() 
    {
        return algorithmType;
    }

    public void setAlgorithmParams(String algorithmParams) 
    {
        this.algorithmParams = algorithmParams;
    }

    public String getAlgorithmParams() 
    {
        return algorithmParams;
    }

    public void setTaskStatus(String taskStatus) 
    {
        this.taskStatus = taskStatus;
    }

    public String getTaskStatus() 
    {
        return taskStatus;
    }

    public void setProgressPercent(Long progressPercent) 
    {
        this.progressPercent = progressPercent;
    }

    public Long getProgressPercent() 
    {
        return progressPercent;
    }

    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }

    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }

    public Date getEndTime() 
    {
        return endTime;
    }

    public void setResultData(String resultData) 
    {
        this.resultData = resultData;
    }

    public String getResultData() 
    {
        return resultData;
    }

    public void setErrorMessage(String errorMessage) 
    {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() 
    {
        return errorMessage;
    }

    public void setExecutionTime(Long executionTime) 
    {
        this.executionTime = executionTime;
    }

    public Long getExecutionTime() 
    {
        return executionTime;
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
            .append("taskId", getTaskId())
            .append("taskName", getTaskName())
            .append("userId", getUserId())
            .append("trajectoryId", getTrajectoryId())
            .append("algorithmType", getAlgorithmType())
            .append("algorithmParams", getAlgorithmParams())
            .append("taskStatus", getTaskStatus())
            .append("progressPercent", getProgressPercent())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("resultData", getResultData())
            .append("errorMessage", getErrorMessage())
            .append("executionTime", getExecutionTime())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
