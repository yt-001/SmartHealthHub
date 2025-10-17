package com.xitian.smarthealthhub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.domain.entity.DoctorProfiles;
import com.xitian.smarthealthhub.domain.query.DoctorPageQuery;
import com.xitian.smarthealthhub.domain.query.DoctorVerificationQuery;
import com.xitian.smarthealthhub.domain.vo.DoctorPageVO;
import com.xitian.smarthealthhub.domain.vo.DoctorProfilesVO;
import com.xitian.smarthealthhub.domain.vo.DoctorVerificationVO;

public interface DoctorProfilesService extends IService<DoctorProfiles> {
    
    /**
     * 分页获取待审核的医生列表
     * @param param 分页参数和查询条件
     * @return 医生信息分页列表
     */
    PageBean<DoctorVerificationVO> pagePendingDoctors(PageParam<DoctorVerificationQuery> param);

    /**
     * 分页获取医生列表
     * @param param 分页参数和查询条件
     * @return 医生信息分页列表
     */
    PageBean<DoctorPageVO> page(PageParam<DoctorPageQuery> param);
}