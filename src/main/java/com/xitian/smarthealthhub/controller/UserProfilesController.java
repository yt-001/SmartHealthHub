package com.xitian.smarthealthhub.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xitian.smarthealthhub.bean.ResultBean;
import com.xitian.smarthealthhub.bean.StatusCode;
import com.xitian.smarthealthhub.converter.UserProfilesConverter;
import com.xitian.smarthealthhub.domain.entity.UserProfiles;
import com.xitian.smarthealthhub.domain.vo.UserProfilesVO;
import com.xitian.smarthealthhub.service.UserProfilesService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;

@RestController
@RequestMapping("/user/profiles")
public class UserProfilesController {

    @Resource
    private UserProfilesService userProfilesService;

    /**
     * 根据用户ID获取用户档案信息
     * @param userId 用户ID
     * @return 用户档案信息
     */
    @GetMapping("/{userId}")
    public ResultBean<UserProfilesVO> getUserProfileByUserId(@PathVariable Long userId) {
        LambdaQueryWrapper<UserProfiles> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserProfiles::getUserId, userId);
        UserProfiles userProfiles = userProfilesService.getOne(queryWrapper);
        if (userProfiles == null) {
            return ResultBean.fail(StatusCode.DATA_NOT_FOUND);
        }
        UserProfilesVO vo = UserProfilesConverter.toVO(userProfiles);
        return ResultBean.success(vo);
    }
}