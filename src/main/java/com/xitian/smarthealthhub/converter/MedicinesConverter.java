package com.xitian.smarthealthhub.converter;

import com.xitian.smarthealthhub.domain.dto.MedicinesDto;
import com.xitian.smarthealthhub.domain.entity.MedicinesEntity;
import com.xitian.smarthealthhub.domain.vo.MedicinesVo;
import org.springframework.stereotype.Component;

/**
 * 药品信息主表转换器
 */
@Component
public class MedicinesConverter {

    /**
     * 将实体转换为DTO
     */
    public MedicinesDto entityToDto(MedicinesEntity entity) {
        if (entity == null) {
            return null;
        }
        MedicinesDto dto = new MedicinesDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCommonName(entity.getCommonName());
        dto.setBrandName(entity.getBrandName());
        dto.setDescription(entity.getDescription());
        dto.setCoverImageUrl(entity.getCoverImageUrl());
        dto.setImages(entity.getImages());
        dto.setSpecs(entity.getSpecs());
        dto.setIsPrescription(entity.getIsPrescription());
        dto.setPrice(entity.getPrice());
        dto.setOriginalPrice(entity.getOriginalPrice());
        dto.setSales(entity.getSales());
        dto.setRating(entity.getRating());
        dto.setIndications(entity.getIndications());
        dto.setFunctions(entity.getFunctions());
        dto.setDosage(entity.getDosage());
        dto.setAdverseReactions(entity.getAdverseReactions());
        dto.setContraindications(entity.getContraindications());
        dto.setPrecautions(entity.getPrecautions());
        dto.setStatus(entity.getStatus());
        dto.setIsDeleted(entity.getIsDeleted());
        return dto;
    }

    /**
     * 将DTO转换为实体
     */
    public MedicinesEntity dtoToEntity(MedicinesDto dto) {
        if (dto == null) {
            return null;
        }
        MedicinesEntity entity = new MedicinesEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setCommonName(dto.getCommonName());
        entity.setBrandName(dto.getBrandName());
        entity.setDescription(dto.getDescription());
        entity.setCoverImageUrl(dto.getCoverImageUrl());
        entity.setImages(dto.getImages());
        entity.setSpecs(dto.getSpecs());
        entity.setIsPrescription(dto.getIsPrescription());
        entity.setPrice(dto.getPrice());
        entity.setOriginalPrice(dto.getOriginalPrice());
        entity.setSales(dto.getSales());
        entity.setRating(dto.getRating());
        entity.setIndications(dto.getIndications());
        entity.setFunctions(dto.getFunctions());
        entity.setDosage(dto.getDosage());
        entity.setAdverseReactions(dto.getAdverseReactions());
        entity.setContraindications(dto.getContraindications());
        entity.setPrecautions(dto.getPrecautions());
        entity.setStatus(dto.getStatus());
        entity.setIsDeleted(dto.getIsDeleted());
        return entity;
    }

    /**
     * 将实体转换为VO
     */
    public MedicinesVo entityToVo(MedicinesEntity entity) {
        if (entity == null) {
            return null;
        }
        MedicinesVo vo = new MedicinesVo();
        vo.setId(entity.getId());
        vo.setName(entity.getName());
        vo.setCommonName(entity.getCommonName());
        vo.setBrandName(entity.getBrandName());
        vo.setDescription(entity.getDescription());
        vo.setCoverImageUrl(entity.getCoverImageUrl());
        vo.setImages(entity.getImages());
        vo.setSpecs(entity.getSpecs());
        vo.setIsPrescription(entity.getIsPrescription());
        vo.setPrice(entity.getPrice());
        vo.setOriginalPrice(entity.getOriginalPrice());
        vo.setSales(entity.getSales());
        vo.setRating(entity.getRating());
        vo.setIndications(entity.getIndications());
        vo.setFunctions(entity.getFunctions());
        vo.setDosage(entity.getDosage());
        vo.setAdverseReactions(entity.getAdverseReactions());
        vo.setContraindications(entity.getContraindications());
        vo.setPrecautions(entity.getPrecautions());
        vo.setStatus(entity.getStatus());
        vo.setIsDeleted(entity.getIsDeleted());
        vo.setCreatedAt(entity.getCreatedAt());
        vo.setUpdatedAt(entity.getUpdatedAt());
        return vo;
    }
    
    /**
     * 将实体和小类名称转换为VO
     */
    public MedicinesVo entityToVoWithSubCategory(MedicinesEntity entity, String subCategoryName) {
        if (entity == null) {
            return null;
        }
        MedicinesVo vo = new MedicinesVo();
        vo.setId(entity.getId());
        vo.setName(entity.getName());
        vo.setCommonName(entity.getCommonName());
        vo.setBrandName(entity.getBrandName());
        vo.setDescription(entity.getDescription());
        vo.setCoverImageUrl(entity.getCoverImageUrl());
        vo.setImages(entity.getImages());
        vo.setSpecs(entity.getSpecs());
        vo.setIsPrescription(entity.getIsPrescription());
        vo.setPrice(entity.getPrice());
        vo.setOriginalPrice(entity.getOriginalPrice());
        vo.setSales(entity.getSales());
        vo.setRating(entity.getRating());
        vo.setIndications(entity.getIndications());
        vo.setFunctions(entity.getFunctions());
        vo.setDosage(entity.getDosage());
        vo.setAdverseReactions(entity.getAdverseReactions());
        vo.setContraindications(entity.getContraindications());
        vo.setPrecautions(entity.getPrecautions());
        vo.setStatus(entity.getStatus());
        vo.setIsDeleted(entity.getIsDeleted());
        vo.setCreatedAt(entity.getCreatedAt());
        vo.setUpdatedAt(entity.getUpdatedAt());
        vo.setSubCategoryName(subCategoryName);
        return vo;
    }
}
