package com.xitian.smarthealthhub.domain.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 药品分类查询类
 * 
 * @author 
 * @date 2025/02/04
 */
@Getter
@Setter
@ToString
public class MedicineCategoriesQuery {
    /**
     * 父分类ID（为空表示一级分类）
     */
    @Schema(description = "父分类ID（为空表示一级分类）")
    private Long parentId;

    /**
     * 分类名称（模糊查询）
     */
    @Schema(description = "分类名称（模糊查询）")
    private String name;

    /**
     * 是否启用：0 否 1 是
     */
    @Schema(description = "是否启用：0 否 1 是")
    private Integer isEnabled;
}