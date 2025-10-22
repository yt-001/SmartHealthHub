package com.xitian.smarthealthhub.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

/**
 * 医生排班创建DTO
 */
@Data
@Schema(description = "医生排班创建请求体")
public class DoctorScheduleCreateDTO {

    @Schema(description = "医生ID")
    @NotNull(message = "医生ID不能为空")
    private Long doctorId;

    @Schema(description = "科室ID")
    @NotNull(message = "科室ID不能为空")
    private Integer deptId;

    @Schema(description = "排班日期")
    @NotNull(message = "排班日期不能为空")
    private LocalDate scheduleDate;

    @Schema(description = "班次编码：AM 上午 PM 晚班 FULL 全天等")
    @NotBlank(message = "班次编码不能为空")
    private String shiftCode;

    @Schema(description = "诊室编号")
    private String roomNo;

    @Schema(description = "最大可预约号数")
    @Positive(message = "最大可预约号数必须大于0")
    private Integer maxAppoint;

    @Schema(description = "冗余扩展：价格、号源池ID、远程视频标识等，JSON格式")
    private String extraJson;
}