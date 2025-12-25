package com.xitian.smarthealthhub.domain.query;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 药品信息主表查询类
 */
@Data
public class MedicinesQuery {
    /**
     * 药品名称（展示名）
     */
    private String name;

    /**
     * 是否处方药：0 否（OTC） 1 是
     */
    private Integer isPrescription;

    /**
     * 状态：0 隐藏 1 显示
     */
    private Integer status;

    /**
     * 价格范围 - 最小值
     */
    private BigDecimal minPrice;

    /**
     * 价格范围 - 最大值
     */
    private BigDecimal maxPrice;

    /**
     * 页码
     */
    private Integer pageNum = 1;

    /**
     * 每页大小
     */
    private Integer pageSize = 10;
}