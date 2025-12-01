package com.xitian.smarthealthhub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.domain.dto.VideoCategoriesCreateDTO;
import com.xitian.smarthealthhub.domain.dto.VideoCategoriesUpdateDTO;
import com.xitian.smarthealthhub.domain.entity.VideoCategories;
import com.xitian.smarthealthhub.domain.query.VideoCategoriesQuery;
import com.xitian.smarthealthhub.domain.vo.CategorySimpleVO;
import com.xitian.smarthealthhub.domain.vo.VideoCategoriesVO;

import java.util.List;

/**
 * 视频分类服务接口
 */
public interface VideoCategoriesService extends IService<VideoCategories> {
    
    /**
     * 分页查询视频分类
     * @param param 分页参数和查询条件
     * @return 视频分类分页数据
     */
    PageBean<VideoCategoriesVO> pageQuery(PageParam<VideoCategoriesQuery> param);
    
    /**
     * 获取所有启用的视频分类（简化信息）
     * @return 视频分类简化信息列表
     */
    List<CategorySimpleVO> getAllEnabledCategoriesSimple();
    
    /**
     * 根据ID列表获取视频分类（简化信息）
     * @param ids 分类ID列表
     * @return 视频分类简化信息列表
     */
    List<CategorySimpleVO> getVideoCategoriesSimpleByIds(List<Long> ids);
    
    /**
     * 获取所有启用的视频分类
     * @return 视频分类列表
     */
    List<VideoCategoriesVO> getAllEnabledCategories();
    
    /**
     * 根据ID获取视频分类详情
     * @param id 分类ID
     * @return 视频分类详情
     */
    VideoCategoriesVO getVideoCategoryById(Long id);
    
    /**
     * 创建视频分类
     * @param videoCategoriesCreateDTO 视频分类创建信息
     * @return 操作结果
     */
    boolean createVideoCategory(VideoCategoriesCreateDTO videoCategoriesCreateDTO);
    
    /**
     * 更新视频分类
     * @param videoCategoriesUpdateDTO 视频分类更新信息
     * @return 操作结果
     */
    boolean updateVideoCategory(VideoCategoriesUpdateDTO videoCategoriesUpdateDTO);
    
    /**
     * 删除视频分类（逻辑删除）
     * @param id 分类ID
     * @return 操作结果
     */
    boolean deleteVideoCategory(Long id);
}