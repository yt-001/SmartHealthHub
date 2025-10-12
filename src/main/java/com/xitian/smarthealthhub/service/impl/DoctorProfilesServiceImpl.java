package com.xitian.smarthealthhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.converter.DoctorPageVOConverter;
import com.xitian.smarthealthhub.domain.entity.DoctorProfiles;
import com.xitian.smarthealthhub.domain.entity.Users;
import com.xitian.smarthealthhub.domain.query.DoctorPageQuery;
import com.xitian.smarthealthhub.domain.vo.DoctorPageVO;
import com.xitian.smarthealthhub.mapper.DoctorProfilesMapper;
import com.xitian.smarthealthhub.mapper.UsersMapper;
import com.xitian.smarthealthhub.service.DoctorProfilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorProfilesServiceImpl extends ServiceImpl<DoctorProfilesMapper, DoctorProfiles> implements DoctorProfilesService {
    
    @Autowired
    private UsersMapper usersMapper;
    
    @Override
    public PageBean<DoctorPageVO> page(PageParam<DoctorPageQuery> param) {
        // 创建分页对象
        Page<DoctorProfiles> page = new Page<>(param.getPageNum(), param.getPageSize());
        
        // 构建查询条件
        LambdaQueryWrapper<DoctorProfiles> queryWrapper = Wrappers.lambdaQuery();
        
        // 如果有查询条件，则添加查询条件
        if (param.getQuery() != null) {
            DoctorPageQuery doctorQuery = param.getQuery();
            
            // 职称模糊查询
            if (StringUtils.hasText(doctorQuery.getTitle())) {
                queryWrapper.like(DoctorProfiles::getTitle, doctorQuery.getTitle());
            }
            
            // 擅长领域模糊查询
            if (StringUtils.hasText(doctorQuery.getSpecialty())) {
                queryWrapper.like(DoctorProfiles::getSpecialty, doctorQuery.getSpecialty());
            }
            
            // 科室精确查询
            if (doctorQuery.getDeptId() != null) {
                queryWrapper.eq(DoctorProfiles::getDeptId, doctorQuery.getDeptId());
            }
        }
        
        // 执行分页查询
        Page<DoctorProfiles> resultPage = this.page(page, queryWrapper);
        
        // 转换为VO对象
        List<DoctorPageVO> voList = resultPage.getRecords().stream()
                .map(doctorProfiles -> {
                    Users user = usersMapper.selectById(doctorProfiles.getUserId());
                    return DoctorPageVOConverter.toVO(doctorProfiles, user);
                })
                .collect(Collectors.toList());
        
        // 构造并返回PageBean
        return PageBean.of(voList, resultPage.getTotal(), param);
    }
}