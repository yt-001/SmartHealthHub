package com.xitian.smarthealthhub.service;

import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.domain.dto.MedicineCategoriesDto;
import com.xitian.smarthealthhub.domain.query.MedicineCategoriesQuery;
import com.xitian.smarthealthhub.domain.vo.MedicineCategoriesVo;

import java.util.List;

/**
 * 药品分类服务接口
 * 
 * @author 
 * @date 2025/02/04
 */
public interface MedicineCategoriesService {
    /**
     * 分页查询
     * 
     * @param param 分页参数和查询条件
     * @return 分页结果
     */
    PageBean<MedicineCategoriesVo> pageQuery(PageParam<MedicineCategoriesQuery> param);

    /**
     * 根据ID查询
     * 
     * @param id ID
     * @return 实体
     */
    MedicineCategoriesVo getById(Long id);

    /**
     * 新增
     * 
     * @param dto DTO
     * @return ID
     */
    Long add(MedicineCategoriesDto dto);

    /**
     * 修改
     * 
     * @param dto DTO
     * @return 是否成功
     */
    Boolean update(MedicineCategoriesDto dto);

    /**
     * 删除
     * 
     * @param id ID
     * @return 是否成功
     */
    Boolean delete(Long id);
    
    /**
     * 根据父分类ID获取子分类ID列表
     * 
     * @param parentId 父分类ID
     * @return 子分类ID列表
     */
    List<Long> getSubCategoryIdsByParentId(Long parentId);

    /**
     * 获取所有一级分类（大类）列表
     *
     * @return 大类分类列表
     */
    List<MedicineCategoriesVo> listBigCategories();

    /**
     * 根据父分类ID获取子分类列表（小类）
     *
     * @param parentId 父分类ID
     * @return 子分类列表
     */
    List<MedicineCategoriesVo> listSubCategoriesByParentId(Long parentId);
}
