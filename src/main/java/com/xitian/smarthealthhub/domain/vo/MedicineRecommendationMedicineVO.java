package com.xitian.smarthealthhub.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 前台调理推荐-药品信息VO
 */
@Data
@Schema(description = "前台调理推荐-药品信息VO")
public class MedicineRecommendationMedicineVO {
    @Schema(description = "药品ID")
    private Long id;

    @Schema(description = "药品名称")
    private String name;

    @Schema(description = "封面图片URL")
    private String image;

    @Schema(description = "简介描述")
    private String desc;

    @Schema(description = "价格")
    private BigDecimal price;

    @Schema(description = "标签集合")
    private List<String> tags;

    @Schema(description = "推荐级别文案")
    private String recommendationLevel;
}

