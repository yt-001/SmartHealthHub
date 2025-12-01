package com.xitian.smarthealthhub.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 视频分类关联实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("video_category_relations")
@Schema(name = "VideoCategoryRelations", description = "视频分类关联实体")
public class VideoCategoryRelations {
    
    @Schema(description = "主键，雪花ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    
    @Schema(description = "视频ID")
    @TableField("video_id")
    private Long videoId;
    
    @Schema(description = "分类ID")
    @TableField("category_id")
    private Long categoryId;
    
    @Schema(description = "创建时间")
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}