package com.xitian.smarthealthhub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.domain.dto.UserUpdateDTO;
import com.xitian.smarthealthhub.domain.entity.Users;
import com.xitian.smarthealthhub.domain.query.UserQuery;
import com.xitian.smarthealthhub.domain.vo.UserVO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsersService extends IService<Users>, UserDetailsService {
    PageBean<UserVO> page(PageParam<UserQuery> param);
    
    /**
     * 根据手机号获取用户信息
     * @param phone 手机号
     * @return 用户信息
     */
    Users getUserByPhone(String phone);
    
    /**
     * 更新用户信息
     * @param userUpdateDTO 用户更新信息
     * @return 是否更新成功
     */
    boolean updateUserInfo(UserUpdateDTO userUpdateDTO);
}