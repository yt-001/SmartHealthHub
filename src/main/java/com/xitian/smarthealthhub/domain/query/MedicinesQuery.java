package com.xitian.smarthealthhub.domain.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 药品查询类
 * 
 * @author 
 * @date 2025/02/04
 */
@Getter
@Setter
@ToString
public class MedicinesQuery {
    /**
     * 药品名称（模糊查询）
     */
    @Schema(description = "药品名称（模糊查询）")
    private String name;

    /**
     * 是否处方药（0-非处方药，1-处方药）
     */
    @Schema(description = "是否处方药（0-非处方药，1-处方药）")
    private Integer isPrescription;

    /**
     * 状态（0-隐藏，1-显示）
     */
    @Schema(description = "状态（0-隐藏，1-显示）")
    private Integer status;
    
    /**
     * 分类ID（通过关联表查询）
     */
    @Schema(description = "分类ID")
    private Long categoryId;
    
    /**
     * 大类ID（通过关联表查询其子分类下的药品）
     */
    @Schema(description = "大类ID")
    private Long parentCategoryId;
}