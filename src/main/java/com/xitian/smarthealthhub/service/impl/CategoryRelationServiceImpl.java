package com.xitian.smarthealthhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xitian.smarthealthhub.domain.entity.ArticleCategoryRelations;
import com.xitian.smarthealthhub.domain.entity.VideoCategoryRelations;
import com.xitian.smarthealthhub.mapper.ArticleCategoryRelationsMapper;
import com.xitian.smarthealthhub.mapper.VideoCategoryRelationsMapper;
import com.xitian.smarthealthhub.service.CategoryRelationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 分类关联服务实现类
 */
@Service
public class CategoryRelationServiceImpl implements CategoryRelationService {
    
    @Resource
    private ArticleCategoryRelationsMapper articleCategoryRelationsMapper;
    
    @Resource
    private VideoCategoryRelationsMapper videoCategoryRelationsMapper;
    
    /**
     * 保存文章分类关联关系
     * @param articleId 文章ID
     * @param categoryIds 分类ID列表
     * @return 操作结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveArticleCategoryRelations(Long articleId, List<Long> categoryIds) {
        // 先删除原有的关联关系
        deleteArticleCategoryRelations(articleId);
        
        // 如果没有分类ID，则直接返回成功
        if (categoryIds == null || categoryIds.isEmpty()) {
            return true;
        }
        
        // 创建新的关联关系
        List<ArticleCategoryRelations> relationsList = categoryIds.stream()
                .map(categoryId -> {
                    ArticleCategoryRelations relation = new ArticleCategoryRelations();
                    relation.setArticleId(articleId);
                    relation.setCategoryId(categoryId);
                    relation.setCreatedAt(LocalDateTime.now());
                    return relation;
                })
                .collect(Collectors.toList());
        
        // 批量插入新的关联关系
        for (ArticleCategoryRelations relation : relationsList) {
            articleCategoryRelationsMapper.insert(relation);
        }
        return true;
    }
    
    /**
     * 保存视频分类关联关系
     * @param videoId 视频ID
     * @param categoryIds 分类ID列表
     * @return 操作结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveVideoCategoryRelations(Long videoId, List<Long> categoryIds) {
        // 先删除原有的关联关系
        deleteVideoCategoryRelations(videoId);
        
        // 如果没有分类ID，则直接返回成功
        if (categoryIds == null || categoryIds.isEmpty()) {
            return true;
        }
        
        // 创建新的关联关系
        List<VideoCategoryRelations> relationsList = categoryIds.stream()
                .map(categoryId -> {
                    VideoCategoryRelations relation = new VideoCategoryRelations();
                    relation.setVideoId(videoId);
                    relation.setCategoryId(categoryId);
                    relation.setCreatedAt(LocalDateTime.now());
                    return relation;
                })
                .collect(Collectors.toList());
        
        // 批量插入新的关联关系
        for (VideoCategoryRelations relation : relationsList) {
            videoCategoryRelationsMapper.insert(relation);
        }
        return true;
    }
    
    /**
     * 获取文章关联的分类ID列表
     * @param articleId 文章ID
     * @return 分类ID列表
     */
    @Override
    public List<Long> getArticleCategoryIds(Long articleId) {
        LambdaQueryWrapper<ArticleCategoryRelations> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ArticleCategoryRelations::getArticleId, articleId);
        
        List<ArticleCategoryRelations> relationsList = articleCategoryRelationsMapper.selectList(queryWrapper);
        if (relationsList == null || relationsList.isEmpty()) {
            return new ArrayList<>();
        }
        
        return relationsList.stream()
                .map(ArticleCategoryRelations::getCategoryId)
                .collect(Collectors.toList());
    }
    
    /**
     * 获取视频关联的分类ID列表
     * @param videoId 视频ID
     * @return 分类ID列表
     */
    @Override
    public List<Long> getVideoCategoryIds(Long videoId) {
        LambdaQueryWrapper<VideoCategoryRelations> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(VideoCategoryRelations::getVideoId, videoId);
        
        List<VideoCategoryRelations> relationsList = videoCategoryRelationsMapper.selectList(queryWrapper);
        if (relationsList == null || relationsList.isEmpty()) {
            return new ArrayList<>();
        }
        
        return relationsList.stream()
                .map(VideoCategoryRelations::getCategoryId)
                .collect(Collectors.toList());
    }
    
    /**
     * 删除文章的所有分类关联关系
     * @param articleId 文章ID
     * @return 操作结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteArticleCategoryRelations(Long articleId) {
        LambdaQueryWrapper<ArticleCategoryRelations> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ArticleCategoryRelations::getArticleId, articleId);
        return articleCategoryRelationsMapper.delete(queryWrapper) >= 0;
    }
    
    /**
     * 删除视频的所有分类关联关系
     * @param videoId 视频ID
     * @return 操作结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteVideoCategoryRelations(Long videoId) {
        LambdaQueryWrapper<VideoCategoryRelations> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(VideoCategoryRelations::getVideoId, videoId);
        return videoCategoryRelationsMapper.delete(queryWrapper) >= 0;
    }
}