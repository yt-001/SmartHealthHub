package com.xitian.smarthealthhub.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.xitian.smarthealthhub.bean.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 健康文章实体类
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@TableName("health_articles")
public class HealthArticles extends BaseEntity {
    
    /**
     * 文章标题
     */
    @TableField("title")
    private String title;
    
    /**
     * 文章摘要
     */
    @TableField("summary")
    private String summary;
    
    /**
     * 文章内容
     */
    @TableField("content")
    private String content;
    
    /**
     * 文章封面图片URL
     */
    @TableField("cover_image_url")
    private String coverImageUrl;
    
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
     * 科室ID
     */
    @TableField("dept_id")
    private Integer deptId;
    
    /**
     * 科室名称
     */
    @TableField("dept_name")
    private String deptName;
    
    /**
     * 文章分类/标签
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