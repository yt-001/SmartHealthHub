package com.xitian.smarthealthhub.service;

import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.domain.entity.UserProfiles;
import com.xitian.smarthealthhub.domain.query.PatientPageQuery;
import com.xitian.smarthealthhub.domain.vo.PatientPageVO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserProfilesService extends IService<UserProfiles> {
    PageBean<PatientPageVO> page(PageParam<PatientPageQuery> param);
}