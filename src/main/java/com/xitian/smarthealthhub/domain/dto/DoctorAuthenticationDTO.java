package com.xitian.smarthealthhub.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Builder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Schema(description = "医生认证DTO")
@Data
@Builder
public class DoctorAuthenticationDTO {
    
    @Schema(description = "用户ID")
    @NotNull(message = "用户ID不能为空")
    private Long userId;
    
    @Schema(description = "科室ID")
    @NotNull(message = "科室ID不能为空")
    private Integer deptId;
    
    @Schema(description = "职称：主任医师/副主任医师/主治医师/住院医师")
    @NotBlank(message = "职称不能为空")
    @Size(max = 32, message = "职称长度不能超过32个字符")
    private String title;
    
    @Schema(description = "擅长病种/技术，逗号分隔")
    @NotBlank(message = "擅长领域不能为空")
    @Size(max = 255, message = "擅长领域长度不能超过255个字符")
    private String specialty;
    
    @Schema(description = "医师执业证号，卫健委可查")
    @NotBlank(message = "医师执业证号不能为空")
    @Size(max = 32, message = "医师执业证号长度不能超过32个字符")
    private String qualificationNo;
}