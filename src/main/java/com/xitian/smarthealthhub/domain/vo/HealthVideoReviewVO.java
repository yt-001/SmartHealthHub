package com.xitian.smarthealthhub.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 健康视频审核列表VO
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "健康视频审核列表VO")
public class HealthVideoReviewVO {
    
    @Schema(description = "主键ID")
    private Long id;
    
    @Schema(description = "视频标题")
    private String title;
    
    @Schema(description = "作者姓名")
    private String authorName;

    @Schema(description = "观看次数")
    private Integer viewCount;
    
    @Schema(description = "视频封面图片URL")
    private String coverImageUrl;
    
    @Schema(description = "视频时长（秒）")
    private Integer duration;
    
    @Schema(description = "状态：0 草稿 1 已发布 2 已下架 3 审核中 4 未通过审核")
    private Byte status;
    
    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}