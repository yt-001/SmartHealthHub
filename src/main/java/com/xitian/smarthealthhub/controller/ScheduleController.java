package com.xitian.smarthealthhub.controller;

import com.xitian.smarthealthhub.bean.ResultBean;
import com.xitian.smarthealthhub.bean.StatusCode;
import com.xitian.smarthealthhub.domain.dto.DoctorScheduleCreateDTO;
import com.xitian.smarthealthhub.domain.query.DoctorScheduleCalendarQuery;
import com.xitian.smarthealthhub.domain.vo.DoctorDeptVO;
import com.xitian.smarthealthhub.domain.vo.DoctorScheduleCalendarVO;
import com.xitian.smarthealthhub.service.DoctorSchedulesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import java.util.List;

/**
 * 医生排班控制器
 */
@Tag(name = "ScheduleController", description = "医生排班接口")
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    
    @Resource
    private DoctorSchedulesService doctorSchedulesService;

    /**
     * 获取指定日期范围的医生排班日历信息
     * @param query 日历查询条件
     * @return 日历视图数据
     */
    @Operation(summary = "获取医生排班日历信息")
    @PostMapping("/calendar")
    public ResultBean<List<DoctorScheduleCalendarVO>> getScheduleCalendar(@RequestBody DoctorScheduleCalendarQuery query) {
        List<DoctorScheduleCalendarVO> calendarData = doctorSchedulesService.getScheduleCalendar(query);
        return ResultBean.success(calendarData);
    }
    
    /**
     * 创建医生排班
     * @param scheduleCreateDTO 排班创建信息
     * @return 操作结果
     */
    @Operation(summary = "创建医生排班")
    @PostMapping("/create")
    public ResultBean<String> createSchedule(@Valid @RequestBody DoctorScheduleCreateDTO scheduleCreateDTO) {
        try {
            // 这里应该调用service层的方法来处理创建逻辑
            boolean result = doctorSchedulesService.createSchedule(scheduleCreateDTO);
            if (result) {
                return ResultBean.success("排班创建成功");
            } else {
                return ResultBean.fail(StatusCode.INTERNAL_SERVER_ERROR, "排班创建失败");
            }
        } catch (RuntimeException e) {
            return ResultBean.fail(StatusCode.BAD_REQUEST, e.getMessage());
        }
    }
    
    /**
     * 获取医生科室信息列表，用于排班选择
     * @return 医生科室信息列表
     */
    @Operation(summary = "获取医生科室信息列表")
    @GetMapping("/doctor-dept-list")
    public ResultBean<List<DoctorDeptVO>> listDoctorDeptInfo() {
        List<DoctorDeptVO> doctorDeptList = doctorSchedulesService.listDoctorDeptInfo();
        return ResultBean.success(doctorDeptList);
    }
}