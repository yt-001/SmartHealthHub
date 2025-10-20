package com.xitian.smarthealthhub.domain.vo;

import com.xitian.smarthealthhub.bean.base.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * 医生排班日历展示VO
 */
@Getter
@Setter
@ToString
public class DoctorScheduleCalendarVO extends BaseVO {
    
    @Schema(description = "排班ID")
    private Long id;

    @Schema(description = "排班日期")
    private LocalDate scheduleDate;

    @Schema(description = "班次编码：AM 上午 PM 晚班 FULL 全天等")
    private String shiftCode;

    // 关联信息
    @Schema(description = "医生姓名")
    private String doctorName;
    
    @Schema(description = "科室名称")
    private String deptName;
    
    @Schema(description = "职称")
    private String title;
}