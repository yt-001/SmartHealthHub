package com.xitian.smarthealthhub.domain.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户端文章查询条件
 */
@Getter
@Setter
@ToString
public class HealthArticlePublicQuery {
    
    @Schema(description = "文章标题（模糊查询）")
    private String title;
    
    @Schema(description = "作者姓名（模糊查询）")
    private String authorName;
    
    @Schema(description = "科室ID")
    private Integer deptId;
    
    @Schema(description = "文章分类")
    private String category;
    
    @Schema(description = "是否置顶：0 否 1 是")
    private Byte isTop;
}