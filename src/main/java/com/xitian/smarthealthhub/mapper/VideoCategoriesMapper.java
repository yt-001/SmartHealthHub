package com.xitian.smarthealthhub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xitian.smarthealthhub.domain.entity.VideoCategories;
import org.apache.ibatis.annotations.Mapper;

/**
 * 视频分类Mapper接口
 */
@Mapper
public interface VideoCategoriesMapper extends BaseMapper<VideoCategories> {
}