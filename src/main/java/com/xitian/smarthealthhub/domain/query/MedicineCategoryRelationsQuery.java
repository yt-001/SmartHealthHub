package com.xitian.smarthealthhub.domain.query;

import lombok.Data;

/**
 * 药品与分类的关联表查询类
 */
@Data
public class MedicineCategoryRelationsQuery {
    /**
     * 药品ID
     */
    private Long medicineId;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 页码
     */
    private Integer pageNum = 1;

    /**
     * 每页大小
     */
    private Integer pageSize = 10;
}