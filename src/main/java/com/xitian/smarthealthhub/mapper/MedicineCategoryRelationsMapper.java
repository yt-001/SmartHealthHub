package com.xitian.smarthealthhub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xitian.smarthealthhub.domain.entity.MedicineCategoryRelationsEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 药品与分类的关联表Mapper接口
 */
@Mapper
public interface MedicineCategoryRelationsMapper extends BaseMapper<MedicineCategoryRelationsEntity> {
}