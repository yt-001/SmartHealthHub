package com.xitian.smarthealthhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.converter.MedicineCategoryRelationsConverter;
import com.xitian.smarthealthhub.domain.dto.MedicineCategoryRelationsDto;
import com.xitian.smarthealthhub.domain.entity.MedicineCategoryRelationsEntity;
import com.xitian.smarthealthhub.domain.query.MedicineCategoryRelationsQuery;
import com.xitian.smarthealthhub.domain.vo.MedicineCategoryRelationsVo;
import com.xitian.smarthealthhub.mapper.MedicineCategoryRelationsMapper;
import com.xitian.smarthealthhub.service.MedicineCategoryRelationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 药品与分类的关联表Service实现类
 */
@Service
public class MedicineCategoryRelationsServiceImpl implements MedicineCategoryRelationsService {

    @Autowired
    private MedicineCategoryRelationsMapper medicineCategoryRelationsMapper;

    @Autowired
    private MedicineCategoryRelationsConverter medicineCategoryRelationsConverter;

    @Override
    public PageBean<MedicineCategoryRelationsVo> pageQuery(MedicineCategoryRelationsQuery query) {
        // 构建查询条件
        QueryWrapper<MedicineCategoryRelationsEntity> wrapper = new QueryWrapper<>();
        if (query.getMedicineId() != null) {
            wrapper.eq("medicine_id", query.getMedicineId());
        }
        if (query.getCategoryId() != null) {
            wrapper.eq("category_id", query.getCategoryId());
        }

        // 执行分页查询
        Page<MedicineCategoryRelationsEntity> page = new Page<>(query.getPageNum(), query.getPageSize());
        Page<MedicineCategoryRelationsEntity> resultPage = medicineCategoryRelationsMapper.selectPage(page, wrapper);

        // 转换为VO并封装分页结果
        PageBean<MedicineCategoryRelationsVo> pageBean = new PageBean<>();
        pageBean.setPageNum(query.getPageNum());
        pageBean.setPageSize(query.getPageSize());
        pageBean.setTotal(resultPage.getTotal());
        pageBean.setList(resultPage.getRecords().stream()
                .map(medicineCategoryRelationsConverter::entityToVo)
                .toList());
        return pageBean;
    }

    @Override
    public MedicineCategoryRelationsVo getById(Long id) {
        MedicineCategoryRelationsEntity entity = medicineCategoryRelationsMapper.selectById(id);
        return medicineCategoryRelationsConverter.entityToVo(entity);
    }

    @Override
    public Long add(MedicineCategoryRelationsDto dto) {
        MedicineCategoryRelationsEntity entity = medicineCategoryRelationsConverter.dtoToEntity(dto);
        medicineCategoryRelationsMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public Boolean update(MedicineCategoryRelationsDto dto) {
        MedicineCategoryRelationsEntity entity = medicineCategoryRelationsConverter.dtoToEntity(dto);
        int result = medicineCategoryRelationsMapper.updateById(entity);
        return result > 0;
    }

    @Override
    public Boolean delete(Long id) {
        int result = medicineCategoryRelationsMapper.deleteById(id);
        return result > 0;
    }
}