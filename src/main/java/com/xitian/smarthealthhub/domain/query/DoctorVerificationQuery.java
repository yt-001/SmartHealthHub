package com.xitian.smarthealthhub.domain.query;

import com.xitian.smarthealthhub.bean.base.BaseQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "医生资质审核查询条件")
@Getter
@Setter
@ToString
public class DoctorVerificationQuery extends BaseQuery {
    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "医师执业证号")
    private String qualificationNo;
}