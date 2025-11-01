package com.xitian.smarthealthhub.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * 健康视频评论创建DTO
 */
@Schema(description = "健康视频评论创建DTO")
@Data
public class HealthVideoCommentCreateDTO {
    
    @Schema(description = "视频ID")
    @NotNull(message = "视频ID不能为空")
    private Long videoId;
    
    @Schema(description = "用户ID")
    @NotNull(message = "用户ID不能为空")
    private Long userId;
    
    @Schema(description = "评论内容")
    @NotBlank(message = "评论内容不能为空")
    @Size(max = 500, message = "评论内容长度不能超过500个字符")
    private String content;
    
    @Schema(description = "父评论ID，用于回复功能")
    private Long parentId;
    
    @Schema(description = "被回复用户ID")
    private Long replyToId;
}