package com.xitian.smarthealthhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.converter.HealthArticleConverter;
import com.xitian.smarthealthhub.converter.HealthArticleReviewConverter;
import com.xitian.smarthealthhub.domain.dto.HealthArticleCreateDTO;
import com.xitian.smarthealthhub.domain.dto.HealthArticleUpdateDTO;
import com.xitian.smarthealthhub.domain.entity.HealthArticles;
import com.xitian.smarthealthhub.domain.query.HealthArticleQuery;
import com.xitian.smarthealthhub.domain.query.HealthArticlePublicQuery;
import com.xitian.smarthealthhub.domain.vo.HealthArticleVO;
import com.xitian.smarthealthhub.domain.vo.HealthArticleReviewVO;
import com.xitian.smarthealthhub.mapper.HealthArticlesMapper;
import com.xitian.smarthealthhub.service.HealthArticlesService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 健康文章服务实现类
 */
@Service
public class HealthArticlesServiceImpl extends ServiceImpl<HealthArticlesMapper, HealthArticles> implements HealthArticlesService {
    
    /**
     * 分页查询健康文章（供管理端审核使用）
     * @param param 分页参数和查询条件
     * @return 健康文章分页数据
     */
    @Override
    public PageBean<HealthArticleReviewVO> pageQuery(PageParam<HealthArticleQuery> param) {
        // 创建分页对象
        Page<HealthArticles> page = new Page<>(param.getPageNum(), param.getPageSize());
        
        // 构建查询条件
        LambdaQueryWrapper<HealthArticles> queryWrapper = Wrappers.lambdaQuery();
        
        // 只查询未删除的文章
        queryWrapper.eq(HealthArticles::getIsDeleted, (byte) 0);
        
        // 如果有查询条件，则添加查询条件
        if (param.getQuery() != null) {
            HealthArticleQuery query = param.getQuery();
            
            // 文章标题模糊查询
            if (StringUtils.hasText(query.getTitle())) {
                queryWrapper.like(HealthArticles::getTitle, query.getTitle());
            }
            
            // 作者姓名模糊查询
            if (StringUtils.hasText(query.getAuthorName())) {
                queryWrapper.like(HealthArticles::getAuthorName, query.getAuthorName());
            }
            
            // 科室ID精确查询
            if (query.getDeptId() != null) {
                queryWrapper.eq(HealthArticles::getDeptId, query.getDeptId());
            }
            
            // 文章分类查询
            if (StringUtils.hasText(query.getCategory())) {
                queryWrapper.like(HealthArticles::getCategory, query.getCategory());
            }

            // 状态查询 - 单个状态
            if (query.getStatus() != null) {
                queryWrapper.eq(HealthArticles::getStatus, query.getStatus());
            }

            // 状态查询 - 多个状态
            if (query.getStatusList() != null && !query.getStatusList().isEmpty()) {
                queryWrapper.in(HealthArticles::getStatus, query.getStatusList());
            }

            // 时间范围查询 - 创建时间
            if (StringUtils.hasText(query.getCreatedStart()) || StringUtils.hasText(query.getCreatedEnd())) {
                if (StringUtils.hasText(query.getCreatedStart())) {
                    queryWrapper.apply("created_at >= {0}", query.getCreatedStart() + " 00:00:00");
                }
                if (StringUtils.hasText(query.getCreatedEnd())) {
                    queryWrapper.apply("created_at < {0}", query.getCreatedEnd() + " 23:59:59");
                }
            }
        }
        
        // 按创建时间倒序排列
        queryWrapper.orderByDesc(HealthArticles::getCreatedAt);
        
        // 执行分页查询
        Page<HealthArticles> resultPage = this.page(page, queryWrapper);
        
        // 转换为VO对象
        List<HealthArticleReviewVO> voList = HealthArticleReviewConverter.toReviewVOList(resultPage.getRecords());
        
        // 构造并返回PageBean
        return PageBean.of(voList, resultPage.getTotal(), param);
    }
    
