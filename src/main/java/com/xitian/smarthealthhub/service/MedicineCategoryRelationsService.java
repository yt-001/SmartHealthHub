package com.xitian.smarthealthhub.service;

import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.domain.dto.MedicineCategoryRelationsDto;
import com.xitian.smarthealthhub.domain.query.MedicineCategoryRelationsQuery;
import com.xitian.smarthealthhub.domain.vo.MedicineCategoryRelationsVo;

import java.util.List;

/**
 * 药品分类关联服务接口
 * 
 * @author 
 * @date 2025/02/04
 */
public interface MedicineCategoryRelationsService {
    /**
     * 分页查询
     * 
     * @param param 分页参数和查询条件
     * @return 分页结果
     */
    PageBean<MedicineCategoryRelationsVo> pageQuery(PageParam<MedicineCategoryRelationsQuery> param);

    /**
     * 根据ID查询
     * 
     * @param id ID
     * @return 实体
     */
    MedicineCategoryRelationsVo get(Long id);

    /**
     * 新增
     * 
     * @param dto DTO
     * @return ID
     */
    Long add(MedicineCategoryRelationsDto dto);

    /**
     * 修改
     * 
     * @param dto DTO
     * @return 是否成功
     */
    Boolean update(MedicineCategoryRelationsDto dto);

    /**
     * 删除
     * 
     * @param id ID
     * @return 是否成功
     */
    Boolean delete(Long id);
    
    /**
     * 根据分类ID获取药品ID列表
     * 
     * @param categoryId 分类ID
     * @return 药品ID列表
     */
    List<Long> getMedicineIdsByCategoryId(Long categoryId);
    
    /**
     * 根据分类ID列表获取药品ID列表
     * 
     * @param categoryIds 分类ID列表
     * @return 药品ID列表
     */
    List<Long> getMedicineIdsByCategoryIds(List<Long> categoryIds);
    
    /**
     * 根据药品ID获取分类ID列表
     * 
     * @param medicineId 药品ID
     * @return 分类ID列表
     */
    List<Long> getCategoryIdsByMedicineId(Long medicineId);
}