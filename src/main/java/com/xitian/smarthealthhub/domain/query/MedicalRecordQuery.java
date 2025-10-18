package com.xitian.smarthealthhub.domain.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "病例查询条件")
@Getter
@Setter
@ToString
public class MedicalRecordQuery {
    @Schema(description = "患者姓名")
    private String patientName;

    @Schema(description = "医生姓名")
    private String doctorName;

    @Schema(description = "状态：0 治疗中 1 已完成 2 已归档")
    private Byte status;

    @Schema(description = "就诊时间起始，格式：yyyy-MM-dd")
    private String visitStart;

    @Schema(description = "就诊时间结束，格式：yyyy-MM-dd")
    private String visitEnd;

    @Schema(description = "完成时间起始，格式：yyyy-MM-dd")
    private String completedStart;

    @Schema(description = "完成时间结束，格式：yyyy-MM-dd")
    private String completedEnd;
}