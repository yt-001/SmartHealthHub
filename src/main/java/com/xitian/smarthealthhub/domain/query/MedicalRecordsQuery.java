package com.xitian.smarthealthhub.domain.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 病例记录查询类
 *
 * @author 
 * @date 2026/01/25
 */
@Getter
@Setter
@ToString
public class MedicalRecordsQuery {
    /**
     * 患者姓名（模糊查询）
     */
    @Schema(description = "患者姓名（模糊查询）")
    private String userName;

    /**
     * 医生姓名（模糊查询）
     */
    @Schema(description = "医生姓名（模糊查询）")
    private String doctorName;

    /**
     * 诊断结果（模糊查询）
     */
    @Schema(description = "诊断结果（模糊查询）")
    private String diagnosis;

    /**
     * 状态：0 治疗中 1 已完成 2 已归档
     */
    @Schema(description = "状态：0 治疗中 1 已完成 2 已归档")
    private Integer status;
}
