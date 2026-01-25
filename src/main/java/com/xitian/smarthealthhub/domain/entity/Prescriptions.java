package com.xitian.smarthealthhub.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.xitian.smarthealthhub.bean.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * 处方实体类
 * 独立于 MedicalRecords (病例档案)，专门记录医生开具的药品详情
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@TableName("prescriptions")
public class Prescriptions extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 关联 medical_records.id (可选，如果处方是在某次就诊中开具的)
     */
    @TableField("medical_record_id")
    private Long medicalRecordId;

    /**
     * 关联 users.id (患者)
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 关联 users.id (医生)
     */
    @TableField("doctor_id")
    private Long doctorId;

    /**
     * 关联 users.username (患者姓名快照)
     */
    @TableField("patient_name")
    private String patientName;

    /**
     * 关联 users.username (医生姓名快照)
     */
    @TableField("doctor_name")
    private String doctorName;

    /**
     * 诊断信息 (冗余/快照)
     */
    @TableField("diagnosis")
    private String diagnosis;

    /**
     * 处方详情 (JSON格式存储药品列表：[{name, spec, count, usage, notes}])
     */
    @TableField("medication_details")
    private String medicationDetails;

    /**
     * 医嘱/备注
     */
    @TableField("notes")
    private String notes;

    /**
     * 状态: 0-待支付/待取药, 1-已支付/已取药, 2-已作废
     */
    @TableField("status")
    private Byte status;
}
