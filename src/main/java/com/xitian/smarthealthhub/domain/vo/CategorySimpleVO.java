package com.xitian.smarthealthhub.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 分类简化信息VO（只包含ID和名称）
 */
@Data
@Schema(description = "分类简化信息VO")
public class CategorySimpleVO {
    
    @Schema(description = "分类ID")
    private Long id;
    
    @Schema(description = "分类名称")
    private String name;
}