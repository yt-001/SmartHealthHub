package com.xitian.smarthealthhub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xitian.smarthealthhub.domain.entity.HealthArticles;
import org.apache.ibatis.annotations.Mapper;

/**
 * 健康文章数据访问层接口
 */
@Mapper
public interface HealthArticlesMapper extends BaseMapper<HealthArticles> {
}