package com.xitian.smarthealthhub.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xitian.smarthealthhub.bean.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 药品与分类的关联表实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("medicine_category_relations")
public class MedicineCategoryRelationsEntity extends BaseEntity {
    /**
     * 药品ID
     */
    private Long medicineId;

    /**
     * 分类ID
     */
    private Long categoryId;
}