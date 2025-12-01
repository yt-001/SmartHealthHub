package com.xitian.smarthealthhub.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * 健康文章更新DTO
 */
@Schema(description = "健康文章更新DTO")
@Data
public class HealthArticleUpdateDTO {
    
    @Schema(description = "文章ID")
    @NotNull(message = "文章ID不能为空")
    private Long id;
    
    @Schema(description = "文章标题")
    @NotBlank(message = "文章标题不能为空")
    @Size(max = 100, message = "文章标题长度不能超过100个字符")
    private String title;
    
    @Schema(description = "文章摘要")
    @Size(max = 500, message = "文章摘要长度不能超过500个字符")
    private String summary;
    
    @Schema(description = "文章内容")
    @NotBlank(message = "文章内容不能为空")
    private String content;
    
    @Schema(description = "文章封面图片URL")
    @Size(max = 255, message = "封面图片URL长度不能超过255个字符")
    private String coverImageUrl;
    
    @Schema(description = "科室ID")
    private Integer deptId;
    
    @Schema(description = "文章分类/标签（JSON格式存储分类ID列表）")
    private String category;
    
    @Schema(description = "是否置顶：0 否 1 是")
    private Byte isTop;
    
    @Schema(description = "状态：0 草稿 1 已发布 2 已下架")
    private Byte status;
}