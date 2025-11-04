package com.xitian.smarthealthhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.converter.HealthVideoCommentConverter;
import com.xitian.smarthealthhub.domain.dto.HealthVideoCommentCreateDTO;
import com.xitian.smarthealthhub.domain.entity.HealthVideoComments;
import com.xitian.smarthealthhub.domain.entity.HealthVideos;
import com.xitian.smarthealthhub.domain.query.HealthVideoCommentQuery;
import com.xitian.smarthealthhub.domain.vo.HealthVideoCommentVO;
import com.xitian.smarthealthhub.mapper.HealthVideoCommentsMapper;
import com.xitian.smarthealthhub.mapper.HealthVideosMapper;
import com.xitian.smarthealthhub.service.HealthVideoCommentsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.annotation.Resource;

/**
 * 健康视频评论服务实现类
 */
@Service
public class HealthVideoCommentsServiceImpl extends ServiceImpl<HealthVideoCommentsMapper, HealthVideoComments> implements HealthVideoCommentsService {
    
    @Resource
    private HealthVideosMapper healthVideosMapper;
    
    /**
     * 分页查询健康视频评论
     * @param param 分页参数和查询条件
     * @return 健康视频评论分页数据
     */
    @Override
    public PageBean<HealthVideoCommentVO> pageQuery(PageParam<HealthVideoCommentQuery> param) {
        // 创建分页对象
        Page<HealthVideoComments> page = new Page<>(param.getPageNum(), param.getPageSize());
        
        // 构建查询条件
        LambdaQueryWrapper<HealthVideoComments> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(HealthVideoComments::getStatus, (byte) 1); // 只查询显示状态的评论
        
        // 如果有查询条件，则添加查询条件
        if (param.getQuery() != null) {
            HealthVideoCommentQuery query = param.getQuery();
            
            // 视频ID查询
            if (query.getVideoId() != null && query.getVideoId() > 0) {
                queryWrapper.eq(HealthVideoComments::getVideoId, query.getVideoId());
            }
            
            // 用户ID查询
            if (query.getUserId() != null && query.getUserId() > 0) {
                queryWrapper.eq(HealthVideoComments::getUserId, query.getUserId());
            }
            
            // 父评论ID查询
            if (query.getParentId() != null && query.getParentId() > 0) {
                queryWrapper.eq(HealthVideoComments::getParentId, query.getParentId());
            }
        }
        
        queryWrapper.eq(HealthVideoComments::getIsDeleted, (byte) 0); // 只查询未删除的评论
        
        // 处理排序
        if (param.getSortField() != null && !param.getSortField().isEmpty()) {
            if ("ASC".equalsIgnoreCase(param.getSortDirection().name())) {
                queryWrapper.orderByAsc(HealthVideoComments::getCreatedAt);
            } else {
                queryWrapper.orderByDesc(HealthVideoComments::getCreatedAt);
            }
        } else {
            // 默认按创建时间倒序排列
            queryWrapper.orderByDesc(HealthVideoComments::getCreatedAt);
        }
        
        // 执行分页查询
        Page<HealthVideoComments> resultPage = this.page(page, queryWrapper);
        
        // 转换为VO对象
        List<HealthVideoCommentVO> voList = HealthVideoCommentConverter.toVOList(resultPage.getRecords());
        
        // 构造并返回PageBean
        return PageBean.of(voList, resultPage.getTotal(), param);
    }
    
    /**
     * 根据视频ID获取评论列表
     * @param videoId 视频ID
     * @return 评论列表
     */
    @Override
    public List<HealthVideoCommentVO> getCommentsByVideoId(Long videoId) {
        // 检查视频是否存在且已发布
        HealthVideos video = healthVideosMapper.selectById(videoId);
        if (video == null || video.getIsDeleted() == 1 || video.getStatus() != 1) {
            // 如果视频不存在、已删除或未发布，则不返回评论
            return List.of();
        }
        
        LambdaQueryWrapper<HealthVideoComments> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(HealthVideoComments::getVideoId, videoId);
        queryWrapper.eq(HealthVideoComments::getStatus, (byte) 1); // 只查询显示状态的评论
        queryWrapper.eq(HealthVideoComments::getIsDeleted, (byte) 0); // 只查询未删除的评论
        queryWrapper.orderByDesc(HealthVideoComments::getCreatedAt); // 按创建时间倒序排列
        
        List<HealthVideoComments> commentsList = this.list(queryWrapper);
        return HealthVideoCommentConverter.toVOList(commentsList);
    }
    
    /**
     * 根据ID获取健康视频评论详情
     * @param id 评论ID
     * @return 健康视频评论详情
     */
    @Override
    public HealthVideoCommentVO getHealthVideoCommentById(Long id) {
        HealthVideoComments comment = this.getById(id);
        if (comment == null || comment.getStatus() != (byte) 1 || comment.getIsDeleted() != (byte) 0) {
            return null;
        }
        return HealthVideoCommentConverter.toVO(comment);
    }
    
    /**
     * 创建健康视频评论
     * @param commentCreateDTO 评论创建信息
     * @return 操作结果
     */
    @Override
    public boolean createHealthVideoComment(HealthVideoCommentCreateDTO commentCreateDTO) {
        // 检查视频是否存在且已发布
        HealthVideos video = healthVideosMapper.selectById(commentCreateDTO.getVideoId());
        if (video == null || video.getIsDeleted() == 1 || video.getStatus() != 1) {
            // 如果视频不存在、已删除或未发布，则不允许评论
            return false;
        }
        
        HealthVideoComments comment = new HealthVideoComments();
        comment.setVideoId(commentCreateDTO.getVideoId());
        comment.setUserId(commentCreateDTO.getUserId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setReplyToId(commentCreateDTO.getReplyToId());
        comment.setLikeCount(0); // 初始点赞数为0
        comment.setStatus((byte) 1); // 显示状态
        comment.setIsDeleted((byte) 0); // 未删除
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());
        
        return this.save(comment);
    }
    
    /**
     * 删除健康视频评论（逻辑删除）
     * @param id 评论ID
     * @return 操作结果
     */
    @Override
    public boolean deleteHealthVideoComment(Long id) {
        HealthVideoComments comment = this.getById(id);
        if (comment == null) {
            return false;
        }
        
        // 逻辑删除，将状态设置为已删除
        comment.setIsDeleted((byte) 1);
        comment.setUpdatedAt(LocalDateTime.now());
        return this.updateById(comment);
    }
}