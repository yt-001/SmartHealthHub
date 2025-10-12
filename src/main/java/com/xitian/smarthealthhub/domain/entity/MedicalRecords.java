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
@TableName("medical_records")
public class MedicalRecords extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 关联 users.id，role=2（患者）
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 关联 users.id，role=1（医生）
     */
    @TableField("doctor_id")
    private Long doctorId;

    /**
     * 患者姓名
     */
    @TableField("user_name")
    private String userName;

    /**
     * 医生姓名
     */
    @TableField("doctor_name")
    private String doctorName;

    /**
     * 就诊时的症状描述
     */
    @TableField("symptoms")
    private String symptoms;

    /**
     * 诊断结果
     */
    @TableField("diagnosis")
    private String diagnosis;

    /**
     * 治疗方案
     */
    @TableField("treatment_plan")
    private String treatmentPlan;

    /**
     * 处方信息
     */
    @TableField("prescription")
    private String prescription;

    /**
     * 医生备注
     */
    @TableField("notes")
    private String notes;

    /**
     * 就诊日期
     */
    @TableField("visit_date")
    private LocalDateTime visitDate;

    /**
     * 完成日期（治疗结束时间）
     */
    @TableField("completed_date")
    private LocalDateTime completedDate;

    /**
     * 状态：0 治疗中 1 已完成 2 已归档
     */
    @TableField("status")
    private Byte status;

}