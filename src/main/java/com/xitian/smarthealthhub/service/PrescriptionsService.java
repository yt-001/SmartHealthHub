package com.xitian.smarthealthhub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.domain.entity.Prescriptions;

/**
 * 处方服务接口
 */
public interface PrescriptionsService extends IService<Prescriptions> {
    
    /**
     * 分页查询处方列表
     * @param pageParam 分页参数
     * @return 分页结果
     */
    PageBean<Prescriptions> pageQuery(PageParam<Prescriptions> pageParam);
}
