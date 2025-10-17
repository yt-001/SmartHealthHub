package com.xitian.smarthealthhub.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.xitian.smarthealthhub.bean.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@TableName("doctor_profiles")
public class DoctorProfiles extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 关联 users.id，role=1
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 科室 ID（建议另建字典表）
     */
    @TableField("dept_id")
    private Integer deptId;

    /**
     * 职称：主任医师/副主任医师/主治医师/住院医师
     */
    @TableField("title")
    private String title;

    /**
     * 擅长病种/技术，逗号分隔
     */
    @TableField("specialty")
    private String specialty;

    /**
     * 医师执业证号，卫健委可查
     */
    @TableField("qualification_no")
    private String qualificationNo;

    /**
     * 个人简介、履历
     */
    @TableField("bio")
    private String bio;

    /**
     * 门诊班次，如"周一上午 周三下午"
     */
    @TableField("work_shift")
    private String workShift;

    /**
     * 诊室编号
     */
    @TableField("office_room")
    private String officeRoom;

    /**
     * 资质认证状态 0 未通过 1 已通过 2 审核中
     */
    @TableField("qualification_verified")
    private Byte qualificationVerified;

    /**
     * 逻辑删除
     */
    @TableField("is_deleted")
    private Byte isDeleted;

}