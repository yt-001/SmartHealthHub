package com.xitian.smarthealthhub.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_profiles")
public class UserProfiles implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键，雪花 ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 关联 users.id，role=2
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 身份证号，业务唯一
     */
    @TableField("id_card")
    private String idCard;

    /**
     * 血型
     */
    @TableField("blood_type")
    private String bloodType;

    /**
     * 过敏史，自由文本
     */
    @TableField("allergy_history")
    private String allergyHistory;

    /**
     * 慢性病史
     */
    @TableField("chronic_disease")
    private String chronicDisease;

    /**
     * 当前主诉/症状
     */
    @TableField("current_symptoms")
    private String currentSymptoms;

    /**
     * 正在执行的治疗方案
     */
    @TableField("current_plan")
    private String currentPlan;

    /**
     * 下一步建议（检查/复诊/手术等）
     */
    @TableField("next_step")
    private String nextStep;

    /**
     * 常住地址快照 {"省":"xx","市":"xx","区":"xx","detail":"路号"}
     */
    @TableField("address")
    private String address;

    /**
     * 实名认证状态 0 未 1 已通过 2 审核中（医疗必填）
     */
    @TableField("id_card_verified")
    private Byte idCardVerified;

    /**
     * 紧急联系人姓名
     */
    @TableField("emergency_name")
    private String emergencyName;

    /**
     * 紧急联系人电话
     */
    @TableField("emergency_phone")
    private String emergencyPhone;

    /**
     * 建档时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /**
     * 最后更新
     */
    @TableField("updated_at")
    private LocalDateTime updatedAt;

}
