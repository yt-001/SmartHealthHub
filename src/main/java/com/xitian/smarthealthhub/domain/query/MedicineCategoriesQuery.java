package com.xitian.smarthealthhub.domain.query;

import lombok.Data;

/**
 * 药品分类查询类
 */
@Data
public class MedicineCategoriesQuery {
    /**
     * 父分类ID（为空表示一级分类）
     */
    private Long parentId;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 是否启用：0 否 1 是
     */
    private Integer isEnabled;

    /**
     * 页码
     */
    private Integer pageNum = 1;

    /**
     * 每页大小
     */
    private Integer pageSize = 10;
}