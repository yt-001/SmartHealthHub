package com.xitian.smarthealthhub.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.xitian.smarthealthhub.bean.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

/**
 * 预约挂号实体类
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@TableName("appointments")
public class Appointment extends BaseEntity {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * 医生排班ID，关联 doctor_schedules.id
     */
    @TableField("schedule_id")
    private Long scheduleId;
    
    /**
     * 患者用户ID，关联 users.id
     */
    @TableField("patient_id")
    private Long patientId;
    
    /**
     * 状态：0 待确认 1 已确认 2 已就诊 3 已取消
     */
    @TableField("status")
    private Byte status;
    
    /**
     * 病情描述/备注
     */
    @TableField("description")
    private String description;
    
    /**
     * 挂号单号
     */
    @TableField("registration_no")
    private String registrationNo;
}