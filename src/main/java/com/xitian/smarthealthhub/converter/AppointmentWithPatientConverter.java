package com.xitian.smarthealthhub.converter;

import com.xitian.smarthealthhub.domain.entity.Appointment;
import com.xitian.smarthealthhub.domain.entity.Users;
import com.xitian.smarthealthhub.domain.vo.AppointmentWithPatientVO;

/**
 * 包含患者信息的预约挂号转换器
 */
public class AppointmentWithPatientConverter {
    
    /**
     * Appointment Entity + Users Entity -> AppointmentWithPatientVO
     * @param appointment Appointment实体
     * @param patientUser 患者用户实体
     * @return AppointmentWithPatientVO
     */
    public static AppointmentWithPatientVO toVO(Appointment appointment, Users patientUser) {
        if (appointment == null) {
            return null;
        }
        
        AppointmentWithPatientVO vo = new AppointmentWithPatientVO();
        
        // BaseVO 字段映射
        vo.setId(appointment.getId());
        vo.setCreatedAt(appointment.getCreatedAt());
        vo.setUpdatedAt(appointment.getUpdatedAt());
        
        // AppointmentVO 字段映射
        vo.setScheduleId(appointment.getScheduleId());
        vo.setPatientId(appointment.getPatientId());
        vo.setStatus(appointment.getStatus());
        vo.setDescription(appointment.getDescription());
        vo.setRegistrationNo(appointment.getRegistrationNo());
        
        // 患者信息字段映射
        if (patientUser != null) {
            vo.setPatientName(patientUser.getRealName());
            vo.setPatientBirthDate(patientUser.getBirthDate());
            vo.setPatientGender(patientUser.getGender());
            vo.setPatientPhone(patientUser.getPhone());
        }
        
        return vo;
    }
}