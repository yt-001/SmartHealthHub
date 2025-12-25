package com.xitian.smarthealthhub.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 药品信息主表DTO
 */
@Data
public class MedicinesDto {
    /**
     * 主键，雪花ID
     */
    private Long id;

    /**
     * 药品名称（展示名）
     */
    private String name;

    /**
     * 通用名称
     */
    private String commonName;

    /**
     * 品牌/商品名
     */
    private String brandName;

    /**
     * 简介摘要（用于卡片文案）
     */
    private String description;

    /**
     * 封面图片URL
     */
    private String coverImageUrl;

    /**
     * 图片列表JSON数组
     */
    private String images;

    /**
     * 规格列表JSON数组（如"10包/盒"）
     */
    private String specs;

    /**
     * 标签JSON数组
     */
    private String tags;

    /**
     * 推荐级别文案（如"首选推荐"）
     */
    private String recommendationLevel;

    /**
     * 是否处方药：0 否（OTC） 1 是
     */
    private Integer isPrescription;

    /**
     * 现价
     */
    private BigDecimal price;

    /**
     * 原价
     */
    private BigDecimal originalPrice;

    /**
     * 月售/销量
     */
    private Integer sales;

    /**
     * 好评率百分比（0-100）
     */
    private BigDecimal rating;

    /**
     * 适应症
     */
    private String indications;

    /**
     * 功能主治
     */
    private String functions;

    /**
     * 用法用量
     */
    private String dosage;

    /**
     * 不良反应
     */
    private String adverseReactions;

    /**
     * 禁忌
     */
    private String contraindications;

    /**
     * 注意事项
     */
    private String precautions;

    /**
     * 状态：0 隐藏 1 显示
     */
    private Integer status;

    /**
     * 逻辑删除：0 正常 1 已删
     */
    private Integer isDeleted;
}