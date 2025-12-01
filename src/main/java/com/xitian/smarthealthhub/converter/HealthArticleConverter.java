package com.xitian.smarthealthhub.converter;

import com.xitian.smarthealthhub.domain.entity.HealthArticles;
import com.xitian.smarthealthhub.domain.vo.HealthArticleVO;
import org.springframework.beans.BeanUtils;

/**
 * 健康文章转换器
 */
public class HealthArticleConverter {
    
    /**
     * 将HealthArticles实体转换为HealthArticleVO视图对象
     * @param healthArticles 健康文章实体
     * @return 健康文章视图对象
     */
    public static HealthArticleVO toVO(HealthArticles healthArticles) {
        if (healthArticles == null) {
            return null;
        }
        
        HealthArticleVO vo = new HealthArticleVO();
        BeanUtils.copyProperties(healthArticles, vo);
        vo.setCategory(healthArticles.getCategory());
        return vo;
    }
    
    /**
     * 将HealthArticleVO视图对象转换为HealthArticles实体
     * @param healthArticleVO 健康文章视图对象
     * @return 健康文章实体
     */
    public static HealthArticles toEntity(HealthArticleVO healthArticleVO) {
        if (healthArticleVO == null) {
            return null;
        }
        
        HealthArticles entity = new HealthArticles();
        BeanUtils.copyProperties(healthArticleVO, entity);
        return entity;
    }
}