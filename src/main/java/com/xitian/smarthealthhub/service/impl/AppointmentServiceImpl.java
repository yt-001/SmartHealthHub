package com.xitian.smarthealthhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.domain.dto.AppointmentCreateDTO;
import com.xitian.smarthealthhub.domain.entity.Appointment;
import com.xitian.smarthealthhub.domain.entity.Departments;
import com.xitian.smarthealthhub.domain.entity.DoctorProfiles;
import com.xitian.smarthealthhub.domain.entity.DoctorSchedules;
import com.xitian.smarthealthhub.domain.entity.Users;
import com.xitian.smarthealthhub.domain.query.AppointmentQuery;
import com.xitian.smarthealthhub.domain.vo.AppointmentDetailVO;
import com.xitian.smarthealthhub.domain.vo.AppointmentVO;
import com.xitian.smarthealthhub.domain.vo.AppointmentWithPatientVO;
import com.xitian.smarthealthhub.mapper.AppointmentMapper;
import com.xitian.smarthealthhub.service.AppointmentService;
import com.xitian.smarthealthhub.service.DepartmentsService;
import com.xitian.smarthealthhub.service.DoctorProfilesService;
import com.xitian.smarthealthhub.service.DoctorSchedulesService;
import com.xitian.smarthealthhub.service.UsersService;
import com.xitian.smarthealthhub.util.RegistrationNoGenerator;
import com.xitian.smarthealthhub.converter.AppointmentWithPatientConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 预约挂号服务实现类
 */
@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements AppointmentService {
    
    @Autowired
    private DoctorSchedulesService doctorSchedulesService;
    
    @Autowired
    private UsersService usersService;
    
    @Autowired
    private DoctorProfilesService doctorProfilesService;
    
    @Autowired
    private DepartmentsService departmentsService;
    
    @Override
    public PageBean<AppointmentVO> pageAppointments(PageParam<AppointmentQuery> param) {
        // 创建分页对象
        Page<Appointment> page = new Page<>(param.getPageNum(), param.getPageSize());
        
        // 构建查询条件
        LambdaQueryWrapper<Appointment> queryWrapper = Wrappers.lambdaQuery();
        
        // 获取查询条件
        AppointmentQuery query = param.getQuery();
        if (query != null) {
            // 根据医生ID查询（需要先查询医生排班表获取scheduleId）
            if (query.getDoctorId() != null) {
                List<DoctorSchedules> schedules = doctorSchedulesService.list(
                    new LambdaQueryWrapper<DoctorSchedules>()
                        .eq(DoctorSchedules::getDoctorId, query.getDoctorId())
                );
                if (schedules.isEmpty()) {
                    // 如果没有找到排班信息，则构造一个不可能的条件
                    queryWrapper.eq(Appointment::getId, -1);
                } else {
                    List<Long> scheduleIds = schedules.stream()
                            .map(DoctorSchedules::getId)
                            .collect(Collectors.toList());
                    queryWrapper.in(Appointment::getScheduleId, scheduleIds);
                }
            }
            
            // 根据患者ID查询
            if (query.getPatientId() != null) {
                queryWrapper.eq(Appointment::getPatientId, query.getPatientId());
            }
            
            // 根据状态查询
            if (query.getStatus() != null) {
                queryWrapper.eq(Appointment::getStatus, query.getStatus());
            }
            
            // 根据创建时间范围查询
            if (query.getStartTime() != null) {
                queryWrapper.ge(Appointment::getCreatedAt, query.getStartTime());
            }
            if (query.getEndTime() != null) {
                queryWrapper.le(Appointment::getCreatedAt, query.getEndTime());
            }
        }
        
        // 按创建时间倒序排列
        queryWrapper.orderByDesc(Appointment::getCreatedAt);
        
        // 执行分页查询
        Page<Appointment> resultPage = this.page(page, queryWrapper);
        
        // 转换为VO对象
        List<AppointmentVO> voList = resultPage.getRecords().stream()
                .map(this::convertToAppointmentVO)
                .collect(Collectors.toList());
        
        // 构造并返回PageBean
        return PageBean.of(voList, resultPage.getTotal(), param);
    }
    
    @Override
    public AppointmentDetailVO getAppointmentDetailById(Long id) {
        Appointment appointment = this.getById(id);
        if (appointment == null) {
            return null;
        }
        
        // 获取医生排班信息
        DoctorSchedules schedule = doctorSchedulesService.getById(appointment.getScheduleId());
        if (schedule == null) {
            return null;
        }
        
        // 获取医生用户信息
        Users doctorUser = usersService.getById(schedule.getDoctorId());
        if (doctorUser == null) {
            return null;
        }
        
        // 获取医生档案信息
        DoctorProfiles doctorProfile = doctorProfilesService.getByUserId(schedule.getDoctorId());
        
        // 获取科室信息
        Departments department = null;
        if (schedule.getDeptId() != null) {
            department = departmentsService.getById(schedule.getDeptId());
        }
        
        // 构造AppointmentDetailVO对象
        AppointmentDetailVO detailVO = new AppointmentDetailVO();
        
        // BaseVO 字段映射
        detailVO.setId(appointment.getId());
        detailVO.setCreatedAt(appointment.getCreatedAt());
        detailVO.setUpdatedAt(appointment.getUpdatedAt());
        
        // Appointment 字段映射
        detailVO.setStatus(appointment.getStatus());
        detailVO.setDescription(appointment.getDescription());
        detailVO.setRegistrationNo(appointment.getRegistrationNo());
        
        // 医生排班信息映射
        detailVO.setScheduleId(schedule.getId());
        detailVO.setScheduleDate(schedule.getScheduleDate());
        detailVO.setShiftCode(schedule.getShiftCode());
        detailVO.setRoomNo(schedule.getRoomNo());
        detailVO.setMaxAppoint(schedule.getMaxAppoint());
        detailVO.setUsedAppoint(schedule.getUsedAppoint());
        
        // 医生相关信息映射
        if (doctorUser != null) {
            detailVO.setDoctorName(doctorUser.getRealName());
        }
        
        if (department != null) {
            detailVO.setDeptName(department.getName());
        }
        
        if (doctorProfile != null) {
            detailVO.setDoctorTitle(doctorProfile.getTitle());
            detailVO.setSpecialty(doctorProfile.getSpecialty());
        }
        
        return detailVO;
    }
    
    @Override
    public boolean createAppointment(AppointmentCreateDTO appointmentCreateDTO) {
        Appointment appointment = new Appointment();
        appointment.setScheduleId(appointmentCreateDTO.getScheduleId());
        appointment.setPatientId(appointmentCreateDTO.getPatientId());
        appointment.setDescription(appointmentCreateDTO.getDescription());
        // 自动生成预约号
        appointment.setRegistrationNo(RegistrationNoGenerator.generateRegistrationNo());
        // 默认状态为待确认
        appointment.setStatus((byte) 0);
        // 设置创建时间和更新时间
        appointment.setCreatedAt(LocalDateTime.now());
        appointment.setUpdatedAt(LocalDateTime.now());
        
        return this.save(appointment);
    }
    
    @Override
    public boolean updateAppointmentStatus(Long id, Byte status) {
        Appointment appointment = this.getById(id);
        if (appointment == null) {
            return false;
        }
        
        appointment.setStatus(status);
        appointment.setUpdatedAt(LocalDateTime.now());
        
        return this.updateById(appointment);
    }
    
    @Override
    public PageBean<AppointmentWithPatientVO> pageAppointmentsWithPatientInfo(PageParam<AppointmentQuery> param) {
        // 创建分页对象
        Page<Appointment> page = new Page<>(param.getPageNum(), param.getPageSize());
        
        // 构建查询条件
        LambdaQueryWrapper<Appointment> queryWrapper = Wrappers.lambdaQuery();
        
        // 获取查询条件
        AppointmentQuery query = param.getQuery();
        if (query != null) {
            // 根据医生ID查询（需要先查询医生排班表获取scheduleId）
            if (query.getDoctorId() != null) {
                List<DoctorSchedules> schedules = doctorSchedulesService.list(
                    new LambdaQueryWrapper<DoctorSchedules>()
                        .eq(DoctorSchedules::getDoctorId, query.getDoctorId())
                );
                if (schedules.isEmpty()) {
                    // 如果没有找到排班信息，则构造一个不可能的条件
                    queryWrapper.eq(Appointment::getId, -1);
                } else {
                    List<Long> scheduleIds = schedules.stream()
                            .map(DoctorSchedules::getId)
                            .collect(Collectors.toList());
                    queryWrapper.in(Appointment::getScheduleId, scheduleIds);
                }
            }
            
            // 根据患者ID查询
            if (query.getPatientId() != null) {
                queryWrapper.eq(Appointment::getPatientId, query.getPatientId());
            }
            
            // 根据状态查询
            if (query.getStatus() != null) {
                queryWrapper.eq(Appointment::getStatus, query.getStatus());
            }
            
            // 根据创建时间范围查询
            if (query.getStartTime() != null) {
                queryWrapper.ge(Appointment::getCreatedAt, query.getStartTime());
            }
            if (query.getEndTime() != null) {
                queryWrapper.le(Appointment::getCreatedAt, query.getEndTime());
            }
        }
        
        // 按创建时间倒序排列
        queryWrapper.orderByDesc(Appointment::getCreatedAt);
        
        // 执行分页查询
        Page<Appointment> resultPage = this.page(page, queryWrapper);
        
        // 转换为带患者信息的VO对象
        List<AppointmentWithPatientVO> voList = resultPage.getRecords().stream()
                .map(appointment -> {
                    Users patientUser = usersService.getById(appointment.getPatientId());
                    return AppointmentWithPatientConverter.toVO(appointment, patientUser);
                })
                .collect(Collectors.toList());
        
        // 构造并返回PageBean
        return PageBean.of(voList, resultPage.getTotal(), (PageParam) param);
    }
    
    /**
     * 将Appointment实体转换为AppointmentVO
     * @param appointment Appointment实体
     * @return AppointmentVO对象
     */
    private AppointmentVO convertToAppointmentVO(Appointment appointment) {
        AppointmentVO vo = new AppointmentVO();
        
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
        
        return vo;
    }
}