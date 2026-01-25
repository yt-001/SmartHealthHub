package com.xitian.smarthealthhub.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 病例记录保存DTO
 */
@Schema(description = "病例记录保存DTO")
@Data
public class MedicalRecordSaveDTO {
    
    @Schema(description = "病例ID，更新时必填")
    private Long id;
    
    @Schema(description = "患者用户ID")
    @NotNull(message = "患者ID不能为空")
    private Long userId;
    
    @Schema(description = "医生用户ID")
    @NotNull(message = "医生ID不能为空")
    private Long doctorId;
    
    @Schema(description = "患者姓名")
    private String userName;
    
    @Schema(description = "医生姓名")
    private String doctorName;
    
    @Schema(description = "就诊时的症状描述")
    private String symptoms;
    
    @Schema(description = "诊断结果")
    private String diagnosis;
    
    @Schema(description = "治疗方案")
    private String treatmentPlan;
    
    @Schema(description = "处方信息(JSON)")
    private String prescription;
    
    @Schema(description = "医生备注")
    private String notes;
    
    @Schema(description = "就诊日期")
    private LocalDateTime visitDate;
    
    @Schema(description = "状态：0 治疗中 1 已完成 2 已归档")
    private Byte status;
}
