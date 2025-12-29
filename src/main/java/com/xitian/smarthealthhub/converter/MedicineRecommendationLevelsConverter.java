package com.xitian.smarthealthhub.converter;

import com.xitian.smarthealthhub.domain.dto.MedicineRecommendationLevelsDto;
import com.xitian.smarthealthhub.domain.entity.MedicineRecommendationLevelsEntity;
import com.xitian.smarthealthhub.domain.vo.MedicineRecommendationLevelsVo;
import org.springframework.stereotype.Component;

/**
 * 药品推荐级别转换器
 */
@Component
public class MedicineRecommendationLevelsConverter {
    /**
     * 将实体转换为DTO
     */
    public MedicineRecommendationLevelsDto entityToDto(MedicineRecommendationLevelsEntity entity) {
        if (entity == null) {
            return null;
        }
        MedicineRecommendationLevelsDto dto = new MedicineRecommendationLevelsDto();
        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setSortOrder(entity.getSortOrder());
        dto.setIsEnabled(entity.getIsEnabled());
        return dto;
    }

    /**
     * 将DTO转换为实体
     */
    public MedicineRecommendationLevelsEntity dtoToEntity(MedicineRecommendationLevelsDto dto) {
        if (dto == null) {
            return null;
        }
        MedicineRecommendationLevelsEntity entity = new MedicineRecommendationLevelsEntity();
        entity.setId(dto.getId());
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setSortOrder(dto.getSortOrder());
        entity.setIsEnabled(dto.getIsEnabled());
        return entity;
    }

    /**
     * 将实体转换为VO
     */
    public MedicineRecommendationLevelsVo entityToVo(MedicineRecommendationLevelsEntity entity) {
        if (entity == null) {
            return null;
        }
        MedicineRecommendationLevelsVo vo = new MedicineRecommendationLevelsVo();
        vo.setId(entity.getId());
        vo.setCode(entity.getCode());
        vo.setName(entity.getName());
        vo.setDescription(entity.getDescription());
        vo.setSortOrder(entity.getSortOrder());
        vo.setIsEnabled(entity.getIsEnabled());
        vo.setCreatedAt(entity.getCreatedAt());
        vo.setUpdatedAt(entity.getUpdatedAt());
        return vo;
    }
}

