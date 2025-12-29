package com.xitian.smarthealthhub.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 前台调理推荐-大类VO
 */
@Data
@Schema(description = "前台调理推荐-大类VO")
public class MedicineRecommendationCategoryVO {
    @Schema(description = "大类ID")
    private Long id;

    @Schema(description = "大类名称")
    private String name;

    @Schema(description = "图标标识")
    private String icon;

    @Schema(description = "小类列表")
    private List<MedicineRecommendationSubCategoryVO> subCategories;
}

