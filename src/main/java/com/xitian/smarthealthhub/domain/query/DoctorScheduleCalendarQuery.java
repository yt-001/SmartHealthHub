package com.xitian.smarthealthhub.domain.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * 医生排班日历查询条件
 */
@Getter
@Setter
@ToString
public class DoctorScheduleCalendarQuery {
    
    @Schema(description = "排班日期起始")
    private LocalDate startDate;

    @Schema(description = "排班日期结束")
    private LocalDate endDate;
    
    @Schema(description = "医生ID")
    private Long doctorId;

    @Schema(description = "科室ID")
    private Integer deptId;
}