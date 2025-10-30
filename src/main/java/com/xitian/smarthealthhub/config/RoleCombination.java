package com.xitian.smarthealthhub.config;

/**
 * 角色组合枚举，用于定义不同角色的访问权限组合
 */
public enum RoleCombination {
    /**
     * 所有角色都可以访问
     */
    ALL(new String[]{"ROLE_ADMIN", "ROLE_DOCTOR", "ROLE_USER"}),

    /**
     * 管理员专属
     */
    ADMIN_ONLY(new String[]{"ROLE_ADMIN"}),

    /**
     * 用户专属
     */
    USER_ONLY(new String[]{"ROLE_USER"}),

    /**
     * 医生专属
     */
    DOCTOR_ONLY(new String[]{"ROLE_DOCTOR"}),

    /**
     * 管理员和医生可以访问
     */
    ADMIN_DOCTOR(new String[]{"ROLE_ADMIN", "ROLE_DOCTOR"}),

    /**
     * 用户和医生可以访问
     */
    USER_DOCTOR(new String[]{"ROLE_USER", "ROLE_DOCTOR"});

    private final String[] roles;

    RoleCombination(String[] roles) {
        this.roles = roles;
    }

    public String[] getRoles() {
        return roles;
    }
}