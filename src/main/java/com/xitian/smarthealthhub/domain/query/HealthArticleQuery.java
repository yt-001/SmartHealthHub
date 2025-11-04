package com.xitian.smarthealthhub.domain.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 健康文章查询条件
 */
@Getter
@Setter
@ToString
public class HealthArticleQuery {
    
    @Schema(description = "文章标题（模糊查询）")
    private String title;
    
    @Schema(description = "作者姓名（模糊查询）")
    private String authorName;
    
    @Schema(description = "科室ID")
    private Integer deptId;
    
    @Schema(description = "文章分类")
    private String category;
    
    @Schema(description = "状态：0 草稿 1 已发布 2 已下架 3 审核中 4 未通过审核")
    private Byte status;
    
    @Schema(description = "状态列表：支持多个状态查询")
    private List<Byte> statusList;
    
    @Schema(description = "创建时间起始")
    private String createdStart;
    
    @Schema(description = "创建时间结束")
    private String createdEnd;
}