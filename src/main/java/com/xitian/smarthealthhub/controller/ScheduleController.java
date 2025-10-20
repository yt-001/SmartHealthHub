package com.xitian.smarthealthhub.controller;

import com.xitian.smarthealthhub.bean.ResultBean;
import com.xitian.smarthealthhub.domain.query.DoctorScheduleCalendarQuery;
import com.xitian.smarthealthhub.domain.vo.DoctorScheduleCalendarVO;
import com.xitian.smarthealthhub.service.DoctorSchedulesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

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
}