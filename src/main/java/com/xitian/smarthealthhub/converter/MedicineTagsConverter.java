package com.xitian.smarthealthhub.converter;

import com.xitian.smarthealthhub.domain.dto.MedicineTagsDto;
import com.xitian.smarthealthhub.domain.entity.MedicineTagsEntity;
import com.xitian.smarthealthhub.domain.vo.MedicineTagsVo;
import org.springframework.stereotype.Component;

/**
 * 药品标签转换器
 */
@Component
public class MedicineTagsConverter {
    /**
     * 将实体转换为DTO
     */
    public MedicineTagsDto entityToDto(MedicineTagsEntity entity) {
        if (entity == null) {
            return null;
        }
        MedicineTagsDto dto = new MedicineTagsDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setSortOrder(entity.getSortOrder());
        dto.setIsEnabled(entity.getIsEnabled());
        return dto;
    }

    /**
     * 将DTO转换为实体
     */
    public MedicineTagsEntity dtoToEntity(MedicineTagsDto dto) {
        if (dto == null) {
            return null;
        }
        MedicineTagsEntity entity = new MedicineTagsEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setSortOrder(dto.getSortOrder());
        entity.setIsEnabled(dto.getIsEnabled());
        return entity;
    }

    /**
     * 将实体转换为VO
     */
    public MedicineTagsVo entityToVo(MedicineTagsEntity entity) {
        if (entity == null) {
            return null;
        }
        MedicineTagsVo vo = new MedicineTagsVo();
        vo.setId(entity.getId());
        vo.setName(entity.getName());
        vo.setDescription(entity.getDescription());
        vo.setSortOrder(entity.getSortOrder());
        vo.setIsEnabled(entity.getIsEnabled());
        vo.setCreatedAt(entity.getCreatedAt());
        vo.setUpdatedAt(entity.getUpdatedAt());
        return vo;
    }
}

