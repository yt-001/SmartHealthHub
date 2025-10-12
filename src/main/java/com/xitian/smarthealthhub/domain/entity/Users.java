package com.xitian.smarthealthhub.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("users")
public class Users implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键，雪花 ID，全局唯一
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 登录账号，业务唯一
     */
    @TableField("username")
    private String username;

    /**
     * bcrypt/argon2 哈希，固定 60 位
     */
    @TableField("password_hash")
    private String passwordHash;

    /**
     * 头像 CDN 地址，列表页直接露出
     */
    @TableField("avatar_url")
    private String avatarUrl;

    /**
     * 真实姓名
     */
    @TableField("real_name")
    private String realName;

    /**
     * 身份：0 管理员 1 医生 2 患者
     */
    @TableField("role")
    private Byte role;

    /**
     * 手机号，业务唯一
     */
    @TableField("phone")
    private String phone;

    /**
     * 邮箱，可空，找回密码用
     */
    @TableField("email")
    private String email;

    /**
     * 性别 M 男 F 女 O 其他
     */
    @TableField("gender")
    private String gender;

    /**
     * 账号状态：0 正常 1 锁定 2 未激活 3 已注销
     */
    @TableField("status")
    private Byte status;

    /**
     * 出生日期，计算年龄
     */
    @TableField("birth_date")
    private LocalDate birthDate;

    /**
     * 逻辑删除 0 正常 1 已删
     */
    @TableField("is_deleted")
    private Byte isDeleted;

    /**
     * 创建时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField("updated_at")
    private LocalDateTime updatedAt;
}