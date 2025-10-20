package com.xitian.smarthealthhub.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.xitian.smarthealthhub.bean.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * 科室实体类
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@TableName("departments")
public class Departments extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 科室名称
     */
    @TableField("name")
    @Schema(description = "科室名称")
    private String name;

    /**
     * 科室描述
     */
    @TableField("description")
    @Schema(description = "科室描述")
    private String description;

    /**
     * 科室主任医生ID，关联users.id
     */
    @TableField("head_doctor_id")
    @Schema(description = "科室主任医生ID，关联users.id")
    private Long headDoctorId;

    /**
     * 科室状态：1 启用 0 停用
     */
    @TableField("is_active")
    @Schema(description = "科室状态：1 启用 0 停用")
    private Byte isActive;
}