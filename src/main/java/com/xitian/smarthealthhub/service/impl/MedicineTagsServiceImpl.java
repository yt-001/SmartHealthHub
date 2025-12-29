package com.xitian.smarthealthhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.converter.MedicineTagsConverter;
import com.xitian.smarthealthhub.domain.dto.MedicineTagsDto;
import com.xitian.smarthealthhub.domain.entity.MedicineTagsEntity;
import com.xitian.smarthealthhub.domain.query.MedicineTagsQuery;
import com.xitian.smarthealthhub.domain.vo.MedicineTagsVo;
import com.xitian.smarthealthhub.mapper.MedicineTagsMapper;
import com.xitian.smarthealthhub.service.MedicineTagsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 药品标签服务实现类
 */
@Service
public class MedicineTagsServiceImpl implements MedicineTagsService {
    private final MedicineTagsMapper medicineTagsMapper;

    private final MedicineTagsConverter medicineTagsConverter;

    public MedicineTagsServiceImpl(MedicineTagsMapper medicineTagsMapper,
                                   MedicineTagsConverter medicineTagsConverter) {
        this.medicineTagsMapper = medicineTagsMapper;
        this.medicineTagsConverter = medicineTagsConverter;
    }

    /**
     * 分页查询药品标签
     *
     * @param param 分页参数和查询条件
     * @return 分页结果
     */
    @Override
    public PageBean<MedicineTagsVo> pageQuery(PageParam<MedicineTagsQuery> param) {
        Page<MedicineTagsEntity> page = new Page<>(param.getPageNum(), param.getPageSize());

        LambdaQueryWrapper<MedicineTagsEntity> queryWrapper = Wrappers.lambdaQuery();

        if (param.getQuery() != null) {
            MedicineTagsQuery query = param.getQuery();

            if (StringUtils.hasText(query.getName())) {
                queryWrapper.like(MedicineTagsEntity::getName, query.getName());
            }

            if (query.getIsEnabled() != null) {
                queryWrapper.eq(MedicineTagsEntity::getIsEnabled, query.getIsEnabled());
            }
        }

        queryWrapper.orderByAsc(MedicineTagsEntity::getSortOrder)
                .orderByDesc(MedicineTagsEntity::getCreatedAt);

        Page<MedicineTagsEntity> resultPage = medicineTagsMapper.selectPage(page, queryWrapper);

        List<MedicineTagsVo> list = resultPage.getRecords().stream()
                .map(medicineTagsConverter::entityToVo)
                .toList();

        return PageBean.of(list, resultPage.getTotal(), param);
    }

    /**
     * 根据ID查询药品标签
     *
     * @param id 标签ID
     * @return 标签详情
     */
    @Override
    public MedicineTagsVo getById(Long id) {
        MedicineTagsEntity entity = medicineTagsMapper.selectById(id);
        return medicineTagsConverter.entityToVo(entity);
    }

    /**
     * 新增药品标签
     *
     * @param dto 标签数据传输对象
     * @return 新增记录ID
     */
    @Override
    public Long add(MedicineTagsDto dto) {
        MedicineTagsEntity entity = medicineTagsConverter.dtoToEntity(dto);
        medicineTagsMapper.insert(entity);
        return entity.getId();
    }

    /**
     * 更新药品标签
     *
     * @param dto 标签数据传输对象
     * @return 是否更新成功
     */
    @Override
    public Boolean update(MedicineTagsDto dto) {
        MedicineTagsEntity entity = medicineTagsConverter.dtoToEntity(dto);
        return medicineTagsMapper.updateById(entity) > 0;
    }

    /**
     * 删除药品标签
     *
     * @param id 标签ID
     * @return 是否删除成功
     */
    @Override
    public Boolean delete(Long id) {
        return medicineTagsMapper.deleteById(id) > 0;
    }
}

