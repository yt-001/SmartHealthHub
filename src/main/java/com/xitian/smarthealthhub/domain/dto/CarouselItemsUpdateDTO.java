package com.xitian.smarthealthhub.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 轮播图更新DTO
 */
@Getter
@Setter
@ToString
public class CarouselItemsUpdateDTO {
    
    @Schema(description = "轮播图ID")
    @NotNull(message = "轮播图ID不能为空")
    private Long id;
    
    @Schema(description = "轮播图标题")
    @NotBlank(message = "轮播图标题不能为空")
    private String title;
    
    @Schema(description = "轮播图描述")
    private String description;
    
    @Schema(description = "图片URL")
    @NotBlank(message = "图片URL不能为空")
    private String imageUrl;
    
    @Schema(description = "跳转类型：0 无跳转 1 健康文章 2 健康视频 3 外部链接")
    @NotNull(message = "跳转类型不能为空")
    private Byte targetType;
    
    @Schema(description = "跳转目标ID（文章ID/视频ID）")
    private Long targetId;
    
    @Schema(description = "跳转目标URL（外部链接）")
    private String targetUrl;
    
    @Schema(description = "排序字段，值越小越靠前")
    private Integer sortOrder;
    
    @Schema(description = "状态：0 隐藏 1 显示")
    private Byte status;
}