package com.xitian.smarthealthhub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xitian.smarthealthhub.domain.entity.DoctorSchedules;
import org.apache.ibatis.annotations.Mapper;

/**
 * 医生排班Mapper接口
 */
@Mapper
public interface DoctorSchedulesMapper extends BaseMapper<DoctorSchedules> {
}