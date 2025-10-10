package com.xitian.smarthealthhub.bean.base.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

public class BaseServiceImpl<E> extends ServiceImpl<BaseMapper<E>, E> implements BaseService<E> {

}
