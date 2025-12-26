package com.xitian.smarthealthhub.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 药品分类关联实体类
 * 
 * @author 
 * @date 2025/02/04
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@TableName("medicine_category_relations")
public class MedicineCategoryRelationsEntity {
    /**
     * 主键，雪花ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @Schema(description = "主键，雪花ID")
    private Long id;

    /**
     * 药品ID
     */
    @TableField("medicine_id")
    @Schema(description = "药品ID")
    private Long medicineId;

    /**
     * 分类ID
     */
    @TableField("category_id")
    @Schema(description = "分类ID")
    private Long categoryId;

    /**
     * 创建时间
     */
    @TableField("created_at")
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
}