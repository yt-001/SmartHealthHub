package com.xitian.smarthealthhub.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 病例详情VO（用于详情页展示）
 */
@Schema(description = "病例详情VO")
@Getter
@Setter
@ToString
public class MedicalRecordDetailVO {
    
    @Schema(description = "病例编号")
    private Long id;
    
    @Schema(description = "患者姓名")
    private String userName;
    
    @Schema(description = "主治医生")
    private String doctorName;
    
    @Schema(description = "就诊时间")
    private LocalDateTime visitDate;
    
    @Schema(description = "患者主诉")
    private String symptoms;
    
    @Schema(description = "医生诊断")
    private String diagnosis;
    
    @Schema(description = "治疗方案")
    private String treatmentPlan;
    
    @Schema(description = "处方信息")
    private String prescription;
    
    @Schema(description = "医生备注")
    private String notes;
    
    @Schema(description = "完成时间（仅当 status=1 时展示）")
    private LocalDateTime completedDate;
    
    @Schema(description = "当前状态")
    private Integer status;
}