package com.xitian.smarthealthhub.service;

import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.domain.dto.MedicinesDto;
import com.xitian.smarthealthhub.domain.query.MedicinesQuery;
import com.xitian.smarthealthhub.domain.vo.MedicinesVo;

/**
 * 药品服务接口
 * 
 * @author 
 * @date 2025/02/04
 */
public interface MedicinesService {
    /**
     * 分页查询
     * 
     * @param param 分页参数和查询条件
     * @return 分页结果
     */
    PageBean<MedicinesVo> pageQuery(PageParam<MedicinesQuery> param);

    /**
     * 根据ID查询
     * 
     * @param id ID
     * @return 实体
     */
    MedicinesVo getById(Long id);

    /**
     * 新增
     * 
     * @param dto DTO
     * @return ID
     */
    Long add(MedicinesDto dto);

    /**
     * 修改
     * 
     * @param dto DTO
     * @return 是否成功
     */
    Boolean update(MedicinesDto dto);

    /**
     * 删除
     * 
     * @param id ID
     * @return 是否成功
     */
    Boolean delete(Long id);
}