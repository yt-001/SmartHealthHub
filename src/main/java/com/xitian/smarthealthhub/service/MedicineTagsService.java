package com.xitian.smarthealthhub.service;

import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.domain.dto.MedicineTagsDto;
import com.xitian.smarthealthhub.domain.query.MedicineTagsQuery;
import com.xitian.smarthealthhub.domain.vo.MedicineTagsVo;

/**
 * 药品标签服务接口
 */
public interface MedicineTagsService {
    /**
     * 分页查询
     */
    PageBean<MedicineTagsVo> pageQuery(PageParam<MedicineTagsQuery> param);

    /**
     * 根据ID查询
     */
    MedicineTagsVo getById(Long id);

    /**
     * 新增
     */
    Long add(MedicineTagsDto dto);

    /**
     * 修改
     */
    Boolean update(MedicineTagsDto dto);

    /**
     * 删除
     */
    Boolean delete(Long id);
}

