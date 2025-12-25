package com.xitian.smarthealthhub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xitian.smarthealthhub.domain.entity.MedicinesEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 药品信息主表Mapper接口
 */
@Mapper
public interface MedicinesMapper extends BaseMapper<MedicinesEntity> {
}