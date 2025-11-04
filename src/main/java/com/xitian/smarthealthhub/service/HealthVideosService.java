package com.xitian.smarthealthhub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.domain.dto.HealthVideoCreateDTO;
import com.xitian.smarthealthhub.domain.dto.HealthVideoUpdateDTO;
import com.xitian.smarthealthhub.domain.entity.HealthVideos;
import com.xitian.smarthealthhub.domain.query.HealthVideoQuery;
import com.xitian.smarthealthhub.domain.vo.HealthVideoVO;
import com.xitian.smarthealthhub.domain.vo.HealthVideoReviewVO;

/**
 * 健康视频服务接口
 */
public interface HealthVideosService extends IService<HealthVideos> {
    
    /**
     * 分页查询健康视频（供管理端使用）
     * @param param 分页参数和查询条件
     * @return 健康视频分页数据
     */
    PageBean<HealthVideoReviewVO> pageQuery(PageParam<HealthVideoQuery> param);
    
    /**
     * 分页查询公开的健康视频（供用户端使用）
     * @param param 分页参数和查询条件
     * @return 健康视频分页数据
     */
    PageBean<HealthVideoVO> pagePublicVideos(PageParam<HealthVideoQuery> param);
    
    /**
     * 根据ID获取健康视频详情
     * @param id 视频ID
     * @return 健康视频详情
     */
    HealthVideoVO getHealthVideoById(Long id);
    
    /**
     * 根据ID获取公开的健康视频详情（供用户端使用）
     * @param id 视频ID
     * @return 健康视频详情
     */
    HealthVideoVO getPublicHealthVideoById(Long id);
    
    /**
     * 创建健康视频
     * @param videoCreateDTO 视频创建信息
     * @return 操作结果
     */
    boolean createHealthVideo(HealthVideoCreateDTO videoCreateDTO);
    
    /**
     * 更新健康视频
     * @param videoUpdateDTO 视频更新信息
     * @return 操作结果
     */
    boolean updateHealthVideo(HealthVideoUpdateDTO videoUpdateDTO);
    
    /**
     * 删除健康视频（逻辑删除）
     * @param id 视频ID
     * @return 操作结果
     */
    boolean deleteHealthVideo(Long id);
}