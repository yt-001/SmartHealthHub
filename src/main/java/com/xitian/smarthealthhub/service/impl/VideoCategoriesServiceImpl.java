package com.xitian.smarthealthhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.converter.CategorySimpleConverter;
import com.xitian.smarthealthhub.converter.VideoCategoriesConverter;
import com.xitian.smarthealthhub.domain.dto.VideoCategoriesCreateDTO;
import com.xitian.smarthealthhub.domain.dto.VideoCategoriesUpdateDTO;
import com.xitian.smarthealthhub.domain.entity.VideoCategories;
import com.xitian.smarthealthhub.domain.query.VideoCategoriesQuery;
import com.xitian.smarthealthhub.domain.vo.CategorySimpleVO;
import com.xitian.smarthealthhub.domain.vo.VideoCategoriesVO;
import com.xitian.smarthealthhub.mapper.VideoCategoriesMapper;
import com.xitian.smarthealthhub.service.VideoCategoriesService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 视频分类服务实现类
 */
@Service
public class VideoCategoriesServiceImpl extends ServiceImpl<VideoCategoriesMapper, VideoCategories> implements VideoCategoriesService {
    
    /**
     * 分页查询视频分类
     * @param param 分页参数和查询条件
     * @return 视频分类分页数据
     */
    @Override
    public PageBean<VideoCategoriesVO> pageQuery(PageParam<VideoCategoriesQuery> param) {
        // 创建分页对象
        Page<VideoCategories> page = new Page<>(param.getPageNum(), param.getPageSize());
        
        // 构建查询条件
        LambdaQueryWrapper<VideoCategories> queryWrapper = Wrappers.lambdaQuery();
        
        // 如果有查询条件，则添加查询条件
        if (param.getQuery() != null) {
            VideoCategoriesQuery query = param.getQuery();
            
            // 分类名称模糊查询
            if (StringUtils.hasText(query.getName())) {
                queryWrapper.like(VideoCategories::getName, query.getName());
            }
            
            // 是否启用精确查询
            if (query.getIsEnabled() != null) {
                queryWrapper.eq(VideoCategories::getIsEnabled, query.getIsEnabled());
            }
        }
        
        // 按排序字段升序排列，再按创建时间倒序排列
        queryWrapper.orderByAsc(VideoCategories::getSortOrder);
        queryWrapper.orderByDesc(VideoCategories::getCreatedAt);
        
        // 执行分页查询
        Page<VideoCategories> resultPage = this.page(page, queryWrapper);
        
        // 转换为VO对象
        List<VideoCategoriesVO> voList = VideoCategoriesConverter.toVOList(resultPage.getRecords());
        
        // 构造并返回PageBean
        return PageBean.of(voList, resultPage.getTotal(), param);
    }
    
    /**
     * 获取所有启用的视频分类（简化信息）
     * @return 视频分类简化信息列表
     */
    @Override
    public List<CategorySimpleVO> getAllEnabledCategoriesSimple() {
        LambdaQueryWrapper<VideoCategories> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(VideoCategories::getIsEnabled, (byte) 1);
        queryWrapper.orderByAsc(VideoCategories::getSortOrder);
        List<VideoCategories> categoriesList = this.list(queryWrapper);
        return CategorySimpleConverter.fromVideoCategoryList(categoriesList);
    }
    
    /**
     * 根据ID列表获取视频分类（简化信息）
     * @param ids 分类ID列表
     * @return 视频分类简化信息列表
     */
    @Override
    public List<CategorySimpleVO> getVideoCategoriesSimpleByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        
        LambdaQueryWrapper<VideoCategories> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(VideoCategories::getId, ids);
        queryWrapper.eq(VideoCategories::getIsEnabled, (byte) 1);
        queryWrapper.orderByAsc(VideoCategories::getSortOrder);
        List<VideoCategories> categoriesList = this.list(queryWrapper);
        return CategorySimpleConverter.fromVideoCategoryList(categoriesList);
    }
    
    /**
     * 获取所有启用的视频分类
     * @return 视频分类列表
     */
    @Override
    public List<VideoCategoriesVO> getAllEnabledCategories() {
        LambdaQueryWrapper<VideoCategories> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(VideoCategories::getIsEnabled, (byte) 1);
        queryWrapper.orderByAsc(VideoCategories::getSortOrder);
        List<VideoCategories> categoriesList = this.list(queryWrapper);
        return VideoCategoriesConverter.toVOList(categoriesList);
    }
    
    /**
     * 根据ID获取视频分类详情
     * @param id 分类ID
     * @return 视频分类详情
     */
    @Override
    public VideoCategoriesVO getVideoCategoryById(Long id) {
        VideoCategories videoCategory = this.getById(id);
        if (videoCategory == null || videoCategory.getIsEnabled() == 0) {
            return null;
        }
        return VideoCategoriesConverter.toVO(videoCategory);
    }
    
    /**
     * 创建视频分类
     * @param videoCategoriesCreateDTO 视频分类创建信息
     * @return 操作结果
     */
    @Override
    public boolean createVideoCategory(VideoCategoriesCreateDTO videoCategoriesCreateDTO) {
        VideoCategories videoCategory = new VideoCategories();
        videoCategory.setName(videoCategoriesCreateDTO.getName());
        videoCategory.setDescription(videoCategoriesCreateDTO.getDescription());
        videoCategory.setSortOrder(videoCategoriesCreateDTO.getSortOrder() != null ? videoCategoriesCreateDTO.getSortOrder() : 0);
        videoCategory.setIsEnabled(videoCategoriesCreateDTO.getIsEnabled() != null ? videoCategoriesCreateDTO.getIsEnabled() : (byte) 1);
        videoCategory.setCreatedAt(LocalDateTime.now());
        videoCategory.setUpdatedAt(LocalDateTime.now());
        
        return this.save(videoCategory);
    }
    
    /**
     * 更新视频分类
     * @param videoCategoriesUpdateDTO 视频分类更新信息
     * @return 操作结果
     */
    @Override
    public boolean updateVideoCategory(VideoCategoriesUpdateDTO videoCategoriesUpdateDTO) {
        VideoCategories videoCategory = this.getById(videoCategoriesUpdateDTO.getId());
        if (videoCategory == null || videoCategory.getIsEnabled() == 0) {
            return false;
        }
        
        videoCategory.setName(videoCategoriesUpdateDTO.getName());
        videoCategory.setDescription(videoCategoriesUpdateDTO.getDescription());
        videoCategory.setSortOrder(videoCategoriesUpdateDTO.getSortOrder() != null ? videoCategoriesUpdateDTO.getSortOrder() : videoCategory.getSortOrder());
        videoCategory.setIsEnabled(videoCategoriesUpdateDTO.getIsEnabled() != null ? videoCategoriesUpdateDTO.getIsEnabled() : videoCategory.getIsEnabled());
        videoCategory.setUpdatedAt(LocalDateTime.now());
        
        return this.updateById(videoCategory);
    }
    
    /**
     * 删除视频分类（逻辑删除）
     * @param id 分类ID
     * @return 操作结果
     */
    @Override
    public boolean deleteVideoCategory(Long id) {
        VideoCategories videoCategory = this.getById(id);
        if (videoCategory == null || videoCategory.getIsEnabled() == 0) {
            return false;
        }
        
        videoCategory.setIsEnabled((byte) 0);
        videoCategory.setUpdatedAt(LocalDateTime.now());
        
        return this.updateById(videoCategory);
    }
}