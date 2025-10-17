package com.xitian.smarthealthhub.converter;

import com.xitian.smarthealthhub.domain.entity.DoctorProfiles;
import com.xitian.smarthealthhub.domain.entity.Users;
import com.xitian.smarthealthhub.domain.vo.DoctorVerificationVO;
import com.xitian.smarthealthhub.util.DesensitizeUtil;

public class DoctorVerificationVOConverter {
    
    public static DoctorVerificationVO toVO(DoctorProfiles doctorProfiles, Users user) {
        DoctorVerificationVO vo = new DoctorVerificationVO();
        vo.setId(doctorProfiles.getId());
        vo.setDeptId(doctorProfiles.getDeptId());
        vo.setTitle(doctorProfiles.getTitle());
        vo.setQualificationNo(doctorProfiles.getQualificationNo());
        vo.setCreatedAt(doctorProfiles.getCreatedAt());
        
        if (user != null) {
            vo.setRealName(user.getRealName());
            vo.setPhone(DesensitizeUtil.phone(user.getPhone()));
        }
        
        return vo;
    }
}