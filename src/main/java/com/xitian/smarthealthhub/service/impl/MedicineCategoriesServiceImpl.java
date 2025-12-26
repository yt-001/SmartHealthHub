package com.xitian.smarthealthhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.converter.MedicineCategoriesConverter;
import com.xitian.smarthealthhub.domain.dto.MedicineCategoriesDto;
import com.xitian.smarthealthhub.domain.entity.MedicineCategoriesEntity;
import com.xitian.smarthealthhub.domain.query.MedicineCategoriesQuery;
import com.xitian.smarthealthhub.domain.vo.MedicineCategoriesVo;
import com.xitian.smarthealthhub.mapper.MedicineCategoriesMapper;
import com.xitian.smarthealthhub.service.MedicineCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 药品分类服务实现类
 * 
 * @author 
 * @date 2025/02/04
 */
@Service
public class MedicineCategoriesServiceImpl implements MedicineCategoriesService {
    private final MedicineCategoriesMapper medicineCategoriesMapper;

    private final MedicineCategoriesConverter medicineCategoriesConverter;

    public MedicineCategoriesServiceImpl(MedicineCategoriesMapper medicineCategoriesMapper,
                                        MedicineCategoriesConverter medicineCategoriesConverter) {
        this.medicineCategoriesMapper = medicineCategoriesMapper;
        this.medicineCategoriesConverter = medicineCategoriesConverter;
    }

    /**
     * 分页查询
     * 
     * @param param 分页参数和查询条件
     * @return 分页结果
     */
    @Override
    public PageBean<MedicineCategoriesVo> pageQuery(PageParam<MedicineCategoriesQuery> param) {
        // 构建分页对象
        Page<MedicineCategoriesEntity> page = new Page<>(param.getPageNum(), param.getPageSize());
        
        // 构建查询条件
        LambdaQueryWrapper<MedicineCategoriesEntity> queryWrapper = Wrappers.lambdaQuery();
        
        // 如果有查询条件，则添加查询条件
        if (param.getQuery() != null) {
            MedicineCategoriesQuery query = param.getQuery();
            
            if (query.getParentId() != null) {
                queryWrapper.eq(MedicineCategoriesEntity::getParentId, query.getParentId());
            }

            if (StringUtils.hasText(query.getName())) {
                queryWrapper.like(MedicineCategoriesEntity::getName, query.getName());
            }

            if (query.getIsEnabled() != null) {
                queryWrapper.eq(MedicineCategoriesEntity::getIsEnabled, query.getIsEnabled());
            }
        }

        // 按创建时间倒序排列
        queryWrapper.orderByDesc(MedicineCategoriesEntity::getCreatedAt);
        
        // 执行分页查询
        Page<MedicineCategoriesEntity> resultPage = medicineCategoriesMapper.selectPage(page, queryWrapper);

        // 转换为VO并封装分页结果
        return PageBean.of(resultPage.getRecords().stream()
                .map(medicineCategoriesConverter::entityToVo)
                .toList(), resultPage.getTotal(), param);
    }

    /**
     * 根据ID查询
     * 
     * @param id ID
     * @return 实体
     */
    @Override
    public MedicineCategoriesVo getById(Long id) {
        MedicineCategoriesEntity entity = medicineCategoriesMapper.selectById(id);
        return medicineCategoriesConverter.entityToVo(entity);
    }

    /**
     * 新增
     * 
     * @param dto DTO
     * @return ID
     */
    @Override
    public Long add(MedicineCategoriesDto dto) {
        MedicineCategoriesEntity entity = medicineCategoriesConverter.dtoToEntity(dto);
        medicineCategoriesMapper.insert(entity);
        return entity.getId();
    }

    /**
     * 修改
     * 
     * @param dto DTO
     * @return 是否成功
     */
    @Override
    public Boolean update(MedicineCategoriesDto dto) {
        MedicineCategoriesEntity entity = medicineCategoriesConverter.dtoToEntity(dto);
        return medicineCategoriesMapper.updateById(entity) > 0;
    }

    /**
     * 删除
     * 
     * @param id ID
     * @return 是否成功
     */
    @Override
    public Boolean delete(Long id) {
        return medicineCategoriesMapper.deleteById(id) > 0;
    }

    /**
     * 根据父分类ID获取子分类ID列表
     * 
     * @param parentId 父分类ID
     * @return 子分类ID列表
     */
    @Override
    public List<Long> getSubCategoryIdsByParentId(Long parentId) {
        LambdaQueryWrapper<MedicineCategoriesEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(MedicineCategoriesEntity::getParentId, parentId);
        
        List<MedicineCategoriesEntity> entities = medicineCategoriesMapper.selectList(queryWrapper);
        return entities.stream()
                .map(MedicineCategoriesEntity::getId)
                .collect(Collectors.toList());
    }

    /**
     * 获取所有一级分类（大类）列表
     *
     * @return 大类分类列表
     */
    @Override
    public List<MedicineCategoriesVo> listBigCategories() {
        LambdaQueryWrapper<MedicineCategoriesEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.isNull(MedicineCategoriesEntity::getParentId);
        queryWrapper.eq(MedicineCategoriesEntity::getIsEnabled, 1);
        queryWrapper.orderByAsc(MedicineCategoriesEntity::getSortOrder)
                .orderByDesc(MedicineCategoriesEntity::getCreatedAt);

        List<MedicineCategoriesEntity> entities = medicineCategoriesMapper.selectList(queryWrapper);
        return entities.stream()
                .map(medicineCategoriesConverter::entityToVo)
                .collect(Collectors.toList());
    }

    /**
     * 根据父分类ID获取子分类列表（小类）
     *
     * @param parentId 父分类ID
     * @return 子分类列表
     */
    @Override
    public List<MedicineCategoriesVo> listSubCategoriesByParentId(Long parentId) {
        LambdaQueryWrapper<MedicineCategoriesEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(MedicineCategoriesEntity::getParentId, parentId);
        queryWrapper.eq(MedicineCategoriesEntity::getIsEnabled, 1);
        queryWrapper.orderByAsc(MedicineCategoriesEntity::getSortOrder)
                .orderByDesc(MedicineCategoriesEntity::getCreatedAt);

        List<MedicineCategoriesEntity> entities = medicineCategoriesMapper.selectList(queryWrapper);
        return entities.stream()
                .map(medicineCategoriesConverter::entityToVo)
                .collect(Collectors.toList());
    }
}