    /**
     * 分页查询公开的健康文章（供用户端使用）
     * @param param 分页参数和查询条件
     * @return 健康文章分页数据
     */
    public PageBean<HealthArticleVO> pagePublicArticles(PageParam<HealthArticlePublicQuery> param) {
        // 创建分页对象
        Page<HealthArticles> page = new Page<>(param.getPageNum(), param.getPageSize());
        
        // 构建查询条件 - 只查询已发布的文章
        LambdaQueryWrapper<HealthArticles> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(HealthArticles::getIsDeleted, (byte) 0); // 只查询未删除的文章
        queryWrapper.eq(HealthArticles::getStatus, (byte) 1); // 只查询已发布的文章
        
        // 如果有查询条件，则添加查询条件
        if (param.getQuery() != null) {
            HealthArticlePublicQuery query = param.getQuery();
            
            // 文章标题模糊查询
            if (StringUtils.hasText(query.getTitle())) {
                queryWrapper.like(HealthArticles::getTitle, query.getTitle());
            }
            
            // 作者姓名模糊查询
            if (StringUtils.hasText(query.getAuthorName())) {
                queryWrapper.like(HealthArticles::getAuthorName, query.getAuthorName());
            }
            
            // 科室ID精确查询
            if (query.getDeptId() != null) {
                queryWrapper.eq(HealthArticles::getDeptId, query.getDeptId());
            }
            
            // 文章分类查询
            if (StringUtils.hasText(query.getCategory())) {
                queryWrapper.like(HealthArticles::getCategory, query.getCategory());
            }
            
            // 是否置顶查询
            if (query.getIsTop() != null) {
                queryWrapper.eq(HealthArticles::getIsTop, query.getIsTop());
            }
        }
        
        // 按创建时间倒序排列
        queryWrapper.orderByDesc(HealthArticles::getCreatedAt);
        
        // 执行分页查询
        Page<HealthArticles> resultPage = this.page(page, queryWrapper);
        
        // 转换为VO对象
        List<HealthArticleVO> voList = resultPage.getRecords().stream()
                .map(HealthArticleConverter::toVO)
                .collect(Collectors.toList());
        
        // 构造并返回PageBean
        return PageBean.of(voList, resultPage.getTotal(), param);
    }
    
    /**
     * 根据ID获取健康文章详情
     * @param id 文章ID
     * @return 健康文章详情
     */
    @Override
    public HealthArticleVO getHealthArticleById(Long id) {
        HealthArticles healthArticle = this.getById(id);
        if (healthArticle == null || healthArticle.getIsDeleted() == 1) {
            return null;
        }
        
        // 增加浏览次数
        healthArticle.setViewCount(healthArticle.getViewCount() + 1);
        this.updateById(healthArticle);
        
        return HealthArticleConverter.toVO(healthArticle);
    }
    
    /**
     * 创建健康文章
     * @param articleCreateDTO 文章创建信息
     * @return 操作结果
     */
    @Override
    public boolean createHealthArticle(HealthArticleCreateDTO articleCreateDTO) {
        HealthArticles healthArticle = HealthArticles.builder()
                .title(articleCreateDTO.getTitle())
                .summary(articleCreateDTO.getSummary())
                .content(articleCreateDTO.getContent())
                .coverImageUrl(articleCreateDTO.getCoverImageUrl())
                .authorId(articleCreateDTO.getAuthorId())
                .deptId(articleCreateDTO.getDeptId())
                .category(articleCreateDTO.getCategory())
                .isTop(articleCreateDTO.getIsTop() != null ? articleCreateDTO.getIsTop() : (byte) 0)
                .status(articleCreateDTO.getStatus() != null ? articleCreateDTO.getStatus() : (byte) 3) // 默认为审核中状态
                .viewCount(0)
                .likeCount(0)
                .isDeleted((byte) 0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        return this.save(healthArticle);
    }
    
    /**
     * 更新健康文章
     * @param articleUpdateDTO 文章更新信息
     * @return 操作结果
     */
    @Override
    public boolean updateHealthArticle(HealthArticleUpdateDTO articleUpdateDTO) {
        HealthArticles healthArticle = this.getById(articleUpdateDTO.getId());
        if (healthArticle == null || healthArticle.getIsDeleted() == 1) {
            return false;
        }
        
        healthArticle.setTitle(articleUpdateDTO.getTitle());
        healthArticle.setSummary(articleUpdateDTO.getSummary());
        healthArticle.setContent(articleUpdateDTO.getContent());
        healthArticle.setCoverImageUrl(articleUpdateDTO.getCoverImageUrl());
        healthArticle.setDeptId(articleUpdateDTO.getDeptId());
        healthArticle.setCategory(articleUpdateDTO.getCategory());
        healthArticle.setIsTop(articleUpdateDTO.getIsTop() != null ? articleUpdateDTO.getIsTop() : healthArticle.getIsTop());
        healthArticle.setStatus(articleUpdateDTO.getStatus() != null ? articleUpdateDTO.getStatus() : healthArticle.getStatus());
        healthArticle.setUpdatedAt(LocalDateTime.now());
        
        return this.updateById(healthArticle);
    }
    
    /**
     * 删除健康文章（逻辑删除）
     * @param id 文章ID
     * @return 操作结果
     */
    @Override
    public boolean deleteHealthArticle(Long id) {
        HealthArticles healthArticle = this.getById(id);
        if (healthArticle == null || healthArticle.getIsDeleted() == 1) {
            return false;
        }
        
        healthArticle.setIsDeleted((byte) 1);
        healthArticle.setUpdatedAt(LocalDateTime.now());
        
        return this.updateById(healthArticle);
    }
}