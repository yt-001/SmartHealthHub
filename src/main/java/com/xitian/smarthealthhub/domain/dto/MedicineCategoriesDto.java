package com.xitian.smarthealthhub.domain.dto;

import lombok.Data;

/**
 * 药品分类DTO
 */
@Data
public class MedicineCategoriesDto {
    /**
     * 主键，雪花ID
     */
    private Long id;

    /**
     * 父分类ID（为空表示一级分类）
     */
    private Long parentId;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类描述
     */
    private String description;

    /**
     * 排序
     */
    private Integer sortOrder;

    /**
     * 是否启用：0 否 1 是
     */
    private Integer isEnabled;
}