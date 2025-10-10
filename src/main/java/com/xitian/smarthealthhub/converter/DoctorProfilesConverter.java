package com.xitian.smarthealthhub.converter;

import com.xitian.smarthealthhub.domain.entity.DoctorProfiles;
import com.xitian.smarthealthhub.domain.entity.Users;
import com.xitian.smarthealthhub.domain.vo.DoctorProfilesVO;
import com.xitian.smarthealthhub.utils.DesensitizeUtil;

public class DoctorProfilesConverter {
    /**
     * DoctorProfiles + Users Entity -> DoctorProfilesVO
     */
    public static DoctorProfilesVO toVO(DoctorProfiles profile) {
        if (profile == null) {
            return null;
        }

        DoctorProfilesVO vo = new DoctorProfilesVO();

        // 从 DoctorProfiles 实体映射字段到 DoctorProfilesVO
        vo.setId(profile.getId());
        vo.setCreateTime(profile.getCreatedAt());
        vo.setUpdateTime(profile.getUpdatedAt());
        vo.setDeptId(profile.getDeptId());
        vo.setTitle(profile.getTitle());
        vo.setSpecialty(profile.getSpecialty());
        vo.setBio(profile.getBio());
        vo.setWorkShift(profile.getWorkShift());
        vo.setOfficeRoom(profile.getOfficeRoom());

        // 脱敏字段
        vo.setQualificationNo(DesensitizeUtil.qualificationNo(profile.getQualificationNo()));

        return vo;
    }
}
