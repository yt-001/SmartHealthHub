package com.xitian.smarthealthhub.converter;

import com.xitian.smarthealthhub.domain.entity.Appointment;
import com.xitian.smarthealthhub.domain.vo.AppointmentVO;

/**
 * 预约挂号转换器
 */
public class AppointmentConverter {
    
    /**
     * Appointment Entity -> AppointmentVO
     * @param entity Appointment实体
     * @return AppointmentVO
     */
    public static AppointmentVO toVO(Appointment entity) {
        if (entity == null) {
            return null;
        }
        
        AppointmentVO vo = new AppointmentVO();
        
        // BaseVO 字段映射
        vo.setId(entity.getId());
        vo.setCreatedAt(entity.getCreatedAt());
        vo.setUpdatedAt(entity.getUpdatedAt());
        
        // AppointmentVO 字段映射
        vo.setScheduleId(entity.getScheduleId());
        vo.setPatientId(entity.getPatientId());
        vo.setStatus(entity.getStatus());
        vo.setDescription(entity.getDescription());
        vo.setRegistrationNo(entity.getRegistrationNo());
        
        return vo;
    }
}