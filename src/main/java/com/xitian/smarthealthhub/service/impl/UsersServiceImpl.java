package com.xitian.smarthealthhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.converter.UserConverter;
import com.xitian.smarthealthhub.domain.entity.Users;
import com.xitian.smarthealthhub.domain.query.UserQuery;
import com.xitian.smarthealthhub.domain.vo.UserVO;
import com.xitian.smarthealthhub.mapper.UsersMapper;
import com.xitian.smarthealthhub.service.UsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {
    @Override
    public PageBean<UserVO> page(PageParam<UserQuery> param) {
        // 创建分页对象
        Page<Users> page = new Page<>(param.getPageNum(), param.getPageSize());
        
        // 构建查询条件
        LambdaQueryWrapper<Users> queryWrapper = Wrappers.lambdaQuery();
        
        // 如果有查询条件，则添加查询条件
        if (param.getQuery() != null) {
            UserQuery userQuery = param.getQuery();
            
            // 用户名模糊查询
            if (StringUtils.hasText(userQuery.getUsername())) {
                queryWrapper.like(Users::getUsername, userQuery.getUsername());
            }
            
            // 真实姓名模糊查询
            if (StringUtils.hasText(userQuery.getRealName())) {
                queryWrapper.like(Users::getRealName, userQuery.getRealName());
            }
            
            // 角色精确查询
            if (userQuery.getRole() != null) {
                queryWrapper.eq(Users::getRole, userQuery.getRole());
            }
            
            // 手机号模糊查询
            if (StringUtils.hasText(userQuery.getPhone())) {
                queryWrapper.like(Users::getPhone, userQuery.getPhone());
            }
            
            // 邮箱模糊查询
            if (StringUtils.hasText(userQuery.getEmail())) {
                queryWrapper.like(Users::getEmail, userQuery.getEmail());
            }
            
            // 性别精确查询
            if (StringUtils.hasText(userQuery.getGender())) {
                queryWrapper.eq(Users::getGender, userQuery.getGender());
            }
            
            // 状态精确查询
            if (userQuery.getStatus() != null) {
                queryWrapper.eq(Users::getStatus, userQuery.getStatus());
            }
        }
        
        // 执行分页查询
        Page<Users> resultPage = this.page(page, queryWrapper);
        
        // 转换为VO对象
        List<UserVO> voList = resultPage.getRecords().stream()
                .map(UserConverter::toVO)
                .collect(Collectors.toList());
        
        // 构造并返回PageBean
        return PageBean.of(voList, resultPage.getTotal(), param);
    }
}