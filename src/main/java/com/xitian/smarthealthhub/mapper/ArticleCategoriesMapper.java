package com.xitian.smarthealthhub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xitian.smarthealthhub.domain.entity.ArticleCategories;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章分类Mapper接口
 */
@Mapper
public interface ArticleCategoriesMapper extends BaseMapper<ArticleCategories> {
}