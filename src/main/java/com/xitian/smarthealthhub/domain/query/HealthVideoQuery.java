package com.xitian.smarthealthhub.domain.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 健康视频查询条件
 */
@Getter
@Setter
@ToString
public class HealthVideoQuery {
    
    @Schema(description = "视频标题（模糊查询）")
    private String title;
    
    @Schema(description = "作者姓名（模糊查询）")
    private String authorName;
    
    @Schema(description = "视频分类")
    private String category;
    
    @Schema(description = "是否置顶：0 否 1 是")
    private Byte isTop;
    
    @Schema(description = "状态：0 草稿 1 已发布 2 已下架 3 审核中 4 未通过审核")
    private Byte status;
    
    @Schema(description = "状态列表：用于多状态查询，如[3, 4]表示查询审核中和未通过审核的视频")
    private List<Byte> statusList;
    
    @Schema(description = "创建时间起始")
    private String createdStart;
    
    @Schema(description = "创建时间结束")
    private String createdEnd;
}