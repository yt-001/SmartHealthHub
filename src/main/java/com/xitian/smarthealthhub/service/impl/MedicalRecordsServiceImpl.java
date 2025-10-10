package com.xitian.smarthealthhub.service.impl;

import com.xitian.smarthealthhub.domain.entity.MedicalRecords;
import com.xitian.smarthealthhub.mapper.MedicalRecordsMapper;
import com.xitian.smarthealthhub.service.MedicalRecordsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class MedicalRecordsServiceImpl extends ServiceImpl<MedicalRecordsMapper, MedicalRecords> implements MedicalRecordsService {
}