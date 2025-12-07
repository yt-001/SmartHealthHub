package com.xitian.smarthealthhub.domain.vo;

import com.xitian.smarthealthhub.bean.base.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 预约挂号信息VO
 */
@Schema(description = "预约挂号信息VO")
@Getter
@Setter
@ToString
public class AppointmentVO extends BaseVO {
    
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
}