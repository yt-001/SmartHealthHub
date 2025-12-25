package com.xitian.smarthealthhub.service;

import com.xitian.smarthealthhub.domain.dto.MedicineCategoryRelationsDto;
import com.xitian.smarthealthhub.domain.query.MedicineCategoryRelationsQuery;
import com.xitian.smarthealthhub.domain.vo.MedicineCategoryRelationsVo;
import com.xitian.smarthealthhub.bean.PageBean;

/**
 * 药品与分类的关联表Service接口
 */
public interface MedicineCategoryRelationsService {

    /**
     * 分页查询药品与分类的关联信息
     * @param query 查询条件
     * @return 分页结果
     */
    PageBean<MedicineCategoryRelationsVo> pageQuery(MedicineCategoryRelationsQuery query);

    /**
     * 根据ID查询药品与分类的关联信息
     * @param id 主键ID
     * @return 药品与分类的关联信息
     */
    MedicineCategoryRelationsVo getById(Long id);

    /**
     * 新增药品与分类的关联信息
     * @param dto 药品与分类的关联信息
     * @return 主键ID
     */
    Long add(MedicineCategoryRelationsDto dto);

    /**
     * 更新药品与分类的关联信息
     * @param dto 药品与分类的关联信息
     * @return 是否成功
     */
    Boolean update(MedicineCategoryRelationsDto dto);

    /**
     * 删除药品与分类的关联信息
     * @param id 主键ID
     * @return 是否成功
     */
    Boolean delete(Long id);
}