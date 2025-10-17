package com.xitian.smarthealthhub.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "医生资质审核列表VO")
@Getter
@Setter
@ToString
public class DoctorVerificationVO {
    @Schema(description = "医生档案ID")
    private Long id;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "职称")
    private String title;

    @Schema(description = "科室ID")
    private Integer deptId;

    @Schema(description = "医师执业证号")
    private String qualificationNo;

    @Schema(description = "提交时间")
    private LocalDateTime createdAt;
}