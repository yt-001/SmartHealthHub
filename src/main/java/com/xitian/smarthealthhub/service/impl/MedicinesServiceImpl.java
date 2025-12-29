package com.xitian.smarthealthhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.converter.MedicinesConverter;
import com.xitian.smarthealthhub.domain.dto.MedicinesDto;
import com.xitian.smarthealthhub.domain.entity.MedicinesEntity;
import com.xitian.smarthealthhub.domain.entity.MedicineRecommendationLevelsEntity;
import com.xitian.smarthealthhub.domain.entity.MedicineRecommendationRelationsEntity;
import com.xitian.smarthealthhub.domain.entity.MedicineTagRelationsEntity;
import com.xitian.smarthealthhub.domain.entity.MedicineTagsEntity;
import com.xitian.smarthealthhub.domain.query.MedicinesQuery;
import com.xitian.smarthealthhub.domain.vo.MedicinesVo;
import com.xitian.smarthealthhub.mapper.MedicinesMapper;
import com.xitian.smarthealthhub.mapper.MedicineRecommendationLevelsMapper;
import com.xitian.smarthealthhub.mapper.MedicineRecommendationRelationsMapper;
import com.xitian.smarthealthhub.mapper.MedicineTagRelationsMapper;
import com.xitian.smarthealthhub.mapper.MedicineTagsMapper;
import com.xitian.smarthealthhub.service.MedicinesService;
import com.xitian.smarthealthhub.service.MedicineCategoriesService;
import com.xitian.smarthealthhub.service.MedicineCategoryRelationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final MedicinesMapper medicinesMapper;

    private final MedicinesConverter medicinesConverter;
    
    @Autowired
    private MedicineCategoriesService medicineCategoriesService;
    
    @Autowired
    private MedicineCategoryRelationsService medicineCategoryRelationsService;

    @Autowired
    private MedicineRecommendationLevelsMapper medicineRecommendationLevelsMapper;

    @Autowired
    private MedicineTagsMapper medicineTagsMapper;

    @Autowired
    private MedicineTagRelationsMapper medicineTagRelationsMapper;

    @Autowired
    private MedicineRecommendationRelationsMapper medicineRecommendationRelationsMapper;

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
        Page<MedicinesEntity> page = new Page<>(param.getPageNum(), param.getPageSize());
        
        LambdaQueryWrapper<MedicinesEntity> queryWrapper = Wrappers.lambdaQuery();
        
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

            if (query.getCategoryId() != null) {
                List<Long> medicineIds = medicineCategoryRelationsService.getMedicineIdsByCategoryId(query.getCategoryId());
                if (medicineIds != null && !medicineIds.isEmpty()) {
                    queryWrapper.in(MedicinesEntity::getId, medicineIds);
                } else {
                    queryWrapper.eq(MedicinesEntity::getId, -1);
                }
            }
            
            if (query.getParentCategoryId() != null) {
                List<Long> subCategoryIds = medicineCategoriesService.getSubCategoryIdsByParentId(query.getParentCategoryId());
                if (subCategoryIds != null && !subCategoryIds.isEmpty()) {
                    List<Long> medicineIds = medicineCategoryRelationsService.getMedicineIdsByCategoryIds(subCategoryIds);
                    if (medicineIds != null && !medicineIds.isEmpty()) {
                        queryWrapper.in(MedicinesEntity::getId, medicineIds);
                    } else {
                        queryWrapper.eq(MedicinesEntity::getId, -1);
                    }
                } else {
                    queryWrapper.eq(MedicinesEntity::getId, -1);
                }
            }
        }

        queryWrapper.orderByDesc(MedicinesEntity::getCreatedAt);
        
        Page<MedicinesEntity> resultPage = medicinesMapper.selectPage(page, queryWrapper);

        List<MedicinesVo> medicinesVoList = resultPage.getRecords().stream()
                .map(entity -> {
                    String subCategoryName = null;
                    List<Long> categoryIds = medicineCategoryRelationsService.getCategoryIdsByMedicineId(entity.getId());
                    
                    if (categoryIds != null && !categoryIds.isEmpty()) {
                        Long categoryId = categoryIds.get(0);
                        if (categoryId != null) {
                            var category = medicineCategoriesService.getById(categoryId);
                            if (category != null) {
                                subCategoryName = category.getName();
                            }
                        }
                    }
                    
                    MedicinesVo vo = medicinesConverter.entityToVoWithSubCategory(entity, subCategoryName);
                    vo.setRecommendationLevel(resolveRecommendationLevelNameForMedicine(entity.getId()));
                    vo.setTags(buildTagsJsonForMedicine(entity.getId()));
                    return vo;
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
        MedicinesVo vo = medicinesConverter.entityToVo(entity);
        if (vo != null) {
            vo.setRecommendationLevel(resolveRecommendationLevelNameForMedicine(id));
            vo.setTags(buildTagsJsonForMedicine(id));
        }
        return vo;
    }

    /**
     * 获取默认推荐级别名称
     */
    private String resolveDefaultRecommendationLevelName() {
        LambdaQueryWrapper<MedicineRecommendationLevelsEntity> wrapper = Wrappers.lambdaQuery(MedicineRecommendationLevelsEntity.class)
                .eq(MedicineRecommendationLevelsEntity::getIsEnabled, 1)
                .orderByAsc(MedicineRecommendationLevelsEntity::getSortOrder)
                .last("LIMIT 1");
        List<MedicineRecommendationLevelsEntity> list = medicineRecommendationLevelsMapper.selectList(wrapper);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0).getName();
    }

    /**
     * 获取指定药品的推荐级别名称
     */
    private String resolveRecommendationLevelNameForMedicine(Long medicineId) {
        if (medicineId == null) {
            return resolveDefaultRecommendationLevelName();
        }
        LambdaQueryWrapper<MedicineRecommendationRelationsEntity> wrapper =
                Wrappers.lambdaQuery(MedicineRecommendationRelationsEntity.class)
                        .eq(MedicineRecommendationRelationsEntity::getMedicineId, medicineId)
                        .last("LIMIT 1");
        MedicineRecommendationRelationsEntity relation = medicineRecommendationRelationsMapper.selectOne(wrapper);
        if (relation == null || relation.getRecommendationLevelId() == null) {
            return resolveDefaultRecommendationLevelName();
        }
        MedicineRecommendationLevelsEntity level = medicineRecommendationLevelsMapper.selectById(relation.getRecommendationLevelId());
        if (level == null) {
            return resolveDefaultRecommendationLevelName();
        }
        if (level.getIsEnabled() != null && level.getIsEnabled() == 0) {
            return resolveDefaultRecommendationLevelName();
        }
        return level.getName();
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
        Long id = entity.getId();
        saveOrUpdateRecommendationLevelRelation(id, dto.getRecommendationLevel());
        saveOrUpdateTags(id, dto.getTags());
        return id;
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
        boolean success = medicinesMapper.updateById(entity) > 0;
        if (success) {
            saveOrUpdateRecommendationLevelRelation(entity.getId(), dto.getRecommendationLevel());
            saveOrUpdateTags(entity.getId(), dto.getTags());
        }
        return success;
    }

    /**
     * 删除
     * 
     * @param id ID
     * @return 是否成功
     */
    @Override
    public Boolean delete(Long id) {
        if (id == null) {
            return false;
        }
        LambdaQueryWrapper<MedicineTagRelationsEntity> tagDeleteWrapper =
                Wrappers.lambdaQuery(MedicineTagRelationsEntity.class)
                        .eq(MedicineTagRelationsEntity::getMedicineId, id);
        medicineTagRelationsMapper.delete(tagDeleteWrapper);
        LambdaQueryWrapper<MedicineRecommendationRelationsEntity> recDeleteWrapper =
                Wrappers.lambdaQuery(MedicineRecommendationRelationsEntity.class)
                        .eq(MedicineRecommendationRelationsEntity::getMedicineId, id);
        medicineRecommendationRelationsMapper.delete(recDeleteWrapper);
        return medicinesMapper.deleteById(id) > 0;
    }

    /**
     * 保存或更新药品与推荐级别的关联
     */
    private void saveOrUpdateRecommendationLevelRelation(Long medicineId, String recommendationLevelName) {
        if (medicineId == null) {
            return;
        }
        if (!StringUtils.hasText(recommendationLevelName)) {
            return;
        }
        LambdaQueryWrapper<MedicineRecommendationLevelsEntity> levelWrapper =
                Wrappers.lambdaQuery(MedicineRecommendationLevelsEntity.class)
                        .eq(MedicineRecommendationLevelsEntity::getName, recommendationLevelName)
                        .last("LIMIT 1");
        MedicineRecommendationLevelsEntity level = medicineRecommendationLevelsMapper.selectOne(levelWrapper);
        if (level == null) {
            level = new MedicineRecommendationLevelsEntity();
            level.setCode(recommendationLevelName);
            level.setName(recommendationLevelName);
            level.setDescription(null);
            level.setSortOrder(0);
            level.setIsEnabled(1);
            medicineRecommendationLevelsMapper.insert(level);
        }
        LambdaQueryWrapper<MedicineRecommendationRelationsEntity> relationWrapper =
                Wrappers.lambdaQuery(MedicineRecommendationRelationsEntity.class)
                        .eq(MedicineRecommendationRelationsEntity::getMedicineId, medicineId)
                        .last("LIMIT 1");
        MedicineRecommendationRelationsEntity relation = medicineRecommendationRelationsMapper.selectOne(relationWrapper);
        if (relation == null) {
            relation = new MedicineRecommendationRelationsEntity();
            relation.setMedicineId(medicineId);
            relation.setRecommendationLevelId(level.getId());
            relation.setCreatedAt(LocalDateTime.now());
            medicineRecommendationRelationsMapper.insert(relation);
        } else {
            relation.setRecommendationLevelId(level.getId());
            medicineRecommendationRelationsMapper.updateById(relation);
        }
    }

    /**
     * 构建指定药品的标签JSON字符串
     */
    private String buildTagsJsonForMedicine(Long medicineId) {
        if (medicineId == null) {
            return "[]";
        }
        LambdaQueryWrapper<MedicineTagRelationsEntity> relationWrapper =
                Wrappers.lambdaQuery(MedicineTagRelationsEntity.class)
                        .eq(MedicineTagRelationsEntity::getMedicineId, medicineId);
        List<MedicineTagRelationsEntity> relations = medicineTagRelationsMapper.selectList(relationWrapper);
        if (relations == null || relations.isEmpty()) {
            return "[]";
        }
        List<Long> tagIds = relations.stream()
                .map(MedicineTagRelationsEntity::getTagId)
                .collect(Collectors.toList());
        List<MedicineTagsEntity> tags = medicineTagsMapper.selectBatchIds(tagIds);
        if (tags == null || tags.isEmpty()) {
            return "[]";
        }
        List<String> names = tags.stream()
                .map(MedicineTagsEntity::getName)
                .collect(Collectors.toList());
        try {
            return OBJECT_MAPPER.writeValueAsString(names);
        } catch (JsonProcessingException e) {
            return "[]";
        }
    }

    /**
     * 保存或更新药品与标签的关联
     */
    private void saveOrUpdateTags(Long medicineId, String tagsJson) {
        if (medicineId == null) {
            return;
        }
        List<String> tagNames = parseTagsFromJson(tagsJson);
        LambdaQueryWrapper<MedicineTagRelationsEntity> deleteWrapper =
                Wrappers.lambdaQuery(MedicineTagRelationsEntity.class)
                        .eq(MedicineTagRelationsEntity::getMedicineId, medicineId);
        medicineTagRelationsMapper.delete(deleteWrapper);
        if (tagNames.isEmpty()) {
            return;
        }
        for (String name : tagNames) {
            if (!StringUtils.hasText(name)) {
                continue;
            }
            LambdaQueryWrapper<MedicineTagsEntity> tagWrapper =
                    Wrappers.lambdaQuery(MedicineTagsEntity.class)
                            .eq(MedicineTagsEntity::getName, name)
                            .last("LIMIT 1");
            MedicineTagsEntity tag = medicineTagsMapper.selectOne(tagWrapper);
            if (tag == null) {
                tag = new MedicineTagsEntity();
                tag.setName(name);
                tag.setDescription(null);
                tag.setSortOrder(0);
                tag.setIsEnabled(1);
                medicineTagsMapper.insert(tag);
            }
            MedicineTagRelationsEntity relation = new MedicineTagRelationsEntity();
            relation.setMedicineId(medicineId);
            relation.setTagId(tag.getId());
            relation.setCreatedAt(LocalDateTime.now());
            medicineTagRelationsMapper.insert(relation);
        }
    }

    /**
     * 解析标签JSON字符串为名称列表
     */
    private List<String> parseTagsFromJson(String tagsJson) {
        if (!StringUtils.hasText(tagsJson)) {
            return new ArrayList<>();
        }
        try {
            return OBJECT_MAPPER.readValue(tagsJson, new TypeReference<List<String>>() {
            });
        } catch (JsonProcessingException e) {
            return new ArrayList<>();
        }
    }
}
