package com.xitian.smarthealthhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.converter.MedicinesConverter;
import com.xitian.smarthealthhub.domain.dto.MedicinesDto;
import com.xitian.smarthealthhub.domain.entity.MedicinesEntity;
import com.xitian.smarthealthhub.domain.query.MedicinesQuery;
import com.xitian.smarthealthhub.domain.vo.MedicinesVo;
import com.xitian.smarthealthhub.mapper.MedicinesMapper;
import com.xitian.smarthealthhub.service.MedicinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 药品信息主表Service实现类
 */
@Service
public class MedicinesServiceImpl implements MedicinesService {

    @Autowired
    private MedicinesMapper medicinesMapper;

    @Autowired
    private MedicinesConverter medicinesConverter;

    @Override
    public PageBean<MedicinesVo> pageQuery(MedicinesQuery query) {
        // 构建查询条件
        QueryWrapper<MedicinesEntity> wrapper = new QueryWrapper<>();
        if (query.getName() != null && !query.getName().isEmpty()) {
            wrapper.like("name", query.getName());
        }
        if (query.getIsPrescription() != null) {
            wrapper.eq("is_prescription", query.getIsPrescription());
        }
        if (query.getStatus() != null) {
            wrapper.eq("status", query.getStatus());
        }
        if (query.getMinPrice() != null) {
            wrapper.ge("price", query.getMinPrice());
        }
        if (query.getMaxPrice() != null) {
            wrapper.le("price", query.getMaxPrice());
        }

        // 执行分页查询
        Page<MedicinesEntity> page = new Page<>(query.getPageNum(), query.getPageSize());
        Page<MedicinesEntity> resultPage = medicinesMapper.selectPage(page, wrapper);

        // 转换为VO并封装分页结果
        PageBean<MedicinesVo> pageBean = new PageBean<>();
        pageBean.setPageNum(query.getPageNum());
        pageBean.setPageSize(query.getPageSize());
        pageBean.setTotal(resultPage.getTotal());
        pageBean.setList(resultPage.getRecords().stream()
                .map(medicinesConverter::entityToVo)
                .toList());
        return pageBean;
    }

    @Override
    public MedicinesVo getById(Long id) {
        MedicinesEntity entity = medicinesMapper.selectById(id);
        return medicinesConverter.entityToVo(entity);
    }

    @Override
    public Long add(MedicinesDto dto) {
        MedicinesEntity entity = medicinesConverter.dtoToEntity(dto);
        medicinesMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public Boolean update(MedicinesDto dto) {
        MedicinesEntity entity = medicinesConverter.dtoToEntity(dto);
        int result = medicinesMapper.updateById(entity);
        return result > 0;
    }

    @Override
    public Boolean delete(Long id) {
        int result = medicinesMapper.deleteById(id);
        return result > 0;
    }
}