package com.xitian.smarthealthhub.domain.query;

import com.xitian.smarthealthhub.bean.base.BaseQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "医生信息查询条件")
@Getter
@Setter
@ToString
public class DoctorPageQuery extends BaseQuery {
    @Schema(description = "用户名/登录账号")
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

    @Schema(description = "账号状态: 0正常 1锁定 2未激活 3已注销")
    private Byte status;
}