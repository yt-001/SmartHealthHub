package com.xitian.smarthealthhub.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 健康视频评论VO
 */
@Schema(description = "健康视频评论VO")
@Data
public class HealthVideoCommentVO {
    
    @Schema(description = "主键ID")
    private Long id;
    
    @Schema(description = "视频ID")
    private Long videoId;
    
    @Schema(description = "评论用户ID")
    private Long userId;
    
    @Schema(description = "评论用户姓名")
    private String userName;
    
    @Schema(description = "评论用户头像URL")
    private String userAvatar;
    
    @Schema(description = "评论内容")
    private String content;
    
    @Schema(description = "点赞次数")
    private Integer likeCount;
    
    @Schema(description = "父评论ID，用于回复功能")
    private Long parentId;
    
    @Schema(description = "被回复用户ID")
    private Long replyToId;
    
    @Schema(description = "被回复用户姓名")
    private String replyToName;
    
    @Schema(description = "状态：0 隐藏 1 显示")
    private Byte status;
    
    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}