package com.xitian.smarthealthhub.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xitian.smarthealthhub.bean.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

/**
 * 药品分类实体类
 * 
 * @author 
 * @date 2025/02/04
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@TableName("medicine_categories")
public class MedicineCategoriesEntity extends BaseEntity {
    /**
     * 父分类ID（为空表示一级分类）
     */
    @TableField("parent_id")
    @Schema(description = "父分类ID（为空表示一级分类）")
    private Long parentId;

    /**
     * 分类名称
     */
    @TableField("name")
    @Schema(description = "分类名称")
    private String name;

    /**
     * 分类描述
     */
    @TableField("description")
    @Schema(description = "分类描述")
    private String description;

    /**
     * 排序
     */
    @TableField("sort_order")
    @Schema(description = "排序")
    private Integer sortOrder;

    /**
     * 是否启用：0 否 1 是
     */
    @TableField("is_enabled")
    @Schema(description = "是否启用：0 否 1 是")
    private Integer isEnabled;
}