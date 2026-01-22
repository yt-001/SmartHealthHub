package com.xitian.smarthealthhub.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Size;

/**
 * 用户健康档案保存DTO
 */
@Schema(description = "用户健康档案保存DTO")
@Data
public class UserProfileSaveDTO {
    
    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "身份证号")
    @Size(max = 18, message = "身份证号长度不能超过18个字符")
    private String idCard;
    
    @Schema(description = "血型：A/B/AB/O/RH-")
    @Size(max = 10, message = "血型长度不能超过10个字符")
    private String bloodType;
    
    @Schema(description = "过敏史，自由文本")
    @Size(max = 1000, message = "过敏史长度不能超过1000个字符")
    private String allergyHistory;
    
    @Schema(description = "慢性病史")
    @Size(max = 1000, message = "慢性病史长度不能超过1000个字符")
    private String chronicDisease;
    
    @Schema(description = "当前主诉/症状")
    @Size(max = 500, message = "当前主诉/症状长度不能超过500个字符")
    private String currentSymptoms;
    
    @Schema(description = "正在执行的治疗方案")
    @Size(max = 1000, message = "正在执行的治疗方案长度不能超过1000个字符")
    private String currentPlan;
    
    @Schema(description = "下一步建议（检查/复诊/手术等）")
    @Size(max = 1000, message = "下一步建议长度不能超过1000个字符")
    private String nextStep;
    
    @Schema(description = "常住地址")
    @Size(max = 255, message = "地址长度不能超过255个字符")
    private String address;
    
    @Schema(description = "紧急联系人姓名")
    @Size(max = 32, message = "紧急联系人姓名长度不能超过32个字符")
    private String emergencyName;
    
    @Schema(description = "紧急联系人电话")
    @Size(max = 20, message = "紧急联系人电话长度不能超过20个字符")
    private String emergencyPhone;
}
