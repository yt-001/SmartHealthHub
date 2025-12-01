package com.xitian.smarthealthhub.converter;

import com.xitian.smarthealthhub.domain.entity.ArticleCategories;
import com.xitian.smarthealthhub.domain.entity.VideoCategories;
import com.xitian.smarthealthhub.domain.vo.CategorySimpleVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类简化信息转换器
 */
public class CategorySimpleConverter {
    
    /**
     * 将文章分类实体转换为简化VO
     * @param articleCategories 文章分类实体
     * @return 分类简化信息VO
     */
    public static CategorySimpleVO fromArticleCategory(ArticleCategories articleCategories) {
        if (articleCategories == null) {
            return null;
        }
        
        CategorySimpleVO vo = new CategorySimpleVO();
        vo.setId(articleCategories.getId());
        vo.setName(articleCategories.getName());
        return vo;
    }
    
    /**
     * 将视频分类实体转换为简化VO
     * @param videoCategories 视频分类实体
     * @return 分类简化信息VO
     */
    public static CategorySimpleVO fromVideoCategory(VideoCategories videoCategories) {
        if (videoCategories == null) {
            return null;
        }
        
        CategorySimpleVO vo = new CategorySimpleVO();
        vo.setId(videoCategories.getId());
        vo.setName(videoCategories.getName());
        return vo;
    }
    
    /**
     * 将文章分类实体列表转换为简化VO列表
     * @param articleCategoriesList 文章分类实体列表
     * @return 分类简化信息VO列表
     */
    public static List<CategorySimpleVO> fromArticleCategoryList(List<ArticleCategories> articleCategoriesList) {
        if (articleCategoriesList == null) {
            return null;
        }
        
        List<CategorySimpleVO> voList = new ArrayList<>();
        for (ArticleCategories articleCategory : articleCategoriesList) {
            voList.add(fromArticleCategory(articleCategory));
        }
        return voList;
    }
    
    /**
     * 将视频分类实体列表转换为简化VO列表
     * @param videoCategoriesList 视频分类实体列表
     * @return 分类简化信息VO列表
     */
    public static List<CategorySimpleVO> fromVideoCategoryList(List<VideoCategories> videoCategoriesList) {
        if (videoCategoriesList == null) {
            return null;
        }
        
        List<CategorySimpleVO> voList = new ArrayList<>();
        for (VideoCategories videoCategory : videoCategoriesList) {
            voList.add(fromVideoCategory(videoCategory));
        }
        return voList;
    }
}