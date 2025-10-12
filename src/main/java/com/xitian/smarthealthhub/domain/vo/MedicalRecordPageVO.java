package com.xitian.smarthealthhub.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "病例历史归档信息分页展示VO")
@Getter
@Setter
@ToString
public class MedicalRecordPageVO {
    @Schema(description = "病例ID")
    private Long id;

    @Schema(description = "患者姓名")
    private String patientName;

    @Schema(description = "医生姓名")
    private String doctorName;

    @Schema(description = "诊断结果")
    private String diagnosis;

    @Schema(description = "就诊日期")
    private LocalDateTime visitDate;

    @Schema(description = "完成日期")
    private LocalDateTime completedDate;

    @Schema(description = "状态：0 治疗中 1 已完成 2 已归档")
    private Byte status;
}