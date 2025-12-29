package com.xitian.smarthealthhub.service;

import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.domain.dto.MedicineRecommendationLevelsDto;
import com.xitian.smarthealthhub.domain.query.MedicineRecommendationLevelsQuery;
import com.xitian.smarthealthhub.domain.vo.MedicineRecommendationLevelsVo;

/**
 * 药品推荐级别服务接口
 */
public interface MedicineRecommendationLevelsService {
    /**
     * 分页查询
     */
    PageBean<MedicineRecommendationLevelsVo> pageQuery(PageParam<MedicineRecommendationLevelsQuery> param);

    /**
     * 根据ID查询
     */
    MedicineRecommendationLevelsVo getById(Long id);

    /**
     * 新增
     */
    Long add(MedicineRecommendationLevelsDto dto);

    /**
     * 修改
     */
    Boolean update(MedicineRecommendationLevelsDto dto);

    /**
     * 删除
     */
    Boolean delete(Long id);
}

