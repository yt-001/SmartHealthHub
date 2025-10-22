package com.xitian.smarthealthhub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xitian.smarthealthhub.domain.dto.DoctorScheduleCreateDTO;
import com.xitian.smarthealthhub.domain.entity.DoctorSchedules;
import com.xitian.smarthealthhub.domain.vo.DoctorDeptVO;
import com.xitian.smarthealthhub.domain.vo.DoctorScheduleCalendarVO;
import com.xitian.smarthealthhub.domain.query.DoctorScheduleCalendarQuery;

import java.time.LocalDate;
import java.util.List;

/**
 * 医生排班服务接口
 */
public interface DoctorSchedulesService extends IService<DoctorSchedules> {

    /**
     * 根据查询条件获取医生排班日历信息
     * @param query 查询条件
     * @return 日历视图数据列表
     */
    List<DoctorScheduleCalendarVO> getScheduleCalendar(DoctorScheduleCalendarQuery query);
    
    /**
     * 创建医生排班
     * @param scheduleCreateDTO 排班创建信息
     * @return 是否创建成功
     */
    boolean createSchedule(DoctorScheduleCreateDTO scheduleCreateDTO);
    
    /**
     * 获取医生科室信息列表，用于排班选择
     * @return 医生科室信息列表
     */
    List<DoctorDeptVO> listDoctorDeptInfo();
}