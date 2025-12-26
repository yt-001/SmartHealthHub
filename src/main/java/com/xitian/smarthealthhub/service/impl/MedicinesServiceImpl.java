package com.xitian.smarthealthhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.converter.MedicinesConverter;
import com.xitian.smarthealthhub.domain.dto.MedicinesDto;
import com.xitian.smarthealthhub.domain.entity.MedicinesEntity;
import com.xitian.smarthealthhub.domain.query.MedicinesQuery;
import com.xitian.smarthealthhub.domain.vo.MedicinesVo;
import com.xitian.smarthealthhub.mapper.MedicinesMapper;
import com.xitian.smarthealthhub.service.MedicinesService;
import com.xitian.smarthealthhub.service.MedicineCategoriesService;
import com.xitian.smarthealthhub.service.MedicineCategoryRelationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 药品服务实现类
 * 
 * @author 
 * @date 2025/02/04
 */
@Service
public class MedicinesServiceImpl implements MedicinesService {
    private final MedicinesMapper medicinesMapper;

    private final MedicinesConverter medicinesConverter;
    
    @Autowired
    private MedicineCategoriesService medicineCategoriesService;
    
    @Autowired
    private MedicineCategoryRelationsService medicineCategoryRelationsService;

    public MedicinesServiceImpl(MedicinesMapper medicinesMapper,
                               MedicinesConverter medicinesConverter) {
        this.medicinesMapper = medicinesMapper;
        this.medicinesConverter = medicinesConverter;
    }

    /**
     * 分页查询
     * 
     * @param param 分页参数和查询条件
     * @return 分页结果
     */
    @Override
    public PageBean<MedicinesVo> pageQuery(PageParam<MedicinesQuery> param) {
        // 构建分页对象
        Page<MedicinesEntity> page = new Page<>(param.getPageNum(), param.getPageSize());
        
        // 构建查询条件，支持通过分类ID查询
        LambdaQueryWrapper<MedicinesEntity> queryWrapper = Wrappers.lambdaQuery();
        
        // 如果有查询条件，则添加查询条件
        if (param.getQuery() != null) {
            MedicinesQuery query = param.getQuery();
            
            if (StringUtils.hasText(query.getName())) {
                queryWrapper.like(MedicinesEntity::getName, query.getName());
            }

            if (query.getIsPrescription() != null) {
                queryWrapper.eq(MedicinesEntity::getIsPrescription, query.getIsPrescription());
            }

            if (query.getStatus() != null) {
                queryWrapper.eq(MedicinesEntity::getStatus, query.getStatus());
            }

            // 如果提供了分类ID，则通过关联表查询属于该分类的药品
            if (query.getCategoryId() != null) {
                // 通过medicine_category_relations表关联查询
                List<Long> medicineIds = medicineCategoryRelationsService.getMedicineIdsByCategoryId(query.getCategoryId());
                if (medicineIds != null && !medicineIds.isEmpty()) {
                    queryWrapper.in(MedicinesEntity::getId, medicineIds);
                } else {
                    // 如果没有找到关联的药品，返回空结果
                    queryWrapper.eq(MedicinesEntity::getId, -1); // 确保返回空结果
                }
            }
            
            // 如果提供了大类ID，则查询该大类下的子分类（小类）下的药品
            if (query.getParentCategoryId() != null) {
                // 先查询所有属于该大类的子分类ID
                List<Long> subCategoryIds = medicineCategoriesService.getSubCategoryIdsByParentId(query.getParentCategoryId());
                if (subCategoryIds != null && !subCategoryIds.isEmpty()) {
                    // 然后查询这些子分类关联的药品
                    List<Long> medicineIds = medicineCategoryRelationsService.getMedicineIdsByCategoryIds(subCategoryIds);
                    if (medicineIds != null && !medicineIds.isEmpty()) {
                        queryWrapper.in(MedicinesEntity::getId, medicineIds);
                    } else {
                        // 如果没有找到关联的药品，返回空结果
                        queryWrapper.eq(MedicinesEntity::getId, -1); // 确保返回空结果
                    }
                } else {
                    // 如果没有找到子分类，返回空结果
                    queryWrapper.eq(MedicinesEntity::getId, -1); // 确保返回空结果
                }
            }
        }

        // 按创建时间倒序排列
        queryWrapper.orderByDesc(MedicinesEntity::getCreatedAt);
        
        // 执行分页查询
        Page<MedicinesEntity> resultPage = medicinesMapper.selectPage(page, queryWrapper);

        // 转换为VO并封装分页结果，同时获取小类名称
        List<MedicinesVo> medicinesVoList = resultPage.getRecords().stream()
                .map(entity -> {
                    // 获取该药品关联的分类信息
                    String subCategoryName = null;
                    // 从关联表中获取分类ID
                    List<Long> categoryIds = medicineCategoryRelationsService.getCategoryIdsByMedicineId(entity.getId());
                    
                    if (categoryIds != null && !categoryIds.isEmpty()) {
                        // 获取第一个分类作为小类名称（可以根据实际需求调整）
                        Long categoryId = categoryIds.get(0);
                        if (categoryId != null) {
                            var category = medicineCategoriesService.getById(categoryId);
                            if (category != null) {
                                subCategoryName = category.getName();
                            }
                        }
                    }
                    
                    return medicinesConverter.entityToVoWithSubCategory(entity, subCategoryName);
                })
                .collect(Collectors.toList());

        return PageBean.of(medicinesVoList, resultPage.getTotal(), param);
    }

    /**
     * 根据ID查询
     * 
     * @param id ID
     * @return 实体
     */
    @Override
    public MedicinesVo getById(Long id) {
        MedicinesEntity entity = medicinesMapper.selectById(id);
        return medicinesConverter.entityToVo(entity);
    }

    /**
     * 新增
     * 
     * @param dto DTO
     * @return ID
     */
    @Override
    public Long add(MedicinesDto dto) {
        MedicinesEntity entity = medicinesConverter.dtoToEntity(dto);
        medicinesMapper.insert(entity);
        return entity.getId();
    }

    /**
     * 修改
     * 
     * @param dto DTO
     * @return 是否成功
     */
    @Override
    public Boolean update(MedicinesDto dto) {
        MedicinesEntity entity = medicinesConverter.dtoToEntity(dto);
        return medicinesMapper.updateById(entity) > 0;
    }

    /**
     * 删除
     * 
     * @param id ID
     * @return 是否成功
     */
    @Override
    public Boolean delete(Long id) {
        return medicinesMapper.deleteById(id) > 0;
    }
}