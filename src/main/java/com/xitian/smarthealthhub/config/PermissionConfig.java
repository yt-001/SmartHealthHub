package com.xitian.smarthealthhub.config;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 权限配置类，用于管理路径和角色组合的映射关系
 */
public class PermissionConfig {
    
    /**
     * 获取路径权限映射关系
     * 使用LinkedHashMap保持路径的配置顺序，确保更具体的路径优先匹配
     * @return 路径权限映射关系
     */
    public static Map<String, RoleCombination> getPathRoleMapping() {
        Map<String, RoleCombination> mapping = new LinkedHashMap<>();
        
        // 健康文章接口权限控制
        mapping.put("/health-articles/page", RoleCombination.ALL); // 分页查询（所有角色）
        mapping.put("/health-articles/*/view", RoleCombination.ALL); // 查看文章详情（所有角色）
        mapping.put("/health-articles/create", RoleCombination.DOCTOR_ONLY); // 创建文章（仅医生）
        mapping.put("/health-articles/update", RoleCombination.DOCTOR_ONLY); // 更新文章（仅医生）
        mapping.put("/health-articles/delete/**", RoleCombination.ADMIN_DOCTOR); // 删除文章（管理员和医生）
        
        // 用户信息更新接口（所有角色都可以访问）
        mapping.put("/user/updateProfile", RoleCombination.ALL);
        
        // 用户接口（用户、医生和管理员都可以访问）
        mapping.put("/user/**", RoleCombination.ALL);
        
        // 医生档案接口（医生和管理员可以访问）
        mapping.put("/doctor/profiles", RoleCombination.ADMIN_DOCTOR);
        
        // 医生接口（管理员和医生可以访问）
        mapping.put("/doctor/**", RoleCombination.ADMIN_DOCTOR);
        
        // 排班接口（医生和管理员可以访问）
        mapping.put("/schedule/**", RoleCombination.ADMIN_DOCTOR);
        
        // 管理员专属接口
        mapping.put("/admin/**", RoleCombination.ADMIN_ONLY);
        
        // 默认权限（需要放在最后）
        mapping.put("/**", RoleCombination.ALL);
        
        return mapping;
    }
    
    /**
     * 获取公共接口路径（无需认证）
     * @return 公共接口路径数组
     */
    public static String[] getPublicPaths() {
        return new String[]{
            "/auth/login",
            "/auth/register", 
            "/auth/doctor-authenticate",
            "/auth/patient-authenticate",
            "/auth/refresh"
        };
    }
}