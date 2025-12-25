package com.xitian.smarthealthhub.service;

import com.xitian.smarthealthhub.domain.dto.MedicineCategoriesDto;
import com.xitian.smarthealthhub.domain.query.MedicineCategoriesQuery;
import com.xitian.smarthealthhub.domain.vo.MedicineCategoriesVo;
import com.xitian.smarthealthhub.bean.PageBean;

/**
 * 药品分类Service接口
 */
public interface MedicineCategoriesService {

    /**
     * 分页查询药品分类
     * @param query 查询条件
     * @return 分页结果
     */
    PageBean<MedicineCategoriesVo> pageQuery(MedicineCategoriesQuery query);

    /**
     * 根据ID查询药品分类
     * @param id 主键ID
     * @return 药品分类信息
     */
    MedicineCategoriesVo getById(Long id);

    /**
     * 新增药品分类
     * @param dto 药品分类信息
     * @return 主键ID
     */
    Long add(MedicineCategoriesDto dto);

    /**
     * 更新药品分类
     * @param dto 药品分类信息
     * @return 是否成功
     */
    Boolean update(MedicineCategoriesDto dto);

    /**
     * 删除药品分类
     * @param id 主键ID
     * @return 是否成功
     */
    Boolean delete(Long id);
}