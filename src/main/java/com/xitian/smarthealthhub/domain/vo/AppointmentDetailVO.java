package com.xitian.smarthealthhub.domain.vo;

import com.xitian.smarthealthhub.bean.base.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * 预约挂号详情VO，包含完整的医生排班信息
 */
@Schema(description = "预约挂号详情VO")
@Getter
@Setter
@ToString
public class AppointmentDetailVO extends BaseVO {

    @Schema(description = "状态：0 待确认 1 已确认 2 已就诊 3 已取消")
    private Byte status;

    @Schema(description = "病情描述/备注")
    private String description;

    @Schema(description = "挂号单号")
    private String registrationNo;

    // 医生排班详细信息
    @Schema(description = "排班ID")
    private Long scheduleId;

    @Schema(description = "排班日期")
    private LocalDate scheduleDate;

    @Schema(description = "班次编码：AM 上午 PM 晚班 FULL 全天等")
    private String shiftCode;

    @Schema(description = "诊室编号")
    private String roomNo;

    @Schema(description = "最大可预约号数")
    private Integer maxAppoint;

    @Schema(description = "已占号数")
    private Integer usedAppoint;

    // 关联信息
    @Schema(description = "医生姓名")
    private String doctorName;

    @Schema(description = "科室名称")
    private String deptName;

    @Schema(description = "医生职称")
    private String doctorTitle;

    @Schema(description = "医生专长")
    private String specialty;
}