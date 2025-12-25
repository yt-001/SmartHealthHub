package com.xitian.smarthealthhub.service;

import com.xitian.smarthealthhub.domain.dto.MedicinesDto;
import com.xitian.smarthealthhub.domain.query.MedicinesQuery;
import com.xitian.smarthealthhub.domain.vo.MedicinesVo;
import com.xitian.smarthealthhub.bean.PageBean;

/**
 * 药品信息主表Service接口
 */
public interface MedicinesService {

    /**
     * 分页查询药品信息
     * @param query 查询条件
     * @return 分页结果
     */
    PageBean<MedicinesVo> pageQuery(MedicinesQuery query);

    /**
     * 根据ID查询药品信息
     * @param id 主键ID
     * @return 药品信息
     */
    MedicinesVo getById(Long id);

    /**
     * 新增药品信息
     * @param dto 药品信息
     * @return 主键ID
     */
    Long add(MedicinesDto dto);

    /**
     * 更新药品信息
     * @param dto 药品信息
     * @return 是否成功
     */
    Boolean update(MedicinesDto dto);

    /**
     * 删除药品信息
     * @param id 主键ID
     * @return 是否成功
     */
    Boolean delete(Long id);
}