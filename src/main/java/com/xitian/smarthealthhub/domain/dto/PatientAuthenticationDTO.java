package com.xitian.smarthealthhub.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Builder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

@Schema(description = "患者认证DTO")
@Data
@Builder
public class PatientAuthenticationDTO {
    
    @Schema(description = "用户ID")
    @NotNull(message = "用户ID不能为空")
    private Long userId;
    
    @Schema(description = "身份证号")
    @NotBlank(message = "身份证号不能为空")
    @Pattern(regexp = "^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$", 
             message = "身份证号格式不正确")
    private String idCard;
    
    @Schema(description = "血型：A/B/AB/O/RH-")
    private String bloodType;
    
    @Schema(description = "过敏史，自由文本")
    @Size(max = 1000, message = "过敏史长度不能超过1000个字符")
    private String allergyHistory;
    
    @Schema(description = "慢性病史")
    @Size(max = 1000, message = "慢性病史长度不能超过1000个字符")
    private String chronicDisease;
    
    @Schema(description = "当前主诉/症状")
    @NotBlank(message = "当前主诉/症状不能为空")
    @Size(max = 500, message = "当前主诉/症状长度不能超过500个字符")
    private String currentSymptoms;
    
    @Schema(description = "正在执行的治疗方案")
    @NotBlank(message = "正在执行的治疗方案不能为空")
    @Size(max = 1000, message = "正在执行的治疗方案长度不能超过1000个字符")
    private String currentPlan;
    
    @Schema(description = "下一步建议（检查/复诊/手术等）")
    @NotBlank(message = "下一步建议不能为空")
    @Size(max = 500, message = "下一步建议长度不能超过500个字符")
    private String nextStep;
    
    @Schema(description = "常住地址快照 {\"省\":\"xx\",\"市\":\"xx\",\"区\":\"xx\",\"detail\":\"路号\"}")
    @Size(max = 200, message = "地址信息长度不能超过200个字符")
    private String address;
    
    @Schema(description = "紧急联系人姓名")
    @Size(max = 32, message = "紧急联系人姓名长度不能超过32个字符")
    private String emergencyName;
    
    @Schema(description = "紧急联系人电话")
    @Size(max = 16, message = "紧急联系人电话长度不能超过16个字符")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "紧急联系人电话格式不正确")
    private String emergencyPhone;
}