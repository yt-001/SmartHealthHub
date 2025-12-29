package com.xitian.smarthealthhub.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 前台调理推荐-小类VO
 */
@Data
@Schema(description = "前台调理推荐-小类VO")
public class MedicineRecommendationSubCategoryVO {
    @Schema(description = "小类ID")
    private Long id;

    @Schema(description = "小类名称")
    private String name;

    @Schema(description = "小类描述")
    private String desc;

    @Schema(description = "展示背景色")
    private String color;

    @Schema(description = "推荐药品列表")
    private List<MedicineRecommendationMedicineVO> medicines;
}

