package com.xitian.smarthealthhub.domain.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "医生信息查询条件")
@Getter
@Setter
@ToString
public class DoctorPageQuery {
    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "职称")
    private String title;

    @Schema(description = "科室ID")
    private Integer deptId;

    @Schema(description = "账号状态: 0正常 1锁定 2未激活 3已注销")
    private Byte status;
    
    // 时间范围查询字段
    @Schema(description = "创建时间起始，格式：yyyy-MM-dd")
    private String createdStart;
    
    @Schema(description = "创建时间结束，格式：yyyy-MM-dd")
    private String createdEnd;
}