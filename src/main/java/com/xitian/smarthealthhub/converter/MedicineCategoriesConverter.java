package com.xitian.smarthealthhub.converter;

import com.xitian.smarthealthhub.domain.dto.MedicineCategoriesDto;
import com.xitian.smarthealthhub.domain.entity.MedicineCategoriesEntity;
import com.xitian.smarthealthhub.domain.vo.MedicineCategoriesVo;
import org.springframework.stereotype.Component;

/**
 * 药品分类转换器
 */
@Component
public class MedicineCategoriesConverter {

    /**
     * 将实体转换为DTO
     */
    public MedicineCategoriesDto entityToDto(MedicineCategoriesEntity entity) {
        if (entity == null) {
            return null;
        }
        MedicineCategoriesDto dto = new MedicineCategoriesDto();
        dto.setId(entity.getId());
        dto.setParentId(entity.getParentId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setSortOrder(entity.getSortOrder());
        dto.setIsEnabled(entity.getIsEnabled());
        return dto;
    }

    /**
     * 将DTO转换为实体
     */
    public MedicineCategoriesEntity dtoToEntity(MedicineCategoriesDto dto) {
        if (dto == null) {
            return null;
        }
        MedicineCategoriesEntity entity = new MedicineCategoriesEntity();
        entity.setId(dto.getId());
        entity.setParentId(dto.getParentId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setSortOrder(dto.getSortOrder());
        entity.setIsEnabled(dto.getIsEnabled());
        return entity;
    }

    /**
     * 将实体转换为VO
     */
    public MedicineCategoriesVo entityToVo(MedicineCategoriesEntity entity) {
        if (entity == null) {
            return null;
        }
        MedicineCategoriesVo vo = new MedicineCategoriesVo();
        vo.setId(entity.getId());
        vo.setParentId(entity.getParentId());
        vo.setName(entity.getName());
        vo.setDescription(entity.getDescription());
        vo.setSortOrder(entity.getSortOrder());
        vo.setIsEnabled(entity.getIsEnabled());
        vo.setCreatedAt(entity.getCreatedAt());
        vo.setUpdatedAt(entity.getUpdatedAt());
        return vo;
    }
}