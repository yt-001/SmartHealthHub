package com.xitian.smarthealthhub.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "医生信息分页展示VO")
@Getter
@Setter
@ToString
public class DoctorPageVO {
    @Schema(description = "医生ID")
    private Long id;

    @Schema(description = "登录账号")
    private String username;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "职称")
    private String title;

    @Schema(description = "科室ID")
    private Integer deptId;

    @Schema(description = "擅长领域")
    private String specialty;

    @Schema(description = "执业证书编号")
    private String qualificationNo;

    @Schema(description = "账号状态: 0正常 1锁定 2未激活 3已注销")
    private Byte status;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
}