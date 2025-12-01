package com.xitian.smarthealthhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.converter.HealthVideoConverter;
import com.xitian.smarthealthhub.converter.HealthVideoReviewConverter;
import com.xitian.smarthealthhub.domain.dto.HealthVideoCreateDTO;
import com.xitian.smarthealthhub.domain.dto.HealthVideoUpdateDTO;
import com.xitian.smarthealthhub.domain.entity.HealthVideos;
import com.xitian.smarthealthhub.domain.query.HealthVideoQuery;
import com.xitian.smarthealthhub.domain.vo.HealthVideoVO;
import com.xitian.smarthealthhub.domain.vo.HealthVideoReviewVO;
import com.xitian.smarthealthhub.mapper.HealthVideosMapper;
import com.xitian.smarthealthhub.service.CategoryRelationService;
import com.xitian.smarthealthhub.service.HealthVideosService;
import com.xitian.smarthealthhub.util.CategoryUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import jakarta.annotation.Resource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.xitian.smarthealthhub.domain.entity.Users;
import com.xitian.smarthealthhub.mapper.UsersMapper;

/**
 * 健康视频服务实现类
 */
@Service
public class HealthVideosServiceImpl extends ServiceImpl<HealthVideosMapper, HealthVideos> implements HealthVideosService {
    
    @Resource
    private UsersMapper usersMapper;
    
    @Resource
    private CategoryRelationService categoryRelationService;
    
    /**
     * 获取作者姓名
     * @param authorId 作者ID
     * @return 作者姓名
     */
    private String getAuthorName(Long authorId) {
        Users user = usersMapper.selectById(authorId);
        return user != null ? user.getRealName() : "";
    }
    
    /**
     * 分页查询健康视频（供管理端使用）
     * @param param 分页参数和查询条件
     * @return 健康视频分页数据
     */
    @Override
    public PageBean<HealthVideoReviewVO> pageQuery(PageParam<HealthVideoQuery> param) {
        // 创建分页对象
        Page<HealthVideos> page = new Page<>(param.getPageNum(), param.getPageSize());
        
        // 构建查询条件
        LambdaQueryWrapper<HealthVideos> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(HealthVideos::getIsDeleted, (byte) 0); // 只查询未删除的视频
        
        // 如果有查询条件，则添加查询条件
        if (param.getQuery() != null) {
            HealthVideoQuery query = param.getQuery();
            
            // 视频标题模糊查询
            if (StringUtils.hasText(query.getTitle())) {
                queryWrapper.like(HealthVideos::getTitle, query.getTitle());
            }
            
            // 作者姓名模糊查询
            if (StringUtils.hasText(query.getAuthorName())) {
                queryWrapper.like(HealthVideos::getAuthorName, query.getAuthorName());
            }
            
            // 视频分类查询
            if (StringUtils.hasText(query.getCategory())) {
                queryWrapper.like(HealthVideos::getCategory, query.getCategory());
            }
            
            // 是否置顶查询
            if (query.getIsTop() != null) {
                queryWrapper.eq(HealthVideos::getIsTop, query.getIsTop());
            }
            
            // 状态查询 - 单个状态
            if (query.getStatus() != null) {
                queryWrapper.eq(HealthVideos::getStatus, query.getStatus());
            }
            
            // 状态查询 - 多个状态
            if (query.getStatusList() != null && !query.getStatusList().isEmpty()) {
                queryWrapper.in(HealthVideos::getStatus, query.getStatusList());
            }
            
            // 时间范围查询 - 创建时间
            if (StringUtils.hasText(query.getCreatedStart()) || StringUtils.hasText(query.getCreatedEnd())) {
                if (StringUtils.hasText(query.getCreatedStart())) {
                    queryWrapper.apply("created_at >= {0}", query.getCreatedStart() + " 00:00:00");
                }
                if (StringUtils.hasText(query.getCreatedEnd())) {
                    queryWrapper.apply("created_at <= {0}", query.getCreatedEnd() + " 23:59:59");
                }
            }
        }
        
        // 按创建时间倒序排列
        queryWrapper.orderByDesc(HealthVideos::getCreatedAt);
        
        // 执行分页查询
        Page<HealthVideos> resultPage = this.page(page, queryWrapper);
        
        // 转换为VO对象
        List<HealthVideoReviewVO> voList = HealthVideoReviewConverter.toReviewVOList(resultPage.getRecords());
        
        // 构造并返回PageBean
        return PageBean.of(voList, resultPage.getTotal(), param);
    }
    
    /**
     * 分页查询公开的健康视频（供用户端使用）
     * @param param 分页参数和查询条件
     * @return 健康视频分页数据
     */
    public PageBean<HealthVideoVO> pagePublicVideos(PageParam<HealthVideoQuery> param) {
        // 创建分页对象
        Page<HealthVideos> page = new Page<>(param.getPageNum(), param.getPageSize());
        
        // 构建查询条件 - 只查询已发布的视频
        LambdaQueryWrapper<HealthVideos> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(HealthVideos::getIsDeleted, (byte) 0); // 只查询未删除的视频
        queryWrapper.eq(HealthVideos::getStatus, (byte) 1); // 只查询已发布的视频
        
        // 如果有查询条件，则添加查询条件
        if (param.getQuery() != null) {
            HealthVideoQuery query = param.getQuery();
            
            // 视频标题模糊查询
            if (StringUtils.hasText(query.getTitle())) {
                queryWrapper.like(HealthVideos::getTitle, query.getTitle());
            }
            
            // 作者姓名模糊查询
            if (StringUtils.hasText(query.getAuthorName())) {
                queryWrapper.like(HealthVideos::getAuthorName, query.getAuthorName());
            }
            
            // 视频分类查询
            if (StringUtils.hasText(query.getCategory())) {
                queryWrapper.like(HealthVideos::getCategory, query.getCategory());
            }
            
            // 是否置顶查询
            if (query.getIsTop() != null) {
                queryWrapper.eq(HealthVideos::getIsTop, query.getIsTop());
            }
            
            // 时间范围查询 - 创建时间
            if (StringUtils.hasText(query.getCreatedStart()) || StringUtils.hasText(query.getCreatedEnd())) {
                if (StringUtils.hasText(query.getCreatedStart())) {
                    queryWrapper.apply("created_at >= {0}", query.getCreatedStart() + " 00:00:00");
                }
                if (StringUtils.hasText(query.getCreatedEnd())) {
                    queryWrapper.apply("created_at <= {0}", query.getCreatedEnd() + " 23:59:59");
                }
            }
        }
        
        // 按创建时间倒序排列
        queryWrapper.orderByDesc(HealthVideos::getCreatedAt);
        
        // 执行分页查询
        Page<HealthVideos> resultPage = this.page(page, queryWrapper);
        
        // 转换为VO对象
        List<HealthVideoVO> voList = HealthVideoConverter.toVOList(resultPage.getRecords());
        
        // 构造并返回PageBean
        return PageBean.of(voList, resultPage.getTotal(), param);
    }
    
    /**
     * 根据ID获取健康视频详情
     * @param id 视频ID
     * @return 健康视频详情
     */
    @Override
    public HealthVideoVO getHealthVideoById(Long id) {
        HealthVideos healthVideo = this.getById(id);
        if (healthVideo == null || healthVideo.getIsDeleted() == 1) {
            return null;
        }
        
        // 增加浏览次数
        healthVideo.setViewCount(healthVideo.getViewCount() + 1);
        this.updateById(healthVideo);
        
        return HealthVideoConverter.toVO(healthVideo);
    }
    
    /**
     * 根据ID获取公开的健康视频详情（供用户端使用）
     * @param id 视频ID
     * @return 健康视频详情
     */
    public HealthVideoVO getPublicHealthVideoById(Long id) {
        HealthVideos healthVideo = this.getById(id);
        // 只有已发布的视频才能被公开访问
        if (healthVideo == null || healthVideo.getIsDeleted() == 1 || healthVideo.getStatus() != 1) {
            return null;
        }
        
        // 增加浏览次数
        healthVideo.setViewCount(healthVideo.getViewCount() + 1);
        this.updateById(healthVideo);
        
        return HealthVideoConverter.toVO(healthVideo);
    }
    
    /**
     * 创建健康视频
     * @param videoCreateDTO 视频创建信息
     * @return 操作结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createHealthVideo(HealthVideoCreateDTO videoCreateDTO) {
        HealthVideos healthVideo = HealthVideos.builder()
                .title(videoCreateDTO.getTitle())
                .description(videoCreateDTO.getDescription())
                .videoUrl(videoCreateDTO.getVideoUrl())
                .coverImageUrl(videoCreateDTO.getCoverImageUrl())
                .duration(videoCreateDTO.getDuration())
                .authorId(videoCreateDTO.getAuthorId())
                .authorName(getAuthorName(videoCreateDTO.getAuthorId()))
                .category(videoCreateDTO.getCategory())
                .isTop(videoCreateDTO.getIsTop() != null ? videoCreateDTO.getIsTop() : (byte) 0)
                .status(videoCreateDTO.getStatus() != null ? videoCreateDTO.getStatus() : (byte) 3) // 默认为审核中状态
                .viewCount(0)
                .likeCount(0)
                .commentCount(0)
                .isDeleted((byte) 0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        boolean saved = this.save(healthVideo);
        
        // 如果视频保存成功且有分类ID列表，则保存分类关联关系
        if (saved && videoCreateDTO.getCategory() != null && !videoCreateDTO.getCategory().isEmpty()) {
            List<Long> categoryIds = CategoryUtils.parseCategoryIdsFromJson(videoCreateDTO.getCategory());
            categoryRelationService.saveVideoCategoryRelations(healthVideo.getId(), categoryIds);
        }
        
        return saved;
    }
    
    /**
     * 更新健康视频
     * @param videoUpdateDTO 视频更新信息
     * @return 操作结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateHealthVideo(HealthVideoUpdateDTO videoUpdateDTO) {
        HealthVideos healthVideo = this.getById(videoUpdateDTO.getId());
        if (healthVideo == null || healthVideo.getIsDeleted() == 1) {
            return false;
        }
        
        healthVideo.setTitle(videoUpdateDTO.getTitle());
        healthVideo.setDescription(videoUpdateDTO.getDescription());
        healthVideo.setVideoUrl(videoUpdateDTO.getVideoUrl());
        healthVideo.setCoverImageUrl(videoUpdateDTO.getCoverImageUrl());
        healthVideo.setDuration(videoUpdateDTO.getDuration());
        healthVideo.setCategory(videoUpdateDTO.getCategory());
        healthVideo.setIsTop(videoUpdateDTO.getIsTop() != null ? videoUpdateDTO.getIsTop() : healthVideo.getIsTop());
        healthVideo.setStatus(videoUpdateDTO.getStatus() != null ? videoUpdateDTO.getStatus() : healthVideo.getStatus());
        healthVideo.setUpdatedAt(LocalDateTime.now());
        
        boolean updated = this.updateById(healthVideo);
        
        // 如果视频更新成功且有分类ID列表，则更新分类关联关系
        if (updated && videoUpdateDTO.getCategory() != null) {
            List<Long> categoryIds = CategoryUtils.parseCategoryIdsFromJson(videoUpdateDTO.getCategory());
            categoryRelationService.saveVideoCategoryRelations(healthVideo.getId(), categoryIds);
        }
        
        return updated;
    }
    
    /**
     * 删除健康视频（逻辑删除）
     * @param id 视频ID
     * @return 操作结果
     */
    @Override
    public boolean deleteHealthVideo(Long id) {
        HealthVideos healthVideo = this.getById(id);
        if (healthVideo == null || healthVideo.getIsDeleted() == 1) {
            return false;
        }
        
        healthVideo.setIsDeleted((byte) 1);
        healthVideo.setUpdatedAt(LocalDateTime.now());
        
        return this.updateById(healthVideo);
    }
}