package com.xitian.smarthealthhub.dto;

import lombok.Data;

/**
 * 登录请求DTO
 */
@Data
public class LoginRequest {
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 角色 (0: 管理员, 1: 医生, 2: 用户)
     */
    private Byte role;
}