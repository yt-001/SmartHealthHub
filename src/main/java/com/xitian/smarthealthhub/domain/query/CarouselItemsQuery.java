package com.xitian.smarthealthhub.domain.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 轮播图查询条件
 */
@Getter
@Setter
@ToString
public class CarouselItemsQuery {
    
    @Schema(description = "跳转类型：0 无跳转 1 健康文章 2 健康视频 3 外部链接")
    private Byte targetType;
    
    @Schema(description = "状态：0 隐藏 1 显示")
    private Byte status;
}