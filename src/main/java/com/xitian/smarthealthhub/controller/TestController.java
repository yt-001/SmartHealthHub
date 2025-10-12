package com.xitian.smarthealthhub.controller;

import com.xitian.smarthealthhub.bean.ResultBean;
import com.xitian.smarthealthhub.bean.StatusCode;
import com.xitian.smarthealthhub.domain.entity.Users;
import com.xitian.smarthealthhub.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 测试控制器，用于创建测试用户等操作
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 创建测试用户接口
     * @param phone 手机号
     * @param password 密码
     * @param username 用户名
     * @param realName 真实姓名
     * @return 用户信息
     */
    @PostMapping("/createUser")
    public ResultBean<String> createTestUser(
            @RequestParam String phone,
            @RequestParam String password,
            @RequestParam String username,
            @RequestParam String realName) {
        
        try {
            // 检查手机号是否已存在
            if (usersService.getOne(new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<Users>()
                    .eq(Users::getPhone, phone)) != null) {
                return ResultBean.fail(com.xitian.smarthealthhub.bean.StatusCode.USER_ALREADY_EXISTS, "手机号已存在");
            }

            // 创建新用户
            Users user = new Users();
            user.setId(System.currentTimeMillis()); // 使用时间戳作为ID
            user.setPhone(phone);
            user.setPasswordHash(passwordEncoder.encode(password)); // 使用BCrypt加密密码
            user.setUsername(username);
            user.setRealName(realName);
            user.setRole((byte) 1); // 默认为患者角色
            user.setStatus((byte) 0); // 正常状态
            user.setGender("M"); // 默认性别
            user.setBirthDate(LocalDate.of(1990, 1, 1)); // 默认生日
            user.setIsDeleted((byte) 0); // 未删除
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());

            // 保存用户
            boolean saved = usersService.save(user);
            
            if (saved) {
                return ResultBean.success("测试用户创建成功，手机号: " + phone + "，密码: " + password);
            } else {
                return ResultBean.fail(StatusCode.SYSTEM_ERROR, "用户创建失败");
            }
        } catch (Exception e) {
            return ResultBean.fail(StatusCode.SYSTEM_ERROR, "创建用户时发生错误: " + e.getMessage());
        }
    }
}