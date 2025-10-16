package com.xitian.smarthealthhub.converter;

import com.xitian.smarthealthhub.domain.entity.DoctorProfiles;
import com.xitian.smarthealthhub.domain.entity.Users;
import com.xitian.smarthealthhub.domain.vo.DoctorPageVO;
import com.xitian.smarthealthhub.util.DesensitizeUtil;

public class DoctorPageVOConverter {
    
    public static DoctorPageVO toVO(DoctorProfiles doctorProfiles, Users user) {
        DoctorPageVO vo = new DoctorPageVO();
        vo.setId(doctorProfiles.getId());
        vo.setDeptId(doctorProfiles.getDeptId());
        vo.setTitle(doctorProfiles.getTitle());
        vo.setQualificationNo(doctorProfiles.getQualificationNo());
        vo.setCreatedAt(doctorProfiles.getCreatedAt());
        
        if (user != null) {
            vo.setUsername(user.getUsername());
            vo.setRealName(user.getRealName());
            vo.setPhone(DesensitizeUtil.phone(user.getPhone()));
            vo.setEmail(DesensitizeUtil.email(user.getEmail()));
            vo.setStatus(user.getStatus());
        }
        
        return vo;
    }
}