package com.xitian.smarthealthhub.domain.vo;

import com.xitian.smarthealthhub.bean.base.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "医生档案信息VO")
@Getter
@Setter
@ToString
public class DoctorProfilesVO extends BaseVO {
    @Schema(description = "科室ID")
    private Integer deptId;

    @Schema(description = "职称")
    private String title;

    @Schema(description = "擅长领域")
    private String specialty;

    @Schema(description = "执业证书编号")
    private String qualificationNo;

    @Schema(description = "个人简介")
    private String bio;

    @Schema(description = "工作班次")
    private String workShift;

    @Schema(description = "诊室编号")
    private String officeRoom;
}
