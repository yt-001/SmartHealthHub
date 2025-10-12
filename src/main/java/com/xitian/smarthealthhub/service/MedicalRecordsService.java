package com.xitian.smarthealthhub.service;

import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.domain.entity.MedicalRecords;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xitian.smarthealthhub.domain.query.MedicalRecordQuery;
import com.xitian.smarthealthhub.domain.vo.MedicalRecordPageVO;

public interface MedicalRecordsService extends IService<MedicalRecords> {
    PageBean<MedicalRecordPageVO> page(PageParam<MedicalRecordQuery> param);
}