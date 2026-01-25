package com.xitian.smarthealthhub.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Schema(description = "用户注册DTO")
@Data
public class UserRegistrationDTO {
    
    @Schema(description = "登录账号")
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 32, message = "用户名长度必须在3-32个字符之间")
    private String username;
    
    @Schema(description = "真实姓名")
    @NotBlank(message = "真实姓名不能为空")
    @Size(max = 32, message = "真实姓名长度不能超过32个字符")
    private String realName;
    
    @Schema(description = "手机号")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;
    
    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度必须在6-20个字符之间")
    private String password;
    
    @Schema(description = "确认密码")
    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;
    
    @Schema(description = "性别: M男 F女 O其他")
    @Pattern(regexp = "^[MFO]$", message = "性别只能是M(男)、F(女)或O(其他)")
    private String gender;
    
    @Schema(description = "角色: 0管理员 1医生 2患者")
    @Pattern(regexp = "^[012]$", message = "角色只能是0(管理员)、1(医生)或2(患者)")
    private String role;
    
    @Schema(description = "邮箱")
    @Size(max = 64, message = "邮箱长度不能超过64个字符")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "邮箱格式不正确", 
             flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;
    
    @Schema(description = "出生日期")
    private LocalDate birthDate;
}