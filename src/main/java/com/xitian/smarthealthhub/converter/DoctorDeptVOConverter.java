package com.xitian.smarthealthhub.converter;

import com.xitian.smarthealthhub.domain.entity.DoctorProfiles;
import com.xitian.smarthealthhub.domain.entity.Departments;
import com.xitian.smarthealthhub.domain.entity.Users;
import com.xitian.smarthealthhub.domain.vo.DoctorDeptVO;

/**
 * 医生科室信息转换器
 */
public class DoctorDeptVOConverter {
    
    /**
     * 医生档案、用户和科室信息 -> DoctorDeptVO
     * @param profile 医生档案信息
     * @param user 医生用户信息
     * @param department 科室信息
     * @return DoctorDeptVO对象
     */
    public static DoctorDeptVO toVO(DoctorProfiles profile, Users user, Departments department) {
        if (profile == null) {
            return null;
        }
        
        DoctorDeptVO vo = new DoctorDeptVO();
        vo.setDoctorId(profile.getUserId());
        vo.setDeptId(profile.getDeptId());
        
        if (user != null) {
            vo.setDoctorName(user.getRealName());
        }
        
        if (department != null) {
            vo.setDeptName(department.getName());
        }
        
        vo.setRoomNo(profile.getOfficeRoom());
        
        return vo;
    }
}