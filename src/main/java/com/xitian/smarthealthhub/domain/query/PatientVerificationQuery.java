package com.xitian.smarthealthhub.domain.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "患者实名认证审核查询条件")
@Getter
@Setter
@ToString
public class PatientVerificationQuery {
    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "身份证号")
    private String idCard;
    
    // 时间范围查询字段
    @Schema(description = "创建时间起始，格式：yyyy-MM-dd")
    private String createdStart;
    
    @Schema(description = "创建时间结束，格式：yyyy-MM-dd")
    private String createdEnd;
}