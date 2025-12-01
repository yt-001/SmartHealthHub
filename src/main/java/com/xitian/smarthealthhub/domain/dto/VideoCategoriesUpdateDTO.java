package com.xitian.smarthealthhub.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 视频分类更新DTO
 */
@Data
@Schema(description = "视频分类更新DTO")
public class VideoCategoriesUpdateDTO {
    
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