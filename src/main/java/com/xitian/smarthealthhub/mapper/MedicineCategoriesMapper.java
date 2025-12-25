package com.xitian.smarthealthhub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xitian.smarthealthhub.domain.entity.MedicineCategoriesEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 药品分类Mapper接口
 */
@Mapper
public interface MedicineCategoriesMapper extends BaseMapper<MedicineCategoriesEntity> {
}