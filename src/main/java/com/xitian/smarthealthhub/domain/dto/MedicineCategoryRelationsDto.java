package com.xitian.smarthealthhub.domain.dto;

import lombok.Data;

/**
 * 药品与分类的关联表DTO
 */
@Data
public class MedicineCategoryRelationsDto {
    /**
     * 主键，雪花ID
     */
    private Long id;

    /**
     * 药品ID
     */
    private Long medicineId;

    /**
     * 分类ID
     */
    private Long categoryId;
}