package com.xitian.smarthealthhub.service;

import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.domain.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xitian.smarthealthhub.domain.query.UserQuery;
import com.xitian.smarthealthhub.domain.vo.UserVO;

public interface UsersService extends IService<Users> {
    PageBean<UserVO> page(PageParam<UserQuery> param);
}
