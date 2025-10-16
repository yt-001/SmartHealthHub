package com.xitian.smarthealthhub.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Schema(description = "用户信息更新DTO")
@Data
public class UserUpdateDTO {

    @Schema(description = "用户ID")
    @NotNull(message = "用户ID不能为空")
    private Long id;

    @Schema(description = "真实姓名")
    @NotBlank(message = "真实姓名不能为空")
    @Size(max = 32, message = "真实姓名长度不能超过32个字符")
    private String realName;

    @Schema(description = "手机号")
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    @Schema(description = "邮箱")
    @Size(max = 64, message = "邮箱长度不能超过64个字符")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "邮箱格式不正确",
             flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;

    @Schema(description = "出生日期")
    private LocalDate birthDate;

    @Schema(description = "性别: M男 F女 O其他")
    @Pattern(regexp = "^[MFO]$", message = "性别只能是M(男)、F(女)或O(其他)")
    private String gender;
}