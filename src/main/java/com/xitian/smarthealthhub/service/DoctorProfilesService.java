package com.xitian.smarthealthhub.service;

import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.domain.entity.DoctorProfiles;
import com.xitian.smarthealthhub.domain.query.DoctorPageQuery;
import com.xitian.smarthealthhub.domain.vo.DoctorPageVO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DoctorProfilesService extends IService<DoctorProfiles> {
    PageBean<DoctorPageVO> page(PageParam<DoctorPageQuery> param);
}