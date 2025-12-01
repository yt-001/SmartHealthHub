package com.xitian.smarthealthhub.converter;

import com.xitian.smarthealthhub.domain.entity.ArticleCategories;
import com.xitian.smarthealthhub.domain.vo.ArticleCategoriesVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 文章分类转换器
 */
public class ArticleCategoriesConverter {
    
    /**
     * 将文章分类实体转换为VO
     * @param articleCategories 文章分类实体
     * @return 文章分类VO
     */
    public static ArticleCategoriesVO toVO(ArticleCategories articleCategories) {
        if (articleCategories == null) {
            return null;
        }
        
        ArticleCategoriesVO vo = new ArticleCategoriesVO();
        vo.setId(articleCategories.getId());
        vo.setName(articleCategories.getName());
        vo.setDescription(articleCategories.getDescription());
        vo.setSortOrder(articleCategories.getSortOrder());
        vo.setIsEnabled(articleCategories.getIsEnabled());
        return vo;
    }
    
    /**
     * 将文章分类实体列表转换为VO列表
     * @param articleCategoriesList 文章分类实体列表
     * @return 文章分类VO列表
     */
    public static List<ArticleCategoriesVO> toVOList(List<ArticleCategories> articleCategoriesList) {
        if (articleCategoriesList == null) {
            return null;
        }
        
        List<ArticleCategoriesVO> voList = new ArrayList<>();
        for (ArticleCategories articleCategory : articleCategoriesList) {
            voList.add(toVO(articleCategory));
        }
        return voList;
    }
}