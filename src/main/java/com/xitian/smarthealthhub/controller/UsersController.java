package com.xitian.smarthealthhub.controller;

import com.xitian.smarthealthhub.domain.entity.Users;
import com.xitian.smarthealthhub.service.UsersService;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Resource
    private UsersService usersService;

    // 可在此处添加基本的CRUD方法
}
