package com.xitian.smarthealthhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.converter.PatientPageVOConverter;
import com.xitian.smarthealthhub.domain.entity.UserProfiles;
import com.xitian.smarthealthhub.domain.entity.Users;
import com.xitian.smarthealthhub.domain.query.PatientPageQuery;
import com.xitian.smarthealthhub.domain.vo.PatientPageVO;
import com.xitian.smarthealthhub.mapper.UserProfilesMapper;
import com.xitian.smarthealthhub.service.UserProfilesService;
import com.xitian.smarthealthhub.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserProfilesServiceImpl extends ServiceImpl<UserProfilesMapper, UserProfiles> implements UserProfilesService {
    
    @Autowired
    private UsersService usersService;
    
    @Override
    public PageBean<PatientPageVO> page(PageParam<PatientPageQuery> param) {
        // 创建分页对象
        Page<Users> page = new Page<>(param.getPageNum(), param.getPageSize());

        // 构建用户查询条件
        LambdaQueryWrapper<Users> queryWrapper = Wrappers.lambdaQuery();
        
        // 只查询患者角色（role=2）
        queryWrapper.eq(Users::getRole, (byte) 2);

        // 如果有查询条件，则添加查询条件
        if (param.getQuery() != null) {
            PatientPageQuery patientPageQuery = param.getQuery();

            // 用户名模糊查询
            if (StringUtils.hasText(patientPageQuery.getUsername())) {
                queryWrapper.like(Users::getUsername, patientPageQuery.getUsername());
            }

            // 真实姓名模糊查询
            if (StringUtils.hasText(patientPageQuery.getRealName())) {
                queryWrapper.like(Users::getRealName, patientPageQuery.getRealName());
            }

            // 手机号模糊查询
            if (StringUtils.hasText(patientPageQuery.getPhone())) {
                queryWrapper.like(Users::getPhone, patientPageQuery.getPhone());
            }

            // 性别精确查询
            if (StringUtils.hasText(patientPageQuery.getGender())) {
                queryWrapper.eq(Users::getGender, patientPageQuery.getGender());
            }

            // 账号状态精确查询
            if (patientPageQuery.getStatus() != null) {
                queryWrapper.eq(Users::getStatus, patientPageQuery.getStatus());
            }
        }

        // 执行分页查询
        Page<Users> resultPage = usersService.page(page, queryWrapper);

        // 获取用户ID列表
        List<Long> userIds = resultPage.getRecords().stream()
                .map(Users::getId)
                .collect(Collectors.toList());

        // 批量查询用户档案信息
        List<UserProfiles> profiles = userIds.isEmpty() ? List.of() : this.listByIds(userIds);
        
        // 构建用户ID到档案的映射
        java.util.Map<Long, UserProfiles> profileMap = profiles.stream()
                .collect(Collectors.toMap(UserProfiles::getUserId, profile -> profile));

        // 转换为PageVO对象
        List<PatientPageVO> voList = resultPage.getRecords().stream()
                .map(user -> PatientPageVOConverter.toPageVO(user, profileMap.get(user.getId())))
                .collect(Collectors.toList());

        // 构造并返回PageBean
        return PageBean.of(voList, resultPage.getTotal(), param);
    }
}