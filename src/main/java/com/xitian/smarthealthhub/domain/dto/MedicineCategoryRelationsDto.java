package com.xitian.smarthealthhub.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 药品分类关联数据传输对象
 * 
 * @author 
 * @date 2025/02/04
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class MedicineCategoryRelationsDto {
    /**
     * 主键，雪花ID
     */
    @Schema(description = "主键，雪花ID")
    private Long id;

    /**
     * 药品ID
     */
    @Schema(description = "药品ID")
    private Long medicineId;

    /**
     * 分类ID
     */
    @Schema(description = "分类ID")
    private Long categoryId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}