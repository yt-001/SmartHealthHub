package com.xitian.smarthealthhub.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 文章分类实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("article_categories")
@Schema(name = "ArticleCategories", description = "文章分类实体")
public class ArticleCategories {
    
    @Schema(description = "主键，雪花ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    
    @Schema(description = "分类名称")
    @TableField("name")
    private String name;
    
    @Schema(description = "分类描述")
    @TableField("description")
    private String description;
    
    @Schema(description = "排序")
    @TableField("sort_order")
    private Integer sortOrder;
    
    @Schema(description = "是否启用：0 否 1 是")
    @TableField("is_enabled")
    private Byte isEnabled;
    
    @Schema(description = "创建时间")
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    
    @Schema(description = "更新时间")
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}