package com.xitian.smarthealthhub.controller;

import com.xitian.smarthealthhub.domain.entity.UserProfiles;
import com.xitian.smarthealthhub.service.UserProfilesService;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;

@RestController
@RequestMapping("/userProfiles")
public class UserProfilesController {

    @Resource
    private UserProfilesService userProfilesService;

    // 可在此处添加基本的CRUD方法
}
