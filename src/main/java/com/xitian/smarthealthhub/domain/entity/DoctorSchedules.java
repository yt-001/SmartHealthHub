package com.xitian.smarthealthhub.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.xitian.smarthealthhub.bean.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 医生排班实体类
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@TableName("doctor_schedules")
public class DoctorSchedules extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * 医生 users.id，role=1
     */
    @TableField("doctor_id")
    @Schema(description = "医生 users.id，role=1")
    private Long doctorId;

    /**
     * 科室 ID（冗余，方便查询）
     */
    @TableField("dept_id")
    @Schema(description = "科室 ID（冗余，方便查询）")
    private Integer deptId;

    /**
     * 排班日期
     */
    @TableField("schedule_date")
    @Schema(description = "排班日期")
    private LocalDate scheduleDate;

    /**
     * 班次编码：AM 上午 PM 晚班 FULL 全天等
     */
    @TableField("shift_code")
    @Schema(description = "班次编码：AM 上午 PM 晚班 FULL 全天等")
    private String shiftCode;

    /**
     * 诊室编号，可与 doctor_profiles.office_room 不一致
     */
    @TableField("room_no")
    @Schema(description = "诊室编号，可与 doctor_profiles.office_room 不一致")
    private String roomNo;

    /**
     * 最大可预约号数
     */
    @TableField("max_appoint")
    @Schema(description = "最大可预约号数")
    private Integer maxAppoint;

    /**
     * 已占号数
     */
    @TableField("used_appoint")
    @Schema(description = "已占号数")
    private Integer usedAppoint;

    /**
     * 冗余扩展：价格、号源池ID、远程视频标识等
     */
    @TableField("extra_json")
    @Schema(description = "冗余扩展：价格、号源池ID、远程视频标识等")
    private String extraJson;

    /**
     * 状态：1 启用 0 停用（临时停诊）
     */
    @TableField("status")
    @Schema(description = "状态：1 启用 0 停用（临时停诊）")
    private Byte status;

    /**
     * 逻辑删除
     */
    @TableField("is_deleted")
    @Schema(description = "逻辑删除")
    private Byte isDeleted;
}