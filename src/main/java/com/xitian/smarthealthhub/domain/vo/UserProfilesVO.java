package com.xitian.smarthealthhub.domain.vo;

import com.xitian.smarthealthhub.bean.base.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "患者档案信息VO")
@Getter
@Setter
@ToString
public class UserProfilesVO extends BaseVO {

    @Schema(description = "身份证号")
    private String idCard;

    @Schema(description = "血型")
    private String bloodType;

    @Schema(description = "过敏史")
    private String allergyHistory;

    @Schema(description = "慢性病史")
    private String chronicDisease;

    @Schema(description = "当前症状")
    private String currentSymptoms;

    @Schema(description = "当前治疗方案")
    private String currentPlan;

    @Schema(description = "下一步建议")
    private String nextStep;

    @Schema(description = "地址信息")
    private String address;

    @Schema(description = "紧急联系人姓名")
    private String emergencyName;

    @Schema(description = "紧急联系人电话")
    private String emergencyPhone;
}
