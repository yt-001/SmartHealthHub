package com.xitian.smarthealthhub.domain.query;

import com.xitian.smarthealthhub.bean.base.BaseQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "患者实名认证审核查询条件")
@Getter
@Setter
@ToString
public class PatientVerificationQuery extends BaseQuery {
    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "身份证号")
    private String idCard;
}