package com.xitian.smarthealthhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.domain.entity.DoctorProfiles;
import com.xitian.smarthealthhub.domain.entity.Users;
import com.xitian.smarthealthhub.domain.query.DoctorPageQuery;
import com.xitian.smarthealthhub.domain.vo.DoctorPageVO;
import com.xitian.smarthealthhub.mapper.DoctorProfilesMapper;
import com.xitian.smarthealthhub.service.DoctorProfilesService;
import com.xitian.smarthealthhub.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorProfilesServiceImpl extends ServiceImpl<DoctorProfilesMapper, DoctorProfiles> implements DoctorProfilesService {
    
    @Autowired
    private UsersService usersService;
    
    @Override
    public PageBean<DoctorPageVO> page(PageParam<DoctorPageQuery> param) {
        // 创建分页对象
        Page<Users> page = new Page<>(param.getPageNum(), param.getPageSize());

        // 构建用户查询条件
        LambdaQueryWrapper<Users> queryWrapper = Wrappers.lambdaQuery();
        
        // 只查询医生角色（role=1）
        queryWrapper.eq(Users::getRole, (byte) 1);

        // 如果有查询条件，则添加查询条件
        if (param.getQuery() != null) {
            DoctorPageQuery doctorPageQuery = param.getQuery();

            // 用户名模糊查询
            if (StringUtils.hasText(doctorPageQuery.getUsername())) {
                queryWrapper.like(Users::getUsername, doctorPageQuery.getUsername());
            }

            // 真实姓名模糊查询
            if (StringUtils.hasText(doctorPageQuery.getRealName())) {
                queryWrapper.like(Users::getRealName, doctorPageQuery.getRealName());
            }

            // 手机号模糊查询
            if (StringUtils.hasText(doctorPageQuery.getPhone())) {
                queryWrapper.like(Users::getPhone, doctorPageQuery.getPhone());
            }

            // 邮箱模糊查询
            if (StringUtils.hasText(doctorPageQuery.getEmail())) {
                queryWrapper.like(Users::getEmail, doctorPageQuery.getEmail());
            }

            // 账号状态精确查询
            if (doctorPageQuery.getStatus() != null) {
                queryWrapper.eq(Users::getStatus, doctorPageQuery.getStatus());
            }
        }

        // 执行分页查询
        Page<Users> resultPage = usersService.page(page, queryWrapper);

        // 获取用户ID列表
        List<Long> userIds = resultPage.getRecords().stream()
                .map(Users::getId)
                .collect(Collectors.toList());

        // 批量查询医生档案信息
        LambdaQueryWrapper<DoctorProfiles> profileQueryWrapper = Wrappers.lambdaQuery();
        profileQueryWrapper.in(DoctorProfiles::getUserId, userIds);
        List<DoctorProfiles> profiles = this.list(profileQueryWrapper);
        
        // 构建用户ID到档案的映射
        java.util.Map<Long, DoctorProfiles> profileMap = profiles.stream()
                .collect(Collectors.toMap(DoctorProfiles::getUserId, profile -> profile));

        // 转换为PageVO对象
        List<DoctorPageVO> voList = resultPage.getRecords().stream()
                .map(user -> {
                    DoctorProfiles profile = profileMap.get(user.getId());
                    DoctorPageVO vo = new DoctorPageVO();
                    
                    // 从 Users 实体映射字段到 DoctorPageVO
                    vo.setId(user.getId());
                    vo.setUsername(user.getUsername());
                    vo.setRealName(user.getRealName());
                    vo.setPhone(user.getPhone());
                    vo.setEmail(user.getEmail());
                    vo.setStatus(user.getStatus());
                    vo.setCreatedAt(user.getCreatedAt());

                    // 从 DoctorProfiles 实体映射字段到 DoctorPageVO（如果存在）
                    if (profile != null) {
                        vo.setTitle(profile.getTitle());
                        vo.setDeptId(profile.getDeptId());
                        vo.setSpecialty(profile.getSpecialty());
                        vo.setQualificationNo(profile.getQualificationNo());
                    }
                    
                    return vo;
                })
                .collect(Collectors.toList());

        // 构造并返回PageBean
        return PageBean.of(voList, resultPage.getTotal(), param);
    }
}