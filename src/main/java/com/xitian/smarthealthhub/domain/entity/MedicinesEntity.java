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

import java.math.BigDecimal;

/**
 * 药品实体类
 * 
 * @author 
 * @date 2025/02/04
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@TableName("medicines")
public class MedicinesEntity extends BaseEntity {
    /**
     * 药品名称（展示名）
     */
    @TableField("name")
    @Schema(description = "药品名称（展示名）")
    private String name;

    /**
     * 通用名称
     */
    @TableField("common_name")
    @Schema(description = "通用名称")
    private String commonName;

    /**
     * 品牌/商品名
     */
    @TableField("brand_name")
    @Schema(description = "品牌/商品名")
    private String brandName;

    /**
     * 简介摘要（用于卡片文案）
     */
    @TableField("description")
    @Schema(description = "简介摘要（用于卡片文案）")
    private String description;

    /**
     * 封面图片URL
     */
    @TableField("cover_image_url")
    @Schema(description = "封面图片URL")
    private String coverImageUrl;

    /**
     * 图片列表JSON数组
     */
    @TableField("images")
    @Schema(description = "图片列表JSON数组")
    private String images;

    /**
     * 规格列表JSON数组（如"10包/盒"）
     */
    @TableField("specs")
    @Schema(description = "规格列表JSON数组（如\"10包/盒\"）")
    private String specs;

    /**
     * 是否处方药：0 否（OTC） 1 是
     */
    @TableField("is_prescription")
    @Schema(description = "是否处方药：0 否（OTC） 1 是")
    private Integer isPrescription;

    /**
     * 现价
     */
    @TableField("price")
    @Schema(description = "现价")
    private BigDecimal price;

    /**
     * 原价
     */
    @TableField("original_price")
    @Schema(description = "原价")
    private BigDecimal originalPrice;

    /**
     * 月售/销量
     */
    @TableField("sales")
    @Schema(description = "月售/销量")
    private Integer sales;

    /**
     * 好评率百分比（0-100）
     */
    @TableField("rating")
    @Schema(description = "好评率百分比（0-100）")
    private BigDecimal rating;

    /**
     * 适应症
     */
    @TableField("indications")
    @Schema(description = "适应症")
    private String indications;

    /**
     * 功能主治
     */
    @TableField("functions")
    @Schema(description = "功能主治")
    private String functions;

    /**
     * 用法用量
     */
    @TableField("dosage")
    @Schema(description = "用法用量")
    private String dosage;

    /**
     * 不良反应
     */
    @TableField("adverse_reactions")
    @Schema(description = "不良反应")
    private String adverseReactions;

    /**
     * 禁忌
     */
    @TableField("contraindications")
    @Schema(description = "禁忌")
    private String contraindications;

    /**
     * 注意事项
     */
    @TableField("precautions")
    @Schema(description = "注意事项")
    private String precautions;

    /**
     * 状态：0 隐藏 1 显示
     */
    @TableField("status")
    @Schema(description = "状态：0 隐藏 1 显示")
    private Integer status;

    /**
     * 逻辑删除：0 正常 1 已删
     */
    @TableField("is_deleted")
    @Schema(description = "逻辑删除：0 正常 1 已删")
    private Integer isDeleted;
}
