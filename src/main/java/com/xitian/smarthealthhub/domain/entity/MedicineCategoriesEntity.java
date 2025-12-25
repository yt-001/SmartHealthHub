package com.xitian.smarthealthhub.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xitian.smarthealthhub.bean.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 药品分类表实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("medicine_categories")
public class MedicineCategoriesEntity extends BaseEntity {
    /**
     * 父分类ID（为空表示一级分类）
     */
    private Long parentId;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类描述
     */
    private String description;

    /**
     * 排序
     */
    private Integer sortOrder;

    /**
     * 是否启用：0 否 1 是
     */
    private Integer isEnabled;
}