package com.xitian.smarthealthhub.converter;

import com.xitian.smarthealthhub.domain.entity.UserProfiles;
import com.xitian.smarthealthhub.domain.vo.UserProfilesVO;
import com.xitian.smarthealthhub.util.DesensitizeUtil;

public class UserProfilesConverter {
    /**
     * UserProfiles + Users Entity -> UserProfilesVO
     */
    public static UserProfilesVO toVO(UserProfiles profile) {
        if (profile == null) {
            return null;
        }

        UserProfilesVO vo = new UserProfilesVO();

        // 从 UserProfiles 实体映射字段
        vo.setId(profile.getId());
        vo.setCreatedAt(profile.getCreatedAt());
        vo.setUpdatedAt(profile.getUpdatedAt());
        vo.setBloodType(profile.getBloodType());
        vo.setAllergyHistory(profile.getAllergyHistory());
        vo.setChronicDisease(profile.getChronicDisease());
        vo.setCurrentSymptoms(profile.getCurrentSymptoms());
        vo.setCurrentPlan(profile.getCurrentPlan());
        vo.setNextStep(profile.getNextStep());
        vo.setAddress(profile.getAddress());
        vo.setEmergencyName(profile.getEmergencyName());

        // 脱敏处理
        vo.setIdCard(DesensitizeUtil.idCard(profile.getIdCard()));
        vo.setEmergencyPhone(DesensitizeUtil.phone(profile.getEmergencyPhone()));

        return vo;
    }
}
