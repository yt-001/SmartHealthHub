package com.xitian.smarthealthhub.controller;

import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.bean.ResultBean;
import com.xitian.smarthealthhub.bean.StatusCode;
import com.xitian.smarthealthhub.domain.dto.AppointmentCreateDTO;
import com.xitian.smarthealthhub.domain.query.AppointmentQuery;
import com.xitian.smarthealthhub.domain.vo.AppointmentDetailVO;
import com.xitian.smarthealthhub.domain.vo.AppointmentVO;
import com.xitian.smarthealthhub.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * 预约挂号控制器
 */
@Tag(name = "AppointmentController", description = "预约挂号接口")
@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    
    @Resource
    private AppointmentService appointmentService;
    
    /**
     * 分页获取所有预约挂号信息
     * @param param 分页参数
     * @return 预约挂号信息分页列表
     */
    @Operation(summary = "分页获取预约列表")
    @PostMapping("/page")
    public ResultBean<PageBean<AppointmentVO>> pageAppointments(@Valid @RequestBody PageParam<AppointmentQuery> param) {
        // 调用服务层进行分页查询
        PageBean<AppointmentVO> result = appointmentService.pageAppointments(param);
        return ResultBean.success(result);
    }
    
    /**
     * 根据ID获取预约挂号信息详情（包含医生排班详细信息）
     * @param id 预约挂号ID
     * @return 预约挂号详情信息
     */
    @Operation(summary = "根据ID获取预约详情")
    @GetMapping("/{id}")
    public ResultBean<AppointmentDetailVO> getAppointmentDetailById(@PathVariable Long id) {
        AppointmentDetailVO detailVO = appointmentService.getAppointmentDetailById(id);
        if (detailVO == null) {
            return ResultBean.fail(StatusCode.DATA_NOT_FOUND);
        }
        return ResultBean.success(detailVO);
    }
    
    /**
     * 根据患者ID获取所有预约挂号记录
     * @param patientId 患者ID
     * @param param 分页参数和查询条件
     * @return 预约挂号记录列表
     */
    @Operation(summary = "根据患者ID获取预约列表")
    @PostMapping("/patient/{patientId}/page")
    public ResultBean<PageBean<AppointmentVO>> getAppointmentsByPatientId(
            @PathVariable @NotNull(message = "患者ID不能为空") Long patientId,
            @Valid @RequestBody PageParam<AppointmentQuery> param) {
        // 确保查询条件中包含患者ID
        if (param.getQuery() == null) {
            param.setQuery(new AppointmentQuery());
        }
        param.getQuery().setPatientId(patientId);
        
        PageBean<AppointmentVO> result = appointmentService.pageAppointments(param);
        return ResultBean.success(result);
    }
    
    /**
     * 根据医生排班ID获取所有预约挂号记录
     * @param doctorId 医生ID
     * @param param 分页参数和查询条件
     * @return 预约挂号记录列表
     */
    @Operation(summary = "根据医生ID获取预约列表")
    @PostMapping("/doctor/{doctorId}/page")
    public ResultBean<PageBean<AppointmentVO>> getAppointmentsByDoctorId(
            @PathVariable @NotNull(message = "医生ID不能为空") Long doctorId,
            @Valid @RequestBody PageParam<AppointmentQuery> param) {
        // 确保查询条件中包含医生ID
        if (param.getQuery() == null) {
            param.setQuery(new AppointmentQuery());
        }
        param.getQuery().setDoctorId(doctorId);
        
        PageBean<AppointmentVO> result = appointmentService.pageAppointments(param);
        return ResultBean.success(result);
    }
    
    /**
     * 添加预约
     * @param appointmentCreateDTO 预约创建信息
     * @return 操作结果
     */
    @Operation(summary = "创建预约")
    @PostMapping("/create")
    public ResultBean<String> createAppointment(@Valid @RequestBody AppointmentCreateDTO appointmentCreateDTO) {
        boolean saved = appointmentService.createAppointment(appointmentCreateDTO);
        
        if (saved) {
            return ResultBean.success("预约创建成功");
        } else {
            return ResultBean.fail(StatusCode.INTERNAL_SERVER_ERROR, "预约创建失败");
        }
    }
    
    /**
     * 更新预约状态
     * @param id 预约ID
     * @param status 新状态
     * @return 操作结果
     */
    @PutMapping("/{id}/status")
    public ResultBean<String> updateAppointmentStatus(@PathVariable Long id, @RequestParam Byte status) {
        boolean updated = appointmentService.updateAppointmentStatus(id, status);
        if (updated) {
            return ResultBean.success("预约状态更新成功");
        } else {
            return ResultBean.fail(StatusCode.DATA_NOT_FOUND, "预约信息不存在或更新失败");
        }
    }
    
    /**
     * 删除预约
     * @param id 预约ID
     * @return 操作结果
     */
    @Operation(summary = "删除预约")
    @DeleteMapping("/delete/{id}")
    public ResultBean<String> deleteAppointment(@PathVariable Long id) {
        boolean removed = appointmentService.removeById(id);
        if (removed) {
            return ResultBean.success("预约删除成功");
        } else {
            return ResultBean.fail(StatusCode.DATA_NOT_FOUND, "预约信息不存在或删除失败");
        }
    }
}