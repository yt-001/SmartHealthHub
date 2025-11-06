package com.xitian.smarthealthhub.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.xitian.smarthealthhub.bean.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 轮播图实体类
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@TableName("carousel_items")
public class CarouselItems extends BaseEntity {
    
    /**
     * 轮播图标题
     */
    @TableField("title")
    private String title;
    
    /**
     * 轮播图描述
     */
    @TableField("description")
    private String description;
    
    /**
     * 图片URL
     */
    @TableField("image_url")
    private String imageUrl;
    
    /**
     * 跳转类型：0 无跳转 1 健康文章 2 健康视频 3 外部链接
     */
    @TableField("target_type")
    private Byte targetType;
    
    /**
     * 跳转目标ID（文章ID/视频ID）
     */
    @TableField("target_id")
    private Long targetId;
    
    /**
     * 跳转目标URL（外部链接）
     */
    @TableField("target_url")
    private String targetUrl;
    
    /**
     * 排序字段，值越小越靠前
     */
    @TableField("sort_order")
    private Integer sortOrder;
    
    /**
     * 状态：0 隐藏 1 显示
     */
    @TableField("status")
    private Byte status;
    
    /**
     * 逻辑删除：0 正常 1 已删
     */
    @TableField("is_deleted")
    private Byte isDeleted;
}