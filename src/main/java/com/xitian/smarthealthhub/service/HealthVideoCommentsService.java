package com.xitian.smarthealthhub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.domain.dto.HealthVideoCommentCreateDTO;
import com.xitian.smarthealthhub.domain.entity.HealthVideoComments;
import com.xitian.smarthealthhub.domain.query.HealthVideoCommentQuery;
import com.xitian.smarthealthhub.domain.vo.HealthVideoCommentVO;

import java.util.List;

/**
 * 健康视频评论服务接口
 */
public interface HealthVideoCommentsService extends IService<HealthVideoComments> {
    
    /**
     * 分页查询健康视频评论
     * @param param 分页参数和查询条件
     * @return 健康视频评论分页数据
     */
    PageBean<HealthVideoCommentVO> pageQuery(PageParam<HealthVideoCommentQuery> param);
    
    /**
     * 根据视频ID获取评论列表
     * @param videoId 视频ID
     * @return 评论列表
     */
    List<HealthVideoCommentVO> getCommentsByVideoId(Long videoId);
    
    /**
     * 根据ID获取健康视频评论详情
     * @param id 评论ID
     * @return 健康视频评论详情
     */
    HealthVideoCommentVO getHealthVideoCommentById(Long id);
    
    /**
     * 创建健康视频评论
     * @param commentCreateDTO 评论创建信息
     * @return 操作结果
     */
    boolean createHealthVideoComment(HealthVideoCommentCreateDTO commentCreateDTO);
    
    /**
     * 删除健康视频评论（逻辑删除）
     * @param id 评论ID
     * @return 操作结果
     */
    boolean deleteHealthVideoComment(Long id);
}