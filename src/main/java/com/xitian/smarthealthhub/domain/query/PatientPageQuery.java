package com.xitian.smarthealthhub.domain.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "患者信息查询条件")
@Getter
@Setter
@ToString
public class PatientPageQuery {
    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "身份证号")
    private String idCard;

    @Schema(description = "性别: M男 F女 O其他")
    private String gender;

    @Schema(description = "账号状态: 0正常 1锁定 2未激活 3已注销")
    private Byte status;

    @Schema(description = "创建时间起始，格式：yyyy-MM-dd")
    private String createdStart;
    
    @Schema(description = "创建时间结束，格式：yyyy-MM-dd")
    private String createdEnd;
}