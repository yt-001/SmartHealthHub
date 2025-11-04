package com.xitian.smarthealthhub.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.xitian.smarthealthhub.bean.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 健康视频实体类
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@TableName("health_videos")
public class HealthVideos extends BaseEntity {
    
    /**
     * 视频标题
     */
    @TableField("title")
    private String title;
    
    /**
     * 视频描述
     */
    @TableField("description")
    private String description;
    
    /**
     * 视频文件URL
     */
    @TableField("video_url")
    private String videoUrl;
    
    /**
     * 视频封面图片URL
     */
    @TableField("cover_image_url")
    private String coverImageUrl;
    
    /**
     * 视频时长（秒）
     */
    @TableField("duration")
    private Integer duration;
    
    /**
     * 作者ID，关联users.id，role=1（医生）
     */
    @TableField("author_id")
    private Long authorId;
    
    /**
     * 作者姓名
     */
    @TableField("author_name")
    private String authorName;
    
    /**
     * 视频分类/标签
     */
    @TableField("category")
    private String category;
    
    /**
     * 浏览次数
     */
    @TableField("view_count")
    private Integer viewCount;
    
    /**
     * 点赞次数
     */
    @TableField("like_count")
    private Integer likeCount;
    
    /**
     * 评论次数
     */
    @TableField("comment_count")
    private Integer commentCount;
    
    /**
     * 是否置顶：0 否 1 是
     */
    @TableField("is_top")
    private Byte isTop;
    
    /**
     * 状态：0 草稿 1 已发布 2 已下架 3 审核中 4 未通过审核
     */
    @TableField("status")
    private Byte status;
    
    /**
     * 逻辑删除：0 正常 1 已删
     */
    @TableField("is_deleted")
    private Byte isDeleted;
}