package com.xitian.smarthealthhub.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 病例列表项VO（用于列表页展示）
 */
@Schema(description = "病例列表项VO")
@Getter
@Setter
@ToString
public class MedicalRecordListItemVO {
    
    @Schema(description = "病例编号/唯一标识")
    private Long id;
    
    @Schema(description = "诊断结果（若为空则展示 symptoms 主诉）")
    private String diagnosis;
    
    @Schema(description = "主治医生姓名")
    private String doctorName;
    
    @Schema(description = "就诊时间（建议格式：yyyy-MM-dd HH:mm:ss）")
    private LocalDateTime visitDate;
    
    @Schema(description = "状态码（0:治疗中, 1:已完成, 2:已归档）")
    private Integer status;
}