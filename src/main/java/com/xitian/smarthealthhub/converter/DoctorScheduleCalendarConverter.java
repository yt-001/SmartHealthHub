package com.xitian.smarthealthhub.converter;

import com.xitian.smarthealthhub.domain.entity.DoctorSchedules;
import com.xitian.smarthealthhub.domain.entity.DoctorProfiles;
import com.xitian.smarthealthhub.domain.entity.Departments;
import com.xitian.smarthealthhub.domain.entity.Users;
import com.xitian.smarthealthhub.domain.vo.DoctorScheduleCalendarVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 医生排班转换器
 */
public class DoctorScheduleCalendarConverter {
    
    /**
     * DoctorSchedules Entity -> DoctorScheduleCalendarVO
     * @param entity 排班实体
     * @param doctorProfile 医生档案信息
     * @param department 科室信息
     * @param doctorUser 医生用户信息
     * @return 排班日历VO
     */
    public static DoctorScheduleCalendarVO toCalendarVO(DoctorSchedules entity, 
                                                       DoctorProfiles doctorProfile, 
                                                       Departments department, 
                                                       Users doctorUser) {
        if (entity == null) {
            return null;
        }

        DoctorScheduleCalendarVO vo = new DoctorScheduleCalendarVO();

        // BaseVO 字段映射
        vo.setId(entity.getId());
        vo.setCreatedAt(entity.getCreatedAt());
        vo.setUpdatedAt(entity.getUpdatedAt());

        // DoctorScheduleCalendarVO 字段映射
        vo.setScheduleDate(entity.getScheduleDate());
        vo.setShiftCode(entity.getShiftCode());

        // 关联信息映射
        if (doctorUser != null) {
            vo.setDoctorName(doctorUser.getRealName());
        }
        
        if (department != null) {
            vo.setDeptName(department.getName());
        }
        
        if (doctorProfile != null) {
            vo.setTitle(doctorProfile.getTitle());
        }

        return vo;
    }
}