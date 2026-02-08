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
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;
import java.util.HashMap;
import java.util.Map;
import jakarta.validation.Valid;

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
    public ResultBean<String> saveOrUpdateUserProfile(@PathVariable Long userId, @Valid @RequestBody UserProfileSaveDTO saveDTO) {
        if (!StringUtils.hasText(saveDTO.getIdCard())) {
            return ResultBean.fail(StatusCode.VALIDATION_ERROR, "请输入身份证号");
        }
        String idCardTrim = saveDTO.getIdCard().trim();
        if (idCardTrim.contains("*")) {
            return ResultBean.fail(StatusCode.VALIDATION_ERROR, "身份证号格式不正确");
        }
        if (!idCardTrim.matches("(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)")) {
            return ResultBean.fail(StatusCode.VALIDATION_ERROR, "身份证号格式不正确");
        }
        if (!StringUtils.hasText(saveDTO.getBloodType())) {
            return ResultBean.fail(StatusCode.VALIDATION_ERROR, "请选择血型");
        }
        if (!StringUtils.hasText(saveDTO.getCurrentSymptoms())) {
            return ResultBean.fail(StatusCode.VALIDATION_ERROR, "请输入当前症状");
        }
        if (!StringUtils.hasText(saveDTO.getCurrentPlan())) {
            return ResultBean.fail(StatusCode.VALIDATION_ERROR, "请输入当前方案");
        }
        if (!StringUtils.hasText(saveDTO.getNextStep())) {
            return ResultBean.fail(StatusCode.VALIDATION_ERROR, "请输入下一步建议");
        }
        if (!StringUtils.hasText(saveDTO.getAddress())) {
            return ResultBean.fail(StatusCode.VALIDATION_ERROR, "请输入居住地址");
        }
        if (!StringUtils.hasText(saveDTO.getEmergencyName())) {
            return ResultBean.fail(StatusCode.VALIDATION_ERROR, "请输入紧急联系人姓名");
        }
        if (!StringUtils.hasText(saveDTO.getEmergencyPhone())) {
            return ResultBean.fail(StatusCode.VALIDATION_ERROR, "请输入紧急联系人电话");
        }
        String emergencyPhoneTrim = saveDTO.getEmergencyPhone().trim();
        if (emergencyPhoneTrim.contains("*") || !emergencyPhoneTrim.matches("^1[3-9]\\d{9}$")) {
            return ResultBean.fail(StatusCode.VALIDATION_ERROR, "紧急联系人电话格式不正确");
        }

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
            existingProfile.setIdCard(idCardTrim);
            existingProfile.setBloodType(saveDTO.getBloodType());
            existingProfile.setAllergyHistory(saveDTO.getAllergyHistory());
            existingProfile.setChronicDisease(saveDTO.getChronicDisease());
            existingProfile.setCurrentSymptoms(StringUtils.hasText(saveDTO.getCurrentSymptoms()) ? saveDTO.getCurrentSymptoms() : "待完善");
            existingProfile.setCurrentPlan(StringUtils.hasText(saveDTO.getCurrentPlan()) ? saveDTO.getCurrentPlan() : "待完善");
            existingProfile.setNextStep(StringUtils.hasText(saveDTO.getNextStep()) ? saveDTO.getNextStep() : "待完善");
            existingProfile.setAddress(jsonAddress);
            existingProfile.setEmergencyName(saveDTO.getEmergencyName());
            existingProfile.setEmergencyPhone(emergencyPhoneTrim);
            existingProfile.setIdCardVerified((byte) 2);
            
            // 确保 id_card 有值，如果前端没传且是新增，这是一个问题，但我们尽量从DTO取
            // 如果DTO里没有，这里会抛出异常，因为数据库约束 NOT NULL
            
            userProfilesService.save(existingProfile);
        } else {
            // 更新
            // 注意：更新时通常不允许修改身份证号，除非是管理员操作或特定流程
            // 这里我们暂时允许修改，或者忽略 idCard 的修改，取决于业务需求
            // 如果前端传了 idCard，我们更新它

            existingProfile.setIdCard(idCardTrim);
            if (saveDTO.getBloodType() != null) {
                existingProfile.setBloodType(saveDTO.getBloodType());
            }
            if (saveDTO.getAllergyHistory() != null) {
                existingProfile.setAllergyHistory(saveDTO.getAllergyHistory());
            }
            if (saveDTO.getChronicDisease() != null) {
                existingProfile.setChronicDisease(saveDTO.getChronicDisease());
            }
            if (saveDTO.getCurrentSymptoms() != null) {
                existingProfile.setCurrentSymptoms(saveDTO.getCurrentSymptoms());
            }
            if (saveDTO.getCurrentPlan() != null) {
                existingProfile.setCurrentPlan(saveDTO.getCurrentPlan());
            }
            if (saveDTO.getNextStep() != null) {
                existingProfile.setNextStep(saveDTO.getNextStep());
            }
            if (saveDTO.getAddress() != null) {
                existingProfile.setAddress(jsonAddress);
            }
            if (saveDTO.getEmergencyName() != null) {
                existingProfile.setEmergencyName(saveDTO.getEmergencyName());
            }
            if (saveDTO.getEmergencyPhone() != null) {
                existingProfile.setEmergencyPhone(emergencyPhoneTrim);
            }
            existingProfile.setIdCardVerified((byte) 2);
            userProfilesService.updateById(existingProfile);
        }
        
        return ResultBean.success("提交成功，等待审核");
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
