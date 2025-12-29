package com.xitian.smarthealthhub.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xitian.smarthealthhub.bean.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@TableName("medicine_tags")
public class MedicineTagsEntity extends BaseEntity {
    @TableField("name")
    @Schema(description = "标签名称")
    private String name;

    @TableField("description")
    @Schema(description = "标签描述")
    private String description;

    @TableField("sort_order")
    @Schema(description = "排序")
    private Integer sortOrder;

    @TableField("is_enabled")
    @Schema(description = "是否启用：0 否 1 是")
    private Integer isEnabled;
}

