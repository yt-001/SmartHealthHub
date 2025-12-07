package com.xitian.smarthealthhub.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 预约创建DTO
 */
@Data
@Schema(description = "预约创建DTO")
public class AppointmentCreateDTO {
    
    @Schema(description = "医生排班ID，关联 doctor_schedules.id")
    private Long scheduleId;
    
    @Schema(description = "患者用户ID，关联 users.id")
    private Long patientId;
    
    @Schema(description = "病情描述/备注")
    private String description;
    
    @Schema(description = "挂号单号（可选，如果为空则系统自动生成）")
    private String registrationNo;
}