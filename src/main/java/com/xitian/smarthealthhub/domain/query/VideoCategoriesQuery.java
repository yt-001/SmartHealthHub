package com.xitian.smarthealthhub.domain.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 视频分类查询条件
 */
@Getter
@Setter
@ToString
public class VideoCategoriesQuery {
    
    @Schema(description = "分类名称（模糊查询）")
    private String name;
    
    @Schema(description = "是否启用：0 否 1 是")
    private Byte isEnabled;
}