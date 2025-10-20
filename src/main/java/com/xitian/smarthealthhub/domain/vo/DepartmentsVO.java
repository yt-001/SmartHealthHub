package com.xitian.smarthealthhub.domain.vo;

import com.xitian.smarthealthhub.bean.base.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 科室VO类
 */
@Getter
@Setter
@ToString
public class DepartmentsVO extends BaseVO {
    
    @Schema(description = "科室名称")
    private String name;

    @Schema(description = "科室描述")
    private String description;

    @Schema(description = "科室主任医生ID，关联users.id")
    private Long headDoctorId;

    @Schema(description = "科室状态：1 启用 0 停用")
    private Byte isActive;
    
    @Schema(description = "科室主任姓名")
    private String headDoctorName;
}