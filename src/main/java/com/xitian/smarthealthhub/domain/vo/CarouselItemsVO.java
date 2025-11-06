package com.xitian.smarthealthhub.domain.vo;

import com.xitian.smarthealthhub.bean.base.BaseVO;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 轮播图视图对象
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CarouselItemsVO extends BaseVO {
    
    /**
     * 轮播图标题
     */
    private String title;
    
    /**
     * 轮播图描述
     */
    private String description;
    
    /**
     * 图片URL
     */
    private String imageUrl;
    
    /**
     * 跳转类型：0 无跳转 1 健康文章 2 健康视频 3 外部链接
     */
    private Byte targetType;
    
    /**
     * 跳转目标ID（文章ID/视频ID）
     */
    private Long targetId;
    
    /**
     * 跳转目标URL（外部链接）
     */
    private String targetUrl;
    
    /**
     * 排序字段，值越小越靠前
     */
    private Integer sortOrder;
    
    /**
     * 状态：0 隐藏 1 显示
     */
    private Byte status;
}