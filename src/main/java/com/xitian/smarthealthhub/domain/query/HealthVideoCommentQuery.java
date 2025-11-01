package com.xitian.smarthealthhub.domain.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 健康视频评论查询条件
 */
@Schema(description = "健康视频评论查询条件")
@Data
public class HealthVideoCommentQuery {
    
    @Schema(description = "视频ID")
    private Long videoId;
    
    @Schema(description = "用户ID")
    private Long userId;
    
    @Schema(description = "父评论ID（用于查询回复）")
    private Long parentId;
}