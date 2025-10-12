package com.xitian.smarthealthhub.domain.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotNull;

/**
 * 登录请求DTO
 */
@Data
public class LoginRequestDTO {
    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;
    
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;
    
    /**
     * 角色 (0: 管理员, 1: 医生, 2: 用户)
     */
    @NotNull(message = "角色不能为空")
    private Byte role;
}