package com.xitian.smarthealthhub.converter;

import com.xitian.smarthealthhub.domain.entity.Users;
import com.xitian.smarthealthhub.domain.entity.UserProfiles;
import com.xitian.smarthealthhub.domain.vo.PatientVerificationVO;
import com.xitian.smarthealthhub.util.DesensitizeUtil;

public class PatientVerificationVOConverter {
    /**
     * Users + UserProfiles Entity -> PatientVerificationVO
     */
    public static PatientVerificationVO toVO(Users user, UserProfiles profile) {
        if (user == null) {
            return null;
        }

        PatientVerificationVO vo = new PatientVerificationVO();

        // 从 Users 实体映射字段到 PatientVerificationVO
        vo.setId(user.getId());
        vo.setRealName(user.getRealName());
        vo.setPhone(DesensitizeUtil.phone(user.getPhone()));
        vo.setCreatedAt(user.getCreatedAt());

        // 从 UserProfiles 实体映射字段到 PatientVerificationVO（如果存在）
        if (profile != null) {
            vo.setIdCard(DesensitizeUtil.idCard(profile.getIdCard()));
        }

        return vo;
    }
}