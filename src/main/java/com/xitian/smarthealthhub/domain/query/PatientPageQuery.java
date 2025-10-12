package com.xitian.smarthealthhub.domain.query;

import com.xitian.smarthealthhub.bean.base.BaseQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "患者信息查询条件")
@Getter
@Setter
@ToString
public class PatientPageQuery extends BaseQuery {
    @Schema(description = "用户名/登录账号")
    private String username;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "身份证号")
    private String idCard;

    @Schema(description = "血型")
    private String bloodType;

    @Schema(description = "性别: M男 F女 O其他")
    private String gender;

    @Schema(description = "账号状态: 0正常 1锁定 2未激活 3已注销")
    private Byte status;
}