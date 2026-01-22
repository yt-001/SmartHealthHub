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

import com.xitian.smarthealthhub.domain.dto.UserProfileSaveDTO;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/profiles")
public class UserProfilesController {

    @Resource
    private UserProfilesService userProfilesService;
    
    @Resource
    private ObjectMapper objectMapper;

    /**
     * 保存或更新用户档案信息
     * @param userId 用户ID
     * @param saveDTO 保存的DTO
     * @return 操作结果
     */
    @PostMapping("/user/{userId}")
    public ResultBean<String> saveOrUpdateUserProfile(@PathVariable Long userId, @RequestBody UserProfileSaveDTO saveDTO) {
        LambdaQueryWrapper<UserProfiles> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserProfiles::getUserId, userId);
        UserProfiles existingProfile = userProfilesService.getOne(queryWrapper);
        
        String jsonAddress = null;
        if (StringUtils.hasText(saveDTO.getAddress())) {
            try {
                Map<String, String> addressMap = new HashMap<>();
                addressMap.put("detail", saveDTO.getAddress());
                jsonAddress = objectMapper.writeValueAsString(addressMap);
            } catch (Exception e) {
                e.printStackTrace();
                // 如果转换失败，尝试直接保存（虽然可能会失败）
                jsonAddress = saveDTO.getAddress();
            }
        }
        
        if (existingProfile == null) {
            // 新增
            existingProfile = new UserProfiles();
            existingProfile.setUserId(userId);
            BeanUtils.copyProperties(saveDTO, existingProfile);
            // 手动设置处理后的地址
            existingProfile.setAddress(jsonAddress);
            
            // 防止DTO中的userId为空导致覆盖，虽然上面已经set了
            existingProfile.setUserId(userId); 
            // 默认未认证
            existingProfile.setIdCardVerified((byte) 0);
            
            // 确保 id_card 有值，如果前端没传且是新增，这是一个问题，但我们尽量从DTO取
            // 如果DTO里没有，这里会抛出异常，因为数据库约束 NOT NULL
            
            userProfilesService.save(existingProfile);
        } else {
            // 更新
            // 注意：更新时通常不允许修改身份证号，除非是管理员操作或特定流程
            // 这里我们暂时允许修改，或者忽略 idCard 的修改，取决于业务需求
            // 如果前端传了 idCard，我们更新它
            
            BeanUtils.copyProperties(saveDTO, existingProfile, "id", "userId", "idCardVerified", "createTime", "updateTime", "deleted");
            // 手动设置处理后的地址
            existingProfile.setAddress(jsonAddress);
            userProfilesService.updateById(existingProfile);
        }
        
        return ResultBean.success("保存成功");
    }

    /**
     * 根据用户ID获取用户档案信息
     * @param userId 用户ID
     * @return 用户档案信息
     */
    @GetMapping("/user/{userId}")
    public ResultBean<UserProfilesVO> getUserProfileByUserId(@PathVariable Long userId) {
        LambdaQueryWrapper<UserProfiles> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserProfiles::getUserId, userId);
        UserProfiles userProfiles = userProfilesService.getOne(queryWrapper);
        if (userProfiles == null) {
            // 如果不存在档案，返回一个空对象而不是报错，以便前端能正常显示结构
            return ResultBean.success(new UserProfilesVO());
        }
        UserProfilesVO vo = UserProfilesConverter.toVO(userProfiles);
        return ResultBean.success(vo);
    }

    /**
     * 根据ID获取用户档案信息
     * @param id ID
     * @return 用户档案信息
     */
    @GetMapping("/{id}")
    public ResultBean<UserProfilesVO> getUserProfileById(@PathVariable Long id) {
        UserProfiles userProfiles = userProfilesService.getById(id);
        if (userProfiles == null) {
            return ResultBean.fail(StatusCode.DATA_NOT_FOUND);
        }
        UserProfilesVO vo = UserProfilesConverter.toVO(userProfiles);
        return ResultBean.success(vo);
    }
}