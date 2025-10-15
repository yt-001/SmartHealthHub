package com.xitian.smarthealthhub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xitian.smarthealthhub.domain.entity.Departments;
import com.xitian.smarthealthhub.mapper.DepartmentsMapper;
import com.xitian.smarthealthhub.service.DepartmentsService;
import org.springframework.stereotype.Service;

/**
 * 科室Service实现类
 */
@Service
public class DepartmentsServiceImpl extends ServiceImpl<DepartmentsMapper, Departments> implements DepartmentsService {
}