package com.xitian.smarthealthhub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xitian.smarthealthhub.domain.entity.ArticleCategoryRelations;
import com.xitian.smarthealthhub.domain.entity.VideoCategoryRelations;

import java.util.List;

/**
 * 分类关联服务接口
 */
public interface CategoryRelationService {
    
    /**
     * 保存文章分类关联关系
     * @param articleId 文章ID
     * @param categoryIds 分类ID列表
     * @return 操作结果
     */
    boolean saveArticleCategoryRelations(Long articleId, List<Long> categoryIds);
    
    /**
     * 保存视频分类关联关系
     * @param videoId 视频ID
     * @param categoryIds 分类ID列表
     * @return 操作结果
     */
    boolean saveVideoCategoryRelations(Long videoId, List<Long> categoryIds);
    
    /**
     * 获取文章关联的分类ID列表
     * @param articleId 文章ID
     * @return 分类ID列表
     */
    List<Long> getArticleCategoryIds(Long articleId);
    
    /**
     * 获取视频关联的分类ID列表
     * @param videoId 视频ID
     * @return 分类ID列表
     */
    List<Long> getVideoCategoryIds(Long videoId);
    
    /**
     * 删除文章的所有分类关联关系
     * @param articleId 文章ID
     * @return 操作结果
     */
    boolean deleteArticleCategoryRelations(Long articleId);
    
    /**
     * 删除视频的所有分类关联关系
     * @param videoId 视频ID
     * @return 操作结果
     */
    boolean deleteVideoCategoryRelations(Long videoId);
}