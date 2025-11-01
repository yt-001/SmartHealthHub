package com.xitian.smarthealthhub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xitian.smarthealthhub.domain.entity.HealthVideoComments;
import org.apache.ibatis.annotations.Mapper;

/**
 * 健康视频评论Mapper接口
 */
@Mapper
public interface HealthVideoCommentsMapper extends BaseMapper<HealthVideoComments> {
}