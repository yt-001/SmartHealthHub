package com.xitian.smarthealthhub.domain.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 药品分类关联查询类
 * 
 * @author 
 * @date 2025/02/04
 */
@Getter
@Setter
@ToString
public class MedicineCategoryRelationsQuery {
    /**
     * 药品ID
     */
    @Schema(description = "药品ID")
    private Long medicineId;

    /**
     * 分类ID
     */
    @Schema(description = "分类ID")
    private Long categoryId;

    /**
     * 大类ID（父分类ID）
     */
    @Schema(description = "大类ID（父分类ID）")
    private Long parentCategoryId;
}
