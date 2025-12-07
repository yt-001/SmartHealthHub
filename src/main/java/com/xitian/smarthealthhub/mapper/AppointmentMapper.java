package com.xitian.smarthealthhub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xitian.smarthealthhub.domain.entity.Appointment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 预约挂号Mapper接口
 */
@Mapper
public interface AppointmentMapper extends BaseMapper<Appointment> {
}