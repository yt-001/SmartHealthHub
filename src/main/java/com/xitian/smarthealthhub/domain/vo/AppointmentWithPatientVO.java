package com.xitian.smarthealthhub.domain.vo;

import com.xitian.smarthealthhub.bean.base.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * 包含患者信息的预约挂号信息VO
 */
@Schema(description = "包含患者信息的预约挂号信息VO")
@Getter
@Setter
@ToString
public class AppointmentWithPatientVO extends BaseVO {
    
    @Schema(description = "医生排班ID，关联 doctor_schedules.id")
    private Long scheduleId;
    
    @Schema(description = "患者用户ID，关联 users.id")
    private Long patientId;
    
    @Schema(description = "状态：0 待确认 1 已确认 2 已就诊 3 已取消")
    private Byte status;
    
    @Schema(description = "病情描述/备注")
    private String description;
    
    @Schema(description = "挂号单号")
    private String registrationNo;
    
    @Schema(description = "患者姓名")
    private String patientName;
    
    @Schema(description = "患者出生日期")
    private LocalDate patientBirthDate;
    
    @Schema(description = "患者性别")
    private String patientGender;
    
    @Schema(description = "患者电话")
    private String patientPhone;
}