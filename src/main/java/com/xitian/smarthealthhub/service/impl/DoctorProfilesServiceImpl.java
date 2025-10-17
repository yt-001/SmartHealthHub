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
import com.xitian.smarthealthhub.domain.query.DoctorVerificationQuery;
import com.xitian.smarthealthhub.domain.vo.DoctorPageVO;
import com.xitian.smarthealthhub.domain.vo.DoctorVerificationVO;
import com.xitian.smarthealthhub.converter.DoctorVerificationVOConverter;
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
            
            // 职称精确查询
            if (StringUtils.hasText(doctorQuery.getTitle())) {
                queryWrapper.eq(DoctorProfiles::getTitle, doctorQuery.getTitle());
            }
            
            // 姓名模糊查询
            if (StringUtils.hasText(doctorQuery.getRealName())) {
                // 先查询匹配的用户ID
                LambdaQueryWrapper<Users> userQueryWrapper = Wrappers.lambdaQuery();
                userQueryWrapper.eq(Users::getRole, (byte) 1); // 医生角色
                userQueryWrapper.like(Users::getRealName, doctorQuery.getRealName());
                List<Users> matchedUsers = usersMapper.selectList(userQueryWrapper);
                List<Long> matchedUserIds = matchedUsers.stream()
                        .map(Users::getId)
                        .collect(Collectors.toList());
                
                if (matchedUserIds.isEmpty()) {
                    // 如果没有匹配的用户，构造一个不可能的条件
                    queryWrapper.eq(DoctorProfiles::getId, -1);
                } else {
                    // 查询这些用户对应的医生档案
                    queryWrapper.in(DoctorProfiles::getUserId, matchedUserIds);
                }
            }
            
            // 科室精确查询
            if (doctorQuery.getDeptId() != null) {
                queryWrapper.eq(DoctorProfiles::getDeptId, doctorQuery.getDeptId());
            }
            
            // 时间范围查询 - 创建时间
            if (StringUtils.hasText(doctorQuery.getCreatedAt())) {
                // 假设createTime格式为yyyy-MM-dd
                String createTime = doctorQuery.getCreatedAt();
                queryWrapper.apply("DATE(created_at) = {0}", createTime);
            }
        }
        
        // 执行分页查询
        Page<DoctorProfiles> resultPage = this.page(page, queryWrapper);
        
        // 获取医生用户ID列表
        List<Long> userIds = resultPage.getRecords().stream()
                .map(DoctorProfiles::getUserId)
                .distinct()
                .collect(Collectors.toList());
        
        // 批量查询用户信息
        List<Users> users = userIds.isEmpty() ? List.of() : usersMapper.selectBatchIds(userIds);
        // 构建用户ID到用户对象的映射
        java.util.Map<Long, Users> userMap = users.stream()
                .collect(Collectors.toMap(Users::getId, java.util.function.Function.identity()));
        
        // 转换为VO对象
        List<DoctorPageVO> voList = resultPage.getRecords().stream()
                .map(doctorProfiles -> {
                    Users user = userMap.get(doctorProfiles.getUserId());
                    return DoctorPageVOConverter.toVO(doctorProfiles, user);
                })
                .collect(Collectors.toList());
        
        // 如果有查询条件，根据姓名匹配度排序
        if (param.getQuery() != null) {
            DoctorPageQuery doctorQuery = param.getQuery();
            if (StringUtils.hasText(doctorQuery.getRealName())) {
                String realName = doctorQuery.getRealName();
                // 完全匹配的排在前面
                voList.sort((vo1, vo2) -> {
                    boolean match1 = realName.equals(vo1.getRealName());
                    boolean match2 = realName.equals(vo2.getRealName());
                    
                    if (match1 && !match2) {
                        return -1; // vo1完全匹配，排在前面
                    } else if (!match1 && match2) {
                        return 1;  // vo2完全匹配，排在前面
                    } else {
                        // 都完全匹配或都不完全匹配，按姓名字母顺序排序
                        return vo1.getRealName().compareTo(vo2.getRealName());
                    }
                });
            }
        }
        
        // 构造并返回PageBean
        return PageBean.of(voList, voList.size(), param);
    }
    
    @Override
    public PageBean<DoctorVerificationVO> pagePendingDoctors(PageParam<DoctorVerificationQuery> param) {
        // 创建分页对象
        Page<DoctorProfiles> page = new Page<>(param.getPageNum(), param.getPageSize());
        
        // 构建查询条件
        LambdaQueryWrapper<DoctorProfiles> queryWrapper = Wrappers.lambdaQuery();
        // 只查询资质认证状态为审核中(2)的医生
        queryWrapper.eq(DoctorProfiles::getQualificationVerified, (byte) 2);
        
        // 如果有查询条件，则添加查询条件
        if (param.getQuery() != null) {
            DoctorVerificationQuery doctorQuery = param.getQuery();
            
            // 真实姓名模糊查询
            if (StringUtils.hasText(doctorQuery.getRealName())) {
                // 先查询匹配的用户ID
                LambdaQueryWrapper<Users> userQueryWrapper = Wrappers.lambdaQuery();
                userQueryWrapper.eq(Users::getRole, (byte) 1); // 医生角色
                userQueryWrapper.like(Users::getRealName, doctorQuery.getRealName());
                List<Users> matchedUsers = usersMapper.selectList(userQueryWrapper);
                List<Long> matchedUserIds = matchedUsers.stream()
                        .map(Users::getId)
                        .collect(Collectors.toList());
                
                if (matchedUserIds.isEmpty()) {
                    // 如果没有匹配的用户，构造一个不可能的条件
                    queryWrapper.eq(DoctorProfiles::getId, -1);
                } else {
                    // 查询这些用户对应的医生档案
                    queryWrapper.in(DoctorProfiles::getUserId, matchedUserIds);
                }
            }
            
            // 医师执业证号模糊查询
            if (StringUtils.hasText(doctorQuery.getQualificationNo())) {
                queryWrapper.like(DoctorProfiles::getQualificationNo, doctorQuery.getQualificationNo());
            }
        }
        
        // 执行分页查询
        Page<DoctorProfiles> resultPage = this.page(page, queryWrapper);
        
        // 获取医生用户ID列表
        List<Long> userIds = resultPage.getRecords().stream()
                .map(DoctorProfiles::getUserId)
                .distinct()
                .collect(Collectors.toList());
        
        // 批量查询用户信息
        List<Users> users = userIds.isEmpty() ? List.of() : usersMapper.selectBatchIds(userIds);
        // 构建用户ID到用户对象的映射
        java.util.Map<Long, Users> userMap = users.stream()
                .collect(Collectors.toMap(Users::getId, java.util.function.Function.identity()));
        
        // 转换为VO对象
        List<DoctorVerificationVO> voList = resultPage.getRecords().stream()
                .map(doctorProfiles -> {
                    Users user = userMap.get(doctorProfiles.getUserId());
                    return DoctorVerificationVOConverter.toVO(doctorProfiles, user);
                })
                .collect(Collectors.toList());
        
        // 构造并返回PageBean
        return PageBean.of(voList, voList.size(), param);
    }
}