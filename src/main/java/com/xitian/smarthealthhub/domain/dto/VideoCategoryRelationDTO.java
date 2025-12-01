package com.xitian.smarthealthhub.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 视频分类关联DTO
 */
@Data
@Schema(description = "视频分类关联DTO")
public class VideoCategoryRelationDTO {
    
    @Schema(description = "视频ID")
    private Long videoId;
    
    @Schema(description = "分类ID列表")
    private List<Long> categoryIds;
}