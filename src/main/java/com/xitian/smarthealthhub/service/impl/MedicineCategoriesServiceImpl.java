package com.xitian.smarthealthhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.converter.MedicineCategoriesConverter;
import com.xitian.smarthealthhub.domain.dto.MedicineCategoriesDto;
import com.xitian.smarthealthhub.domain.entity.MedicineCategoriesEntity;
import com.xitian.smarthealthhub.domain.query.MedicineCategoriesQuery;
import com.xitian.smarthealthhub.domain.vo.MedicineCategoriesVo;
import com.xitian.smarthealthhub.mapper.MedicineCategoriesMapper;
import com.xitian.smarthealthhub.service.MedicineCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 药品分类Service实现类
 */
@Service
public class MedicineCategoriesServiceImpl implements MedicineCategoriesService {

    @Autowired
    private MedicineCategoriesMapper medicineCategoriesMapper;

    @Autowired
    private MedicineCategoriesConverter medicineCategoriesConverter;

    @Override
    public PageBean<MedicineCategoriesVo> pageQuery(MedicineCategoriesQuery query) {
        // 构建查询条件
        QueryWrapper<MedicineCategoriesEntity> wrapper = new QueryWrapper<>();
        if (query.getParentId() != null) {
            wrapper.eq("parent_id", query.getParentId());
        }
        if (query.getName() != null && !query.getName().isEmpty()) {
            wrapper.like("name", query.getName());
        }
        if (query.getIsEnabled() != null) {
            wrapper.eq("is_enabled", query.getIsEnabled());
        }

        // 执行分页查询
        Page<MedicineCategoriesEntity> page = new Page<>(query.getPageNum(), query.getPageSize());
        Page<MedicineCategoriesEntity> resultPage = medicineCategoriesMapper.selectPage(page, wrapper);

        // 转换为VO并封装分页结果
        PageBean<MedicineCategoriesVo> pageBean = new PageBean<>();
        pageBean.setPageNum(query.getPageNum());
        pageBean.setPageSize(query.getPageSize());
        pageBean.setTotal(resultPage.getTotal());
        pageBean.setList(resultPage.getRecords().stream()
                .map(medicineCategoriesConverter::entityToVo)
                .toList());
        return pageBean;
    }

    @Override
    public MedicineCategoriesVo getById(Long id) {
        MedicineCategoriesEntity entity = medicineCategoriesMapper.selectById(id);
        return medicineCategoriesConverter.entityToVo(entity);
    }

    @Override
    public Long add(MedicineCategoriesDto dto) {
        MedicineCategoriesEntity entity = medicineCategoriesConverter.dtoToEntity(dto);
        medicineCategoriesMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public Boolean update(MedicineCategoriesDto dto) {
        MedicineCategoriesEntity entity = medicineCategoriesConverter.dtoToEntity(dto);
        int result = medicineCategoriesMapper.updateById(entity);
        return result > 0;
    }

    @Override
    public Boolean delete(Long id) {
        int result = medicineCategoriesMapper.deleteById(id);
        return result > 0;
    }
}