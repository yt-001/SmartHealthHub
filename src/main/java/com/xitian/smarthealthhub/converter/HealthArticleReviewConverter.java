package com.xitian.smarthealthhub.converter;

import com.xitian.smarthealthhub.domain.entity.HealthArticles;
import com.xitian.smarthealthhub.domain.vo.HealthArticleReviewVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 健康文章审核VO转换器
 */
public class HealthArticleReviewConverter {
    
    /**
     * 将HealthArticles实体转换为HealthArticleReviewVO
     * @param entity 健康文章实体
     * @return 健康文章审核VO
     */
    public static HealthArticleReviewVO toReviewVO(HealthArticles entity) {
        if (entity == null) {
            return null;
        }
        
        return HealthArticleReviewVO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .authorName(entity.getAuthorName())
                .deptName(entity.getDeptName())
                .category(entity.getCategory())
                .viewCount(entity.getViewCount())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .build();
    }
    
    /**
     * 将HealthArticles实体列表转换为HealthArticleReviewVO列表
     * @param entityList 健康文章实体列表
     * @return 健康文章审核VO列表
     */
    public static List<HealthArticleReviewVO> toReviewVOList(List<HealthArticles> entityList) {
        if (entityList == null) {
            return null;
        }
        
        return entityList.stream()
                .map(HealthArticleReviewConverter::toReviewVO)
                .collect(Collectors.toList());
    }
}