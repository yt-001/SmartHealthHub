package com.xitian.smarthealthhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.converter.MedicineCategoryRelationsConverter;
import com.xitian.smarthealthhub.domain.dto.MedicineCategoryRelationsDto;
import com.xitian.smarthealthhub.domain.entity.MedicineCategoryRelationsEntity;
import com.xitian.smarthealthhub.domain.query.MedicineCategoryRelationsQuery;
import com.xitian.smarthealthhub.domain.vo.MedicineCategoryRelationsVo;
import com.xitian.smarthealthhub.mapper.MedicinesMapper;
import com.xitian.smarthealthhub.mapper.MedicineCategoryRelationsMapper;
import com.xitian.smarthealthhub.service.MedicineCategoryRelationsService;
import com.xitian.smarthealthhub.service.MedicinesService;
import com.xitian.smarthealthhub.service.MedicineCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 药品分类关联服务实现类
 * 
 * @author 
 * @date 2025/02/04
 */
@Service
public class MedicineCategoryRelationsServiceImpl implements MedicineCategoryRelationsService {
    private final MedicineCategoryRelationsMapper medicineCategoryRelationsMapper;

    private final MedicineCategoryRelationsConverter medicineCategoryRelationsConverter;
    
    @Autowired
    private MedicineCategoriesService medicineCategoriesService;
    
    @Autowired
    private MedicinesMapper medicinesMapper;

    public MedicineCategoryRelationsServiceImpl(MedicineCategoryRelationsMapper medicineCategoryRelationsMapper,
                                               MedicineCategoryRelationsConverter medicineCategoryRelationsConverter) {
        this.medicineCategoryRelationsMapper = medicineCategoryRelationsMapper;
        this.medicineCategoryRelationsConverter = medicineCategoryRelationsConverter;
    }

    /**
     * 分页查询
     * 
     * @param param 分页参数和查询条件
     * @return 分页结果
     */
    @Override
    public PageBean<MedicineCategoryRelationsVo> pageQuery(PageParam<MedicineCategoryRelationsQuery> param) {
        // 构建查询条件
        LambdaQueryWrapper<MedicineCategoryRelationsEntity> queryWrapper = Wrappers.lambdaQuery();
        
        // 如果有查询条件，则添加查询条件
        if (param.getQuery() != null) {
            MedicineCategoryRelationsQuery query = param.getQuery();
            
            if (query.getMedicineId() != null) {
                queryWrapper.eq(MedicineCategoryRelationsEntity::getMedicineId, query.getMedicineId());
            }

            if (query.getCategoryId() != null) {
                queryWrapper.eq(MedicineCategoryRelationsEntity::getCategoryId, query.getCategoryId());
            }

            if (query.getParentCategoryId() != null) {
                List<Long> subCategoryIds = medicineCategoriesService.getSubCategoryIdsByParentId(query.getParentCategoryId());
                if (subCategoryIds != null && !subCategoryIds.isEmpty()) {
                    queryWrapper.in(MedicineCategoryRelationsEntity::getCategoryId, subCategoryIds);
                } else {
                    queryWrapper.eq(MedicineCategoryRelationsEntity::getId, -1L);
                }
            }
        }

        // 执行分页查询
        Page<MedicineCategoryRelationsEntity> page = new Page<>(param.getPageNum(), param.getPageSize());
        Page<MedicineCategoryRelationsEntity> resultPage = medicineCategoryRelationsMapper.selectPage(page, queryWrapper);

        // 转换为VO并封装分页结果，同时获取药品名称和分类名称
        return PageBean.of(resultPage.getRecords().stream()
                .map(entity -> {
                    String medicineName = null;
                    String categoryName = null;
                    
                    // 获取药品名称 - 直接通过Mapper查询，避免循环依赖
                    if (entity.getMedicineId() != null) {
                        var medicine = medicinesMapper.selectById(entity.getMedicineId());
                        if (medicine != null) {
                            medicineName = medicine.getName();
                        }
                    }
                    
                    // 获取分类名称
                    if (entity.getCategoryId() != null) {
                        var category = medicineCategoriesService.getById(entity.getCategoryId());
                        if (category != null) {
                            categoryName = category.getName();
                        }
                    }
                    
                    return medicineCategoryRelationsConverter.entityWithNamesToVo(entity, medicineName, categoryName);
                })
                .toList(), resultPage.getTotal(), param);
    }

    /**
     * 根据ID查询
     * 
     * @param id ID
     * @return 实体
     */
    @Override
    public MedicineCategoryRelationsVo get(Long id) {
        MedicineCategoryRelationsEntity entity = medicineCategoryRelationsMapper.selectById(id);
        if (entity == null) {
            return null;
        }
        
        String medicineName = null;
        String categoryName = null;
        
        // 获取药品名称 - 直接通过Mapper查询，避免循环依赖
        if (entity.getMedicineId() != null) {
            var medicine = medicinesMapper.selectById(entity.getMedicineId());
            if (medicine != null) {
                medicineName = medicine.getName();
            }
        }
        
        // 获取分类名称
        if (entity.getCategoryId() != null) {
            var category = medicineCategoriesService.getById(entity.getCategoryId());
            if (category != null) {
                categoryName = category.getName();
            }
        }
        
        return medicineCategoryRelationsConverter.entityWithNamesToVo(entity, medicineName, categoryName);
    }

    /**
     * 新增
     * 
     * @param dto DTO
     * @return ID
     */
    @Override
    public Long add(MedicineCategoryRelationsDto dto) {
        MedicineCategoryRelationsEntity entity = medicineCategoryRelationsConverter.dtoToEntity(dto);
        entity.setCreatedAt(LocalDateTime.now()); // 设置创建时间
        medicineCategoryRelationsMapper.insert(entity);
        return entity.getId();
    }

    /**
     * 修改
     * 
     * @param dto DTO
     * @return 是否成功
     */
    @Override
    public Boolean update(MedicineCategoryRelationsDto dto) {
        MedicineCategoryRelationsEntity entity = medicineCategoryRelationsConverter.dtoToEntity(dto);
        // 不更新更新时间，因为这个表没有updated_at字段
        return medicineCategoryRelationsMapper.updateById(entity) > 0;
    }

    /**
     * 删除
     * 
     * @param id ID
     * @return 是否成功
     */
    @Override
    public Boolean delete(Long id) {
        return medicineCategoryRelationsMapper.deleteById(id) > 0;
    }

    /**
     * 根据分类ID获取药品ID列表
     * 
     * @param categoryId 分类ID
     * @return 药品ID列表
     */
    @Override
    public List<Long> getMedicineIdsByCategoryId(Long categoryId) {
        LambdaQueryWrapper<MedicineCategoryRelationsEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(MedicineCategoryRelationsEntity::getCategoryId, categoryId);
        
        List<MedicineCategoryRelationsEntity> entities = medicineCategoryRelationsMapper.selectList(queryWrapper);
        return entities.stream()
                .map(MedicineCategoryRelationsEntity::getMedicineId)
                .collect(Collectors.toList());
    }

    /**
     * 根据分类ID列表获取药品ID列表
     * 
     * @param categoryIds 分类ID列表
     * @return 药品ID列表
     */
    @Override
    public List<Long> getMedicineIdsByCategoryIds(List<Long> categoryIds) {
        LambdaQueryWrapper<MedicineCategoryRelationsEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(MedicineCategoryRelationsEntity::getCategoryId, categoryIds);
        
        List<MedicineCategoryRelationsEntity> entities = medicineCategoryRelationsMapper.selectList(queryWrapper);
        return entities.stream()
                .map(MedicineCategoryRelationsEntity::getMedicineId)
                .collect(Collectors.toList());
    }

    /**
     * 根据药品ID获取分类ID列表
     * 
     * @param medicineId 药品ID
     * @return 分类ID列表
     */
    @Override
    public List<Long> getCategoryIdsByMedicineId(Long medicineId) {
        LambdaQueryWrapper<MedicineCategoryRelationsEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(MedicineCategoryRelationsEntity::getMedicineId, medicineId);
        
        List<MedicineCategoryRelationsEntity> entities = medicineCategoryRelationsMapper.selectList(queryWrapper);
        return entities.stream()
                .map(MedicineCategoryRelationsEntity::getCategoryId)
                .collect(Collectors.toList());
    }
}
