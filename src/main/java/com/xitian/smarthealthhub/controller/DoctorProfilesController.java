package com.xitian.smarthealthhub.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xitian.smarthealthhub.bean.ResultBean;
import com.xitian.smarthealthhub.bean.StatusCode;
import com.xitian.smarthealthhub.converter.DoctorProfilesConverter;
import com.xitian.smarthealthhub.domain.entity.DoctorProfiles;
import com.xitian.smarthealthhub.domain.vo.DoctorProfilesVO;
import com.xitian.smarthealthhub.service.DoctorProfilesService;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;

/**
 * 医生档案信息控制器
 */
@RestController
@RequestMapping("/doctor/profiles")
public class DoctorProfilesController {

    @Resource
    private DoctorProfilesService doctorProfilesService;

    /**
     * 根据用户ID获取医生档案信息
     * @param userId 用户ID
     * @return 医生档案信息
     */
    @GetMapping("/{userId}")
    public ResultBean<DoctorProfilesVO> getDoctorProfileByUserId(@PathVariable Long userId) {
        LambdaQueryWrapper<DoctorProfiles> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DoctorProfiles::getUserId, userId);
        DoctorProfiles doctorProfiles = doctorProfilesService.getOne(queryWrapper);
        if (doctorProfiles == null) {
            return ResultBean.fail(StatusCode.DATA_NOT_FOUND);
        }
        DoctorProfilesVO vo = DoctorProfilesConverter.toVO(doctorProfiles);
        return ResultBean.success(vo);
    }
}