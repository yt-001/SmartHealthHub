package com.xitian.smarthealthhub.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "患者实名认证审核列表VO")
@Getter
@Setter
@ToString
public class PatientVerificationVO {
    @Schema(description = "患者档案ID")
    private Long id;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "身份证号")
    private String idCard;

    @Schema(description = "提交时间")
    private LocalDateTime createdAt;
}