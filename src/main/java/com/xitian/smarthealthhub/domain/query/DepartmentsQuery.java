package com.xitian.smarthealthhub.domain.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 科室查询条件
 */
@Getter
@Setter
@ToString
public class DepartmentsQuery {
    
    @Schema(description = "科室名称（模糊查询）")
    private String name;

    @Schema(description = "科室状态：1 启用 0 停用")
    private Byte isActive;
    
    @Schema(description = "科室主任姓名（模糊查询）")
    private String headDoctorName;
}