package com.xitian.smarthealthhub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.domain.entity.DoctorProfiles;
import com.xitian.smarthealthhub.domain.query.DoctorPageQuery;
import com.xitian.smarthealthhub.domain.vo.DoctorPageVO;

public interface DoctorProfilesService extends IService<DoctorProfiles> {
    /**
     * 分页查询医生信息
     * @param param 分页参数
     * @return 医生信息分页列表
     */
    PageBean<DoctorPageVO> page(PageParam<DoctorPageQuery> param);
}