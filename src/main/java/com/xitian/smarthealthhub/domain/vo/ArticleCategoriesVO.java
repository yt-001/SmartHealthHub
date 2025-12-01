package com.xitian.smarthealthhub.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 文章分类VO
 */
@Data
@Schema(description = "文章分类VO")
public class ArticleCategoriesVO {
    
    @Schema(description = "主键，雪花ID")
    private Long id;
    
    @Schema(description = "分类名称")
    private String name;
    
    @Schema(description = "分类描述")
    private String description;
    
    @Schema(description = "排序")
    private Integer sortOrder;
    
    @Schema(description = "是否启用：0 否 1 是")
    private Byte isEnabled;
}