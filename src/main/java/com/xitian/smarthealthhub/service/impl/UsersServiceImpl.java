package com.xitian.smarthealthhub.service.impl;

import com.xitian.smarthealthhub.domain.entity.Users;
import com.xitian.smarthealthhub.mapper.UsersMapper;
import com.xitian.smarthealthhub.service.UsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {
}
