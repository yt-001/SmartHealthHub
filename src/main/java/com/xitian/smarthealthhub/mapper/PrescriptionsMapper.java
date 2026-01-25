package com.xitian.smarthealthhub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xitian.smarthealthhub.domain.entity.Prescriptions;
import org.apache.ibatis.annotations.Mapper;

/**
 * 处方Mapper接口
 */
@Mapper
public interface PrescriptionsMapper extends BaseMapper<Prescriptions> {
}
