package com.xitian.smarthealthhub.converter;

import com.xitian.smarthealthhub.domain.dto.MedicineCategoryRelationsDto;
import com.xitian.smarthealthhub.domain.entity.MedicineCategoryRelationsEntity;
import com.xitian.smarthealthhub.domain.vo.MedicineCategoryRelationsVo;
import org.springframework.stereotype.Component;

/**
 * 药品与分类的关联表转换器
 */
@Component
public class MedicineCategoryRelationsConverter {

    /**
     * 将实体转换为DTO
     */
    public MedicineCategoryRelationsDto entityToDto(MedicineCategoryRelationsEntity entity) {
        if (entity == null) {
            return null;
        }
        MedicineCategoryRelationsDto dto = new MedicineCategoryRelationsDto();
        dto.setId(entity.getId());
        dto.setMedicineId(entity.getMedicineId());
        dto.setCategoryId(entity.getCategoryId());
        return dto;
    }

    /**
     * 将DTO转换为实体
     */
    public MedicineCategoryRelationsEntity dtoToEntity(MedicineCategoryRelationsDto dto) {
        if (dto == null) {
            return null;
        }
        MedicineCategoryRelationsEntity entity = new MedicineCategoryRelationsEntity();
        entity.setId(dto.getId());
        entity.setMedicineId(dto.getMedicineId());
        entity.setCategoryId(dto.getCategoryId());
        return entity;
    }

    /**
     * 将实体转换为VO
     */
    public MedicineCategoryRelationsVo entityToVo(MedicineCategoryRelationsEntity entity) {
        if (entity == null) {
            return null;
        }
        MedicineCategoryRelationsVo vo = new MedicineCategoryRelationsVo();
        vo.setId(entity.getId());
        vo.setMedicineId(entity.getMedicineId());
        vo.setCategoryId(entity.getCategoryId());
        vo.setCreatedAt(entity.getCreatedAt());
        return vo;
    }

    /**
     * 将实体和药品名称、分类名称转换为VO
     */
    public MedicineCategoryRelationsVo entityWithNamesToVo(MedicineCategoryRelationsEntity entity, String medicineName, String categoryName) {
        if (entity == null) {
            return null;
        }
        MedicineCategoryRelationsVo vo = new MedicineCategoryRelationsVo();
        vo.setId(entity.getId());
        vo.setMedicineId(entity.getMedicineId());
        vo.setCategoryId(entity.getCategoryId());
        vo.setCreatedAt(entity.getCreatedAt());
        vo.setMedicineName(medicineName);
        vo.setCategoryName(categoryName);
        return vo;
    }
}