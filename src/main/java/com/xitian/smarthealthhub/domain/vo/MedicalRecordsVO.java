package com.xitian.smarthealthhub.domain.vo;

import com.xitian.smarthealthhub.bean.base.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "患者病例历史信息VO")
@Getter
@Setter
@ToString
public class MedicalRecordsVO extends BaseVO {
    @Schema(description = "关联 users.id，role=2（患者）")
    private Long userId;

    @Schema(description = "关联 users.id，role=1（医生）")
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

    @Schema(description = "处方信息")
    private String prescription;

    @Schema(description = "医生备注")
    private String notes;

    @Schema(description = "就诊日期")
    private LocalDateTime visitDate;

    @Schema(description = "完成日期（治疗结束时间）")
    private LocalDateTime completedDate;

    @Schema(description = "状态：0 治疗中 1 已完成 2 已归档")
    private Byte status;
}