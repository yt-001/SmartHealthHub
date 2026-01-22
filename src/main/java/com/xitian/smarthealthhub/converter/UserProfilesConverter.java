package com.xitian.smarthealthhub.converter;

import com.xitian.smarthealthhub.domain.entity.UserProfiles;
import com.xitian.smarthealthhub.domain.vo.UserProfilesVO;
import com.xitian.smarthealthhub.util.DesensitizeUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

public class UserProfilesConverter {
    
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

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
        
        // 处理地址字段（可能是JSON格式）
        String rawAddress = profile.getAddress();
        if (StringUtils.hasText(rawAddress) && rawAddress.trim().startsWith("{")) {
            try {
                JsonNode node = OBJECT_MAPPER.readTree(rawAddress);
                if (node.has("detail")) {
                    vo.setAddress(node.get("detail").asText());
                } else {
                    vo.setAddress(rawAddress);
                }
            } catch (Exception e) {
                vo.setAddress(rawAddress);
            }
        } else {
            vo.setAddress(rawAddress);
        }
        
        vo.setEmergencyName(profile.getEmergencyName());

        // 脱敏处理
        vo.setIdCard(DesensitizeUtil.idCard(profile.getIdCard()));
        vo.setEmergencyPhone(DesensitizeUtil.phone(profile.getEmergencyPhone()));

        return vo;
    }
}
