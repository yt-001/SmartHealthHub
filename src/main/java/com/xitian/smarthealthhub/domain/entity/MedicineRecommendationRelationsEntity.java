package com.xitian.smarthealthhub.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@TableName("medicine_recommendation_relations")
public class MedicineRecommendationRelationsEntity {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @Schema(description = "主键，雪花ID")
    private Long id;

    @TableField("medicine_id")
    @Schema(description = "药品ID")
    private Long medicineId;

    @TableField("recommendation_level_id")
    @Schema(description = "推荐级别ID")
    private Long recommendationLevelId;

    @TableField("created_at")
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
}

