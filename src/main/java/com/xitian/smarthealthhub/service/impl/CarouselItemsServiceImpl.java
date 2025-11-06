package com.xitian.smarthealthhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.converter.CarouselItemsConverter;
import com.xitian.smarthealthhub.domain.dto.CarouselItemsCreateDTO;
import com.xitian.smarthealthhub.domain.dto.CarouselItemsUpdateDTO;
import com.xitian.smarthealthhub.domain.entity.CarouselItems;
import com.xitian.smarthealthhub.domain.query.CarouselItemsQuery;
import com.xitian.smarthealthhub.domain.vo.CarouselItemsVO;
import com.xitian.smarthealthhub.mapper.CarouselItemsMapper;
import com.xitian.smarthealthhub.service.CarouselItemsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 轮播图服务实现类
 */
@Service
public class CarouselItemsServiceImpl extends ServiceImpl<CarouselItemsMapper, CarouselItems> implements CarouselItemsService {
    
    /**
     * 分页查询轮播图
     * @param param 分页参数和查询条件
     * @return 轮播图分页数据
     */
    @Override
    public PageBean<CarouselItemsVO> pageQuery(PageParam<CarouselItemsQuery> param) {
        // 创建分页对象
        Page<CarouselItems> page = new Page<>(param.getPageNum(), param.getPageSize());
        
        // 构建查询条件
        LambdaQueryWrapper<CarouselItems> queryWrapper = Wrappers.lambdaQuery();
        
        // 只查询未删除的轮播图
        queryWrapper.eq(CarouselItems::getIsDeleted, (byte) 0);
        
        // 如果有查询条件，则添加查询条件
        if (param.getQuery() != null) {
            CarouselItemsQuery query = param.getQuery();
            
            // 跳转类型查询
            if (query.getTargetType() != null) {
                queryWrapper.eq(CarouselItems::getTargetType, query.getTargetType());
            }
            
            // 状态查询
            if (query.getStatus() != null) {
                queryWrapper.eq(CarouselItems::getStatus, query.getStatus());
            }
        }
        
        // 按排序字段升序排列，然后按创建时间倒序排列
        queryWrapper.orderByAsc(CarouselItems::getSortOrder);
        queryWrapper.orderByDesc(CarouselItems::getCreatedAt);
        
        // 执行分页查询
        Page<CarouselItems> resultPage = this.page(page, queryWrapper);
        
        // 转换为VO对象
        List<CarouselItemsVO> voList = CarouselItemsConverter.toVOList(resultPage.getRecords());
        
        // 构造并返回PageBean
        return PageBean.of(voList, resultPage.getTotal(), param);
    }
    
    /**
     * 查询显示的轮播图列表（用户端使用）
     * @return 轮播图列表
     */
    @Override
    public List<CarouselItemsVO> listDisplayCarouselItems() {
        // 构建查询条件
        LambdaQueryWrapper<CarouselItems> queryWrapper = Wrappers.lambdaQuery();
        
        // 只查询未删除且状态为显示的轮播图
        queryWrapper.eq(CarouselItems::getIsDeleted, (byte) 0);
        queryWrapper.eq(CarouselItems::getStatus, (byte) 1);
        
        // 按排序字段升序排列，然后按创建时间倒序排列
        queryWrapper.orderByAsc(CarouselItems::getSortOrder);
        queryWrapper.orderByDesc(CarouselItems::getCreatedAt);
        
        // 查询最多10条记录
        Page<CarouselItems> page = new Page<>(1, 10);
        Page<CarouselItems> resultPage = this.page(page, queryWrapper);
        
        // 转换为VO对象并返回
        return CarouselItemsConverter.toVOList(resultPage.getRecords());
    }
    
    /**
     * 根据ID获取轮播图详情
     * @param id 轮播图ID
     * @return 轮播图详情
     */
    @Override
    public CarouselItemsVO getCarouselItemById(Long id) {
        CarouselItems carouselItem = this.getById(id);
        if (carouselItem == null || carouselItem.getIsDeleted() == 1) {
            return null;
        }
        
        return CarouselItemsConverter.toVO(carouselItem);
    }
    
    /**
     * 创建轮播图
     * @param carouselItemsCreateDTO 轮播图创建信息
     * @return 操作结果
     */
    @Override
    public boolean createCarouselItem(CarouselItemsCreateDTO carouselItemsCreateDTO) {
        CarouselItems carouselItem = CarouselItems.builder()
                .title(carouselItemsCreateDTO.getTitle())
                .description(carouselItemsCreateDTO.getDescription())
                .imageUrl(carouselItemsCreateDTO.getImageUrl())
                .targetType(carouselItemsCreateDTO.getTargetType())
                .targetId(carouselItemsCreateDTO.getTargetId())
                .targetUrl(carouselItemsCreateDTO.getTargetUrl())
                .sortOrder(carouselItemsCreateDTO.getSortOrder() != null ? carouselItemsCreateDTO.getSortOrder() : 0)
                .status(carouselItemsCreateDTO.getStatus() != null ? carouselItemsCreateDTO.getStatus() : (byte) 1)
                .isDeleted((byte) 0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        return this.save(carouselItem);
    }
    
    /**
     * 更新轮播图
     * @param carouselItemsUpdateDTO 轮播图更新信息
     * @return 操作结果
     */
    @Override
    public boolean updateCarouselItem(CarouselItemsUpdateDTO carouselItemsUpdateDTO) {
        CarouselItems carouselItem = this.getById(carouselItemsUpdateDTO.getId());
        if (carouselItem == null || carouselItem.getIsDeleted() == 1) {
            return false;
        }
        
        carouselItem = CarouselItems.builder()
                .id(carouselItemsUpdateDTO.getId())
                .title(carouselItemsUpdateDTO.getTitle())
                .description(carouselItemsUpdateDTO.getDescription())
                .imageUrl(carouselItemsUpdateDTO.getImageUrl())
                .targetType(carouselItemsUpdateDTO.getTargetType())
                .targetId(carouselItemsUpdateDTO.getTargetId())
                .targetUrl(carouselItemsUpdateDTO.getTargetUrl())
                .sortOrder(carouselItemsUpdateDTO.getSortOrder() != null ? carouselItemsUpdateDTO.getSortOrder() : carouselItem.getSortOrder())
                .status(carouselItemsUpdateDTO.getStatus() != null ? carouselItemsUpdateDTO.getStatus() : carouselItem.getStatus())
                .isDeleted(carouselItem.getIsDeleted())
                .createdAt(carouselItem.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
        
        return this.updateById(carouselItem);
    }
    
    /**
     * 删除轮播图（逻辑删除）
     * @param id 轮播图ID
     * @return 操作结果
     */
    @Override
    public boolean deleteCarouselItem(Long id) {
        CarouselItems carouselItem = this.getById(id);
        if (carouselItem == null || carouselItem.getIsDeleted() == 1) {
            return false;
        }
        
        carouselItem = CarouselItems.builder()
                .id(carouselItem.getId())
                .title(carouselItem.getTitle())
                .description(carouselItem.getDescription())
                .imageUrl(carouselItem.getImageUrl())
                .targetType(carouselItem.getTargetType())
                .targetId(carouselItem.getTargetId())
                .targetUrl(carouselItem.getTargetUrl())
                .sortOrder(carouselItem.getSortOrder())
                .status(carouselItem.getStatus())
                .isDeleted((byte) 1)
                .createdAt(carouselItem.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
        
        return this.updateById(carouselItem);
    }
}