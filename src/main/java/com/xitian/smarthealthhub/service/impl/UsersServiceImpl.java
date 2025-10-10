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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService, UserDetailsService {
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据手机号查找用户
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Users::getPhone, username);
        Users user = this.getOne(queryWrapper);
        
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        
        // 返回UserDetails对象
        return new User(user.getPhone(), user.getPasswordHash(), 
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}