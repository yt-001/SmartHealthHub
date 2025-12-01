package com.xitian.smarthealthhub.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 文章分类关联DTO
 */
@Data
@Schema(description = "文章分类关联DTO")
public class ArticleCategoryRelationDTO {
    
    @Schema(description = "文章ID")
    private Long articleId;
    
    @Schema(description = "分类ID列表")
    private List<Long> categoryIds;
}