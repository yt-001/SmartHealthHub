package com.xitian.smarthealthhub.domain.query;

import com.xitian.smarthealthhub.bean.base.BaseQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "病例查询条件")
@Getter
@Setter
@ToString
public class MedicalRecordQuery extends BaseQuery {
    @Schema(description = "患者姓名")
    private String patientName;

    @Schema(description = "医生姓名")
    private String doctorName;

    @Schema(description = "诊断结果")
    private String diagnosis;

    @Schema(description = "状态：0 治疗中 1 已完成 2 已归档")
    private Byte status;
}