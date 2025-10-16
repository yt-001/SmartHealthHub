package com.xitian.smarthealthhub.converter;

import com.xitian.smarthealthhub.domain.entity.Users;
import com.xitian.smarthealthhub.domain.entity.UserProfiles;
import com.xitian.smarthealthhub.domain.vo.PatientPageVO;
import com.xitian.smarthealthhub.util.DesensitizeUtil;

public class PatientPageVOConverter {
    /**
     * Users + UserProfiles Entity -> PatientPageVO
     */
    public static PatientPageVO toPageVO(Users user, UserProfiles profile) {
        if (user == null) {
            return null;
        }

        PatientPageVO vo = new PatientPageVO();

        // 从 Users 实体映射字段到 PatientPageVO
        vo.setId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setRealName(user.getRealName());
        vo.setPhone(DesensitizeUtil.phone(user.getPhone()));
        vo.setGender(user.getGender());
        vo.setBirthDate(user.getBirthDate());
        vo.setStatus(user.getStatus());
        vo.setCreatedAt(user.getCreatedAt());

        // 从 UserProfiles 实体映射字段到 PatientPageVO（如果存在）
        if (profile != null) {
            vo.setIdCard(DesensitizeUtil.idCard(profile.getIdCard()));
            vo.setBloodType(profile.getBloodType());
        }

        return vo;
    }
}