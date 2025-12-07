package com.xitian.smarthealthhub.converter;

import com.xitian.smarthealthhub.domain.entity.Appointment;
import com.xitian.smarthealthhub.domain.entity.Departments;
import com.xitian.smarthealthhub.domain.entity.DoctorProfiles;
import com.xitian.smarthealthhub.domain.entity.DoctorSchedules;
import com.xitian.smarthealthhub.domain.entity.Users;
import com.xitian.smarthealthhub.domain.vo.AppointmentDetailVO;

/**
 * 预约挂号详情转换器
 */
public class AppointmentDetailConverter {

    /**
     * Appointment Entity + 关联信息 -> AppointmentDetailVO
     *
     * @param appointment    预约实体
     * @param schedule       医生排班实体
     * @param doctorUser     医生用户实体
     * @param doctorProfile  医生档案实体
     * @param department     科室实体
     * @return AppointmentDetailVO
     */
    public static AppointmentDetailVO toDetailVO(
            Appointment appointment,
            DoctorSchedules schedule,
            Users doctorUser,
            DoctorProfiles doctorProfile,
            Departments department) {

        if (appointment == null || schedule == null || doctorUser == null) {
            return null;
        }

        AppointmentDetailVO vo = new AppointmentDetailVO();

        // BaseVO 字段映射
        vo.setId(appointment.getId());
        vo.setCreatedAt(appointment.getCreatedAt());
        vo.setUpdatedAt(appointment.getUpdatedAt());

        // Appointment 字段映射
        vo.setStatus(appointment.getStatus());
        vo.setDescription(appointment.getDescription());
        vo.setRegistrationNo(appointment.getRegistrationNo());

        // 医生排班信息映射
        vo.setScheduleId(schedule.getId());
        vo.setScheduleDate(schedule.getScheduleDate());
        vo.setShiftCode(schedule.getShiftCode());
        vo.setRoomNo(schedule.getRoomNo());
        vo.setMaxAppoint(schedule.getMaxAppoint());
        vo.setUsedAppoint(schedule.getUsedAppoint());

        // 医生相关信息映射
        if (doctorUser != null) {
            vo.setDoctorName(doctorUser.getRealName());
        }

        if (department != null) {
            vo.setDeptName(department.getName());
        }

        if (doctorProfile != null) {
            vo.setDoctorTitle(doctorProfile.getTitle());
            vo.setSpecialty(doctorProfile.getSpecialty());
        }

        return vo;
    }
}