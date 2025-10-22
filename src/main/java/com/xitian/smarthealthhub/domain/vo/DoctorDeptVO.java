package com.xitian.smarthealthhub.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 医生科室信息VO，用于排班选择
 */
@Data
@Schema(description = "医生科室信息VO")
public class DoctorDeptVO {
    
    @Schema(description = "医生ID")
    private Long doctorId;
    
    @Schema(description = "医生姓名")
    private String doctorName;
    
    @Schema(description = "科室ID")
    private Integer deptId;
    
    @Schema(description = "科室名称")
    private String deptName;
    
    @Schema(description = "诊室编号")
    private String roomNo;
}