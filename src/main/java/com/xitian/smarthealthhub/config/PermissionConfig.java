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
        
        // 健康视频管理接口权限映射
        mapping.put("/health-videos/page", RoleCombination.ADMIN_ONLY); // 分页查询健康视频（仅管理员）
        mapping.put("/health-videos/create", RoleCombination.DOCTOR_ONLY); // 创建健康视频（医生）
        mapping.put("/health-videos/update", RoleCombination.ADMIN_DOCTOR); // 更新健康视频（医生和管理员）
        mapping.put("/health-videos/review", RoleCombination.ADMIN_ONLY); // 审核健康视频（仅管理员）
        mapping.put("/health-videos/delete/*", RoleCombination.ADMIN_DOCTOR); // 删除健康视频（医生和管理员）

        // 轮播图管理接口权限映射
        mapping.put("/carousel-items/page", RoleCombination.ADMIN_ONLY); // 分页查询轮播图（仅管理员）
        mapping.put("/carousel-items/create", RoleCombination.ADMIN_ONLY); // 创建轮播图（仅管理员）
        mapping.put("/carousel-items/update", RoleCombination.ADMIN_ONLY); // 更新轮播图（仅管理员）
        mapping.put("/carousel-items/delete/*", RoleCombination.ADMIN_ONLY); // 删除轮播图（仅管理员）
        mapping.put("/carousel-items/display", RoleCombination.ALL); // 查询显示的轮播图列表（所有用户）

        // 健康视频评论管理接口权限映射
        mapping.put("/health-video-comments/page", RoleCombination.ALL); // 分页查询（所有角色）
        mapping.put("/health-video-comments/video/*", RoleCombination.ALL); // 查看视频评论列表（所有角色）
        mapping.put("/health-video-comments/*", RoleCombination.ALL); // 查看评论详情（所有角色）
        mapping.put("/health-video-comments/create", RoleCombination.ALL); // 创建评论（所有角色）
        mapping.put("/health-video-comments/delete/**", RoleCombination.ALL); // 删除评论（管理员、用户和医生）
        
        // 预约挂号接口权限映射（对所有身份开放）
        mapping.put("/appointments/page", RoleCombination.ALL); // 分页查询预约（所有角色）
        mapping.put("/appointments/*", RoleCombination.ALL); // 根据ID获取预约详情（所有角色）
        mapping.put("/appointments/patient/*", RoleCombination.ALL); // 根据患者ID获取预约列表（所有角色）
        mapping.put("/appointments/schedule/*", RoleCombination.ALL); // 根据医生排班ID获取预约列表（所有角色）
        mapping.put("/appointments/create", RoleCombination.ALL); // 创建预约（所有角色）
        mapping.put("/appointments/*/status", RoleCombination.ALL); // 更新预约状态（所有角色）
        mapping.put("/appointments/delete/*", RoleCombination.ALL); // 删除预约（所有角色）

        // 医生排班接口权限映射
        mapping.put("/schedule/calendar", RoleCombination.ALL); // 获取医生排班日历信息（所有角色）
        mapping.put("/schedule/doctor/*", RoleCombination.ALL); // 根据医生ID获取医生排班信息（所有角色）
        mapping.put("/schedule/create", RoleCombination.ADMIN_DOCTOR); // 创建医生排班（医生和管理员）
        mapping.put("/schedule/doctor-dept-list", RoleCombination.ALL); // 获取医生科室信息列表（所有角色）

        // 用户信息更新接口（所有角色都可以访问）
        mapping.put("/user/updateProfile", RoleCombination.ALL);
        
        // 用户接口（用户、医生和管理员都可以访问）
        mapping.put("/user/**", RoleCombination.ALL);
        
        // 医生档案接口（医生和管理员可以访问）
        mapping.put("/doctor/profiles", RoleCombination.ADMIN_DOCTOR);
        
        // 医生接口（管理员和医生可以访问）
        mapping.put("/doctor/**", RoleCombination.ADMIN_DOCTOR);
        
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