package com.xitian.smarthealthhub.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.xitian.smarthealthhub.bean.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 健康视频评论实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("health_video_comments")
@Schema(description = "健康视频评论实体类")
public class HealthVideoComments extends BaseEntity {
    
    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    @Schema(description = "视频ID")
    @TableField("video_id")
    private Long videoId;
    
    @Schema(description = "评论用户ID")
    @TableField("user_id")
    private Long userId;
    
    @Schema(description = "评论用户姓名")
    @TableField("user_name")
    private String userName;
    
    @Schema(description = "评论用户头像URL")
    @TableField("user_avatar")
    private String userAvatar;
    
    @Schema(description = "评论内容")
    @TableField("content")
    private String content;
    
    @Schema(description = "点赞次数")
    @TableField("like_count")
    private Integer likeCount;
    
    @Schema(description = "父评论ID，用于回复功能")
    @TableField("parent_id")
    private Long parentId;
    
    @Schema(description = "被回复用户ID")
    @TableField("reply_to_id")
    private Long replyToId;
    
    @Schema(description = "被回复用户姓名")
    @TableField("reply_to_name")
    private String replyToName;
    
    @Schema(description = "状态：0 隐藏 1 显示")
    @TableField("status")
    private Byte status;
    
    @Schema(description = "逻辑删除：0 正常 1 已删")
    @TableField("is_deleted")
    private Byte isDeleted;
}