package com.xitian.smarthealthhub.converter;

import com.xitian.smarthealthhub.domain.entity.HealthArticles;
import com.xitian.smarthealthhub.domain.vo.CategorySimpleVO;
import com.xitian.smarthealthhub.domain.vo.HealthArticleAdminVO;
import com.xitian.smarthealthhub.domain.vo.HealthArticleReviewVO;
import com.xitian.smarthealthhub.service.ArticleCategoriesService;
import com.xitian.smarthealthhub.service.CategoryRelationService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 健康文章管理员列表转换器
 */
public class HealthArticleAdminConverter {
    
    /**
     * 将健康文章实体转换为管理员列表VO对象（包含分类信息）
     * @param healthArticles 健康文章实体
     * @param categoryRelationService 分类关联服务
     * @param articleCategoriesService 文章分类服务
     * @return 健康文章管理员列表VO对象
     */
    public static HealthArticleAdminVO toAdminVO(HealthArticles healthArticles,
                                                 CategoryRelationService categoryRelationService,
                                                 ArticleCategoriesService articleCategoriesService) {
        if (healthArticles == null) {
            return null;
        }
        
        // 先转换为基本的审核VO
        HealthArticleReviewVO reviewVO = HealthArticleReviewConverter.toReviewVO(healthArticles);
        
        // 获取文章的分类ID列表
        List<Long> categoryIds = categoryRelationService.getArticleCategoryIds(healthArticles.getId());
        
        // 根据分类ID列表获取分类简化信息列表
        List<CategorySimpleVO> categories = articleCategoriesService.getArticleCategoriesSimpleByIds(categoryIds);
        
        // 构建管理员VO对象
        HealthArticleAdminVO adminVO = new HealthArticleAdminVO();
        adminVO.setId(reviewVO.getId());
        adminVO.setTitle(reviewVO.getTitle());
        adminVO.setSummary(reviewVO.getSummary());
        adminVO.setAuthorName(reviewVO.getAuthorName());
        adminVO.setDeptName(reviewVO.getDeptName());
        adminVO.setViewCount(reviewVO.getViewCount());
        adminVO.setStatus(reviewVO.getStatus());
        adminVO.setCreatedAt(reviewVO.getCreatedAt());
        adminVO.setCategories(categories);
        
        return adminVO;
    }
    
    /**
     * 将健康文章实体列表转换为管理员列表VO对象列表（包含分类信息）
     * @param healthArticlesList 健康文章实体列表
     * @param categoryRelationService 分类关联服务
     * @param articleCategoriesService 文章分类服务
     * @return 健康文章管理员列表VO对象列表
     */
    public static List<HealthArticleAdminVO> toAdminVOList(List<HealthArticles> healthArticlesList,
                                                           CategoryRelationService categoryRelationService,
                                                           ArticleCategoriesService articleCategoriesService) {
        if (healthArticlesList == null) {
            return null;
        }
        
        return healthArticlesList.stream()
                .map(article -> toAdminVO(article, categoryRelationService, articleCategoriesService))
                .collect(Collectors.toList());
    }
}