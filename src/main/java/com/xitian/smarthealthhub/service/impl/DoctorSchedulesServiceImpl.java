package com.xitian.smarthealthhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xitian.smarthealthhub.converter.DoctorScheduleCalendarConverter;
import com.xitian.smarthealthhub.domain.entity.DoctorSchedules;
import com.xitian.smarthealthhub.domain.entity.DoctorProfiles;
import com.xitian.smarthealthhub.domain.entity.Departments;
import com.xitian.smarthealthhub.domain.entity.Users;
import com.xitian.smarthealthhub.domain.query.DoctorScheduleCalendarQuery;
import com.xitian.smarthealthhub.mapper.DoctorSchedulesMapper;
import com.xitian.smarthealthhub.mapper.DoctorProfilesMapper;
import com.xitian.smarthealthhub.mapper.DepartmentsMapper;
import com.xitian.smarthealthhub.mapper.UsersMapper;
import com.xitian.smarthealthhub.service.DoctorSchedulesService;
import com.xitian.smarthealthhub.domain.vo.DoctorScheduleCalendarVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 医生排班服务实现类
 */
@Service
public class DoctorSchedulesServiceImpl extends ServiceImpl<DoctorSchedulesMapper, DoctorSchedules> implements DoctorSchedulesService {
    
    @Autowired
    private DoctorProfilesMapper doctorProfilesMapper;
    
    @Autowired
    private DepartmentsMapper departmentsMapper;
    
    @Autowired
    private UsersMapper usersMapper;

    /**
     * 根据查询条件获取医生排班日历信息
     * @param query 查询条件
     * @return 日历视图数据列表
     */
    @Override
    public List<DoctorScheduleCalendarVO> getScheduleCalendar(DoctorScheduleCalendarQuery query) {
        // 构建查询条件
        LambdaQueryWrapper<DoctorSchedules> queryWrapper = Wrappers.lambdaQuery();
        
        // 日期范围查询
        if (query.getStartDate() != null) {
            queryWrapper.ge(DoctorSchedules::getScheduleDate, query.getStartDate());
        }
        if (query.getEndDate() != null) {
            queryWrapper.le(DoctorSchedules::getScheduleDate, query.getEndDate());
        }
        
        // 医生ID查询
        if (query.getDoctorId() != null) {
            queryWrapper.eq(DoctorSchedules::getDoctorId, query.getDoctorId());
        }
        
        // 科室ID查询
        if (query.getDeptId() != null) {
            queryWrapper.eq(DoctorSchedules::getDeptId, query.getDeptId());
        }
        
        // 只查询启用状态的排班
        queryWrapper.eq(DoctorSchedules::getStatus, (byte) 1);
        
        // 查询排班数据
        List<DoctorSchedules> scheduleList = this.list(queryWrapper);
        
        if (CollectionUtils.isEmpty(scheduleList)) {
            return List.of();
        }
        
        // 提取关联的医生ID和科室ID
        List<Long> doctorIds = scheduleList.stream()
                .map(DoctorSchedules::getDoctorId)
                .distinct()
                .collect(Collectors.toList());
        
        // 注意：科室ID在DoctorSchedules中是Integer类型，但在Departments实体中是Long类型
        List<Integer> deptIds = scheduleList.stream()
                .map(DoctorSchedules::getDeptId)
                .distinct()
                .collect(Collectors.toList());
        
        // 批量查询医生档案信息
        List<DoctorProfiles> doctorProfiles = doctorIds.isEmpty() ? List.of() : 
                doctorProfilesMapper.selectList(Wrappers.<DoctorProfiles>lambdaQuery()
                        .in(DoctorProfiles::getUserId, doctorIds));
        
        // 批量查询科室信息（需要类型转换）
        List<Departments> departments = deptIds.isEmpty() ? List.of() : 
                departmentsMapper.selectBatchIds(
                    deptIds.stream()
                        .map(Integer::longValue) // 转换Integer到Long
                        .collect(Collectors.toList())
                );
        
        // 批量查询医生用户信息
        List<Users> doctors = doctorIds.isEmpty() ? List.of() : 
                usersMapper.selectBatchIds(doctorIds);
        
        // 构建映射关系
        Map<Long, DoctorProfiles> doctorProfileMap = doctorProfiles.stream()
                .collect(Collectors.toMap(DoctorProfiles::getUserId, profile -> profile));
        
        // 注意：科室映射的键是Long类型
        Map<Long, Departments> departmentMap = departments.stream()
                .collect(Collectors.toMap(Departments::getId, dept -> dept));
        
        Map<Long, Users> doctorUserMap = doctors.stream()
                .collect(Collectors.toMap(Users::getId, user -> user));
        
        // 转换为VO对象
        return scheduleList.stream()
                .map(schedule -> {
                    DoctorProfiles doctorProfile = doctorProfileMap.get(schedule.getDoctorId());
                    // 注意：这里需要将Integer转换为Long来匹配departmentMap的键
                    Departments department = departmentMap.get(Long.valueOf(schedule.getDeptId()));
                    Users doctorUser = doctorUserMap.get(schedule.getDoctorId());
                    return DoctorScheduleCalendarConverter.toCalendarVO(schedule, doctorProfile, department, doctorUser);
                })
                .collect(Collectors.toList());
    }
}