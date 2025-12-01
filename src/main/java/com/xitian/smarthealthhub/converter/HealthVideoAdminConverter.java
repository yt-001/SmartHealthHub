package com.xitian.smarthealthhub.converter;

import com.xitian.smarthealthhub.domain.entity.HealthVideos;
import com.xitian.smarthealthhub.domain.vo.CategorySimpleVO;
import com.xitian.smarthealthhub.domain.vo.HealthVideoAdminVO;
import com.xitian.smarthealthhub.domain.vo.HealthVideoReviewVO;
import com.xitian.smarthealthhub.service.CategoryRelationService;
import com.xitian.smarthealthhub.service.VideoCategoriesService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 健康视频管理员列表转换器
 */
public class HealthVideoAdminConverter {
    
    /**
     * 将健康视频实体转换为管理员列表VO对象（包含分类信息）
     * @param healthVideos 健康视频实体
     * @param categoryRelationService 分类关联服务
     * @param videoCategoriesService 视频分类服务
     * @return 健康视频管理员列表VO对象
     */
    public static HealthVideoAdminVO toAdminVO(HealthVideos healthVideos, 
                                               CategoryRelationService categoryRelationService,
                                               VideoCategoriesService videoCategoriesService) {
        if (healthVideos == null) {
            return null;
        }
        
        // 先转换为基本的审核VO
        HealthVideoReviewVO reviewVO = HealthVideoReviewConverter.toReviewVO(healthVideos);
        
        // 获取视频的分类ID列表
        List<Long> categoryIds = categoryRelationService.getVideoCategoryIds(healthVideos.getId());
        
        // 根据分类ID列表获取分类简化信息列表
        List<CategorySimpleVO> categories = videoCategoriesService.getVideoCategoriesSimpleByIds(categoryIds);
        
        // 构建管理员VO对象
        HealthVideoAdminVO adminVO = new HealthVideoAdminVO();
        adminVO.setId(reviewVO.getId());
        adminVO.setTitle(reviewVO.getTitle());
        adminVO.setAuthorName(reviewVO.getAuthorName());
        adminVO.setViewCount(reviewVO.getViewCount());
        adminVO.setCoverImageUrl(reviewVO.getCoverImageUrl());
        adminVO.setDuration(reviewVO.getDuration());
        adminVO.setStatus(reviewVO.getStatus());
        adminVO.setCreatedAt(reviewVO.getCreatedAt());
        adminVO.setCategories(categories);
        
        return adminVO;
    }
    
    /**
     * 将健康视频实体列表转换为管理员列表VO对象列表（包含分类信息）
     * @param healthVideosList 健康视频实体列表
     * @param categoryRelationService 分类关联服务
     * @param videoCategoriesService 视频分类服务
     * @return 健康视频管理员列表VO对象列表
     */
    public static List<HealthVideoAdminVO> toAdminVOList(List<HealthVideos> healthVideosList,
                                                         CategoryRelationService categoryRelationService,
                                                         VideoCategoriesService videoCategoriesService) {
        if (healthVideosList == null) {
            return null;
        }
        
        return healthVideosList.stream()
                .map(video -> toAdminVO(video, categoryRelationService, videoCategoriesService))
                .collect(Collectors.toList());
    }
}