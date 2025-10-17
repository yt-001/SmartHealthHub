package com.xitian.smarthealthhub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.domain.entity.UserProfiles;
import com.xitian.smarthealthhub.domain.query.PatientPageQuery;
import com.xitian.smarthealthhub.domain.query.PatientVerificationQuery;
import com.xitian.smarthealthhub.domain.vo.PatientPageVO;
import com.xitian.smarthealthhub.domain.vo.PatientVerificationVO;

public interface UserProfilesService extends IService<UserProfiles> {
    
    /**
     * 分页获取待审核的患者列表
     * @param param 分页参数和查询条件
     * @return 患者信息分页列表
     */
    PageBean<PatientVerificationVO> pagePendingPatients(PageParam<PatientVerificationQuery> param);

    /**
     * 分页获取患者列表
     * @param param 分页参数和查询条件
     * @return 患者信息分页列表
     */
    PageBean<PatientPageVO> page(PageParam<PatientPageQuery> param);
}