package com.xitian.smarthealthhub.service.impl;

import com.xitian.smarthealthhub.domain.entity.DoctorProfiles;
import com.xitian.smarthealthhub.mapper.DoctorProfilesMapper;
import com.xitian.smarthealthhub.service.DoctorProfilesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DoctorProfilesServiceImpl extends ServiceImpl<DoctorProfilesMapper, DoctorProfiles> implements DoctorProfilesService {
}
