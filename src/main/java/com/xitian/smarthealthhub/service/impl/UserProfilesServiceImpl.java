package com.xitian.smarthealthhub.service.impl;

import com.xitian.smarthealthhub.domain.entity.UserProfiles;
import com.xitian.smarthealthhub.mapper.UserProfilesMapper;
import com.xitian.smarthealthhub.service.UserProfilesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserProfilesServiceImpl extends ServiceImpl<UserProfilesMapper, UserProfiles> implements UserProfilesService {
}
