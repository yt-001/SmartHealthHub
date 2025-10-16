package com.xitian.smarthealthhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.converter.UserConverter;
import com.xitian.smarthealthhub.domain.dto.UserUpdateDTO;
import com.xitian.smarthealthhub.domain.entity.Users;
import com.xitian.smarthealthhub.domain.query.UserQuery;
import com.xitian.smarthealthhub.domain.vo.UserVO;
import com.xitian.smarthealthhub.mapper.UsersMapper;
import com.xitian.smarthealthhub.service.UsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService, UserDetailsService {
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
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
        // 直接查询数据库获取用户信息（主要用于登录认证过程）
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Users::getPhone, username);
        Users user = this.getOne(queryWrapper);
        
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        
        // 检查用户状态，只有正常状态的用户才能登录
        if (user.getStatus() != 0) {
            throw new UsernameNotFoundException("用户账户状态异常");
        }
        
        // 根据用户角色设置权限
        String roleName = getRoleName(user.getRole());
        return new User(user.getPhone(), user.getPasswordHash(), true, true, true, true,
                Collections.singletonList(new SimpleGrantedAuthority(roleName)));
    }
    
    @Override
    public Users getUserByPhone(String phone) {
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Users::getPhone, phone);
        return this.getOne(queryWrapper);
    }
    
    @Override
    public boolean updateUserInfo(UserUpdateDTO userUpdateDTO) {
        // 根据ID查询用户
        Users user = this.getById(userUpdateDTO.getId());
        if (user == null) {
            return false;
        }
        
        // 只更新传递了的字段
        if (userUpdateDTO.getRealName() != null) {
            user.setRealName(userUpdateDTO.getRealName());
        }
        if (userUpdateDTO.getPhone() != null) {
            user.setPhone(userUpdateDTO.getPhone());
        }
        if (userUpdateDTO.getEmail() != null) {
            user.setEmail(userUpdateDTO.getEmail());
        }
        if (userUpdateDTO.getBirthDate() != null) {
            user.setBirthDate(userUpdateDTO.getBirthDate());
        }
        if (userUpdateDTO.getGender() != null) {
            user.setGender(userUpdateDTO.getGender());
        }
        
        // 保存更新后的用户信息
        return this.updateById(user);
    }
    
    /**
     * 清除用户权限缓存
     * @param username 用户名
     */
    public void clearUserCache(String username) {
        // 由于现在JWT中已包含角色信息，不再需要Redis缓存用户详情
        // 此方法保留以确保与AuthController的兼容性
    }
    
    /**
     * 根据角色代码获取角色名称
     * @param roleCode 角色代码
     * @return 角色名称
     */
    private String getRoleName(Byte roleCode) {
        return switch (roleCode) {
            case 0 -> "ROLE_ADMIN";
            case 1 -> "ROLE_DOCTOR";
            case 2 -> "ROLE_USER";
            default -> "ROLE_UNKNOWN";
        };
    }
}