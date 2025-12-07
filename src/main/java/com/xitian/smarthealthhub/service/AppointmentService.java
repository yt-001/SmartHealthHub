package com.xitian.smarthealthhub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.domain.dto.AppointmentCreateDTO;
import com.xitian.smarthealthhub.domain.entity.Appointment;
import com.xitian.smarthealthhub.domain.query.AppointmentQuery;
import com.xitian.smarthealthhub.domain.vo.AppointmentDetailVO;
import com.xitian.smarthealthhub.domain.vo.AppointmentVO;

import java.util.List;

/**
 * 预约挂号服务接口
 */
public interface AppointmentService extends IService<Appointment> {
    
    /**
     * 分页查询预约信息
     * @param param 分页参数和查询条件
     * @return 预约信息分页结果
     */
    PageBean<AppointmentVO> pageAppointments(PageParam<AppointmentQuery> param);
    
    /**
     * 根据ID获取预约详情
     * @param id 预约ID
     * @return 预约详情VO
     */
    AppointmentDetailVO getAppointmentDetailById(Long id);
    
    /**
     * 创建预约
     * @param appointmentCreateDTO 预约创建信息
     * @return 是否创建成功
     */
    boolean createAppointment(AppointmentCreateDTO appointmentCreateDTO);
    
    /**
     * 更新预约状态
     * @param id 预约ID
     * @param status 新状态
     * @return 是否更新成功
     */
    boolean updateAppointmentStatus(Long id, Byte status);
}