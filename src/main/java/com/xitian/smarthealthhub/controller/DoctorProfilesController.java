package com.xitian.smarthealthhub.controller;

import com.xitian.smarthealthhub.domain.entity.DoctorProfiles;
import com.xitian.smarthealthhub.service.DoctorProfilesService;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;

@RestController
@RequestMapping("/doctorProfiles")
public class DoctorProfilesController {

    @Resource
    private DoctorProfilesService doctorProfilesService;

    // 可在此处添加基本的CRUD方法
}
