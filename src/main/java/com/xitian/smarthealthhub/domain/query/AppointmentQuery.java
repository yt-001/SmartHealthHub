package com.xitian.smarthealthhub.domain.query;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 预约查询类
 */
@Data
@Schema(description = "预约查询类")
public class AppointmentQuery {
    
    @NotNull(message = "医生ID不能为空", groups = DoctorIdRequired.class)
    @Schema(description = "医生ID")
    private Long doctorId;
    
    @NotNull(message = "患者ID不能为空", groups = PatientIdRequired.class)
    @Schema(description = "患者ID")
    private Long patientId;
    
    @Schema(description = "预约状态：0 待确认 1 已确认 2 已就诊 3 已取消")
    private Byte status;
    
    @Schema(description = "创建开始时间")
    private LocalDateTime startTime;
    
    @Schema(description = "创建结束时间")
    private LocalDateTime endTime;
    
    /**
     * 标记患者ID为必需的校验组
     */
    public interface PatientIdRequired {}
    
    /**
     * 标记医生ID为必需的校验组
     */
    public interface DoctorIdRequired {}
}