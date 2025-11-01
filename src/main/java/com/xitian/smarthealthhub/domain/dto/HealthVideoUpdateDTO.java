package com.xitian.smarthealthhub.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * 健康视频更新DTO
 */
@Schema(description = "健康视频更新DTO")
@Data
public class HealthVideoUpdateDTO {
    
    @Schema(description = "视频ID")
    @NotNull(message = "视频ID不能为空")
    private Long id;
    
    @Schema(description = "视频标题")
    @NotBlank(message = "视频标题不能为空")
    @Size(max = 100, message = "视频标题长度不能超过100个字符")
    private String title;
    
    @Schema(description = "视频描述")
    @Size(max = 500, message = "视频描述长度不能超过500个字符")
    private String description;
    
    @Schema(description = "视频封面图片URL")
    @Size(max = 255, message = "封面图片URL长度不能超过255个字符")
    private String coverImageUrl;
    
    @Schema(description = "视频文件URL")
    @NotBlank(message = "视频文件URL不能为空")
    @Size(max = 255, message = "视频文件URL长度不能超过255个字符")
    private String videoUrl;
    
    @Schema(description = "视频时长（秒）")
    private Integer duration;
    
    @Schema(description = "视频分类/标签")
    @Size(max = 50, message = "视频分类长度不能超过50个字符")
    private String category;
    
    @Schema(description = "是否置顶：0 否 1 是")
    private Byte isTop;
    
    @Schema(description = "状态：0 草稿 1 已发布 2 已下架")
    private Byte status;
}