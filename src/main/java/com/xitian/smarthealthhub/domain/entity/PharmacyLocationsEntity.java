package com.xitian.smarthealthhub.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xitian.smarthealthhub.bean.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 取药地点实体类
 *
 * @author 
 * @date 2025/02/04
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@TableName("pharmacy_locations")
public class PharmacyLocationsEntity extends BaseEntity {
    /**
     * 地点名称
     */
    @TableField("name")
    @Schema(description = "地点名称")
    private String name;

    /**
     * 详细地址
     */
    @TableField("address")
    @Schema(description = "详细地址")
    private String address;

    /**
     * 联系电话
     */
    @TableField("phone")
    @Schema(description = "联系电话")
    private String phone;

    /**
     * 营业时间
     */
    @TableField("opening_hours")
    @Schema(description = "营业时间")
    private String openingHours;

    /**
     * 是否启用：0 否 1 是
     */
    @TableField("is_enabled")
    @Schema(description = "是否启用：0 否 1 是")
    private Integer isEnabled;

    /**
     * 排序
     */
    @TableField("sort_order")
    @Schema(description = "排序")
    private Integer sortOrder;
}
