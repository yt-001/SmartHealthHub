package com.xitian.smarthealthhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.converter.MedicineRecommendationLevelsConverter;
import com.xitian.smarthealthhub.domain.dto.MedicineRecommendationLevelsDto;
import com.xitian.smarthealthhub.domain.entity.MedicineRecommendationLevelsEntity;
import com.xitian.smarthealthhub.domain.query.MedicineRecommendationLevelsQuery;
import com.xitian.smarthealthhub.domain.vo.MedicineRecommendationLevelsVo;
import com.xitian.smarthealthhub.mapper.MedicineRecommendationLevelsMapper;
import com.xitian.smarthealthhub.service.MedicineRecommendationLevelsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 药品推荐级别服务实现类
 */
@Service
public class MedicineRecommendationLevelsServiceImpl implements MedicineRecommendationLevelsService {
    private final MedicineRecommendationLevelsMapper medicineRecommendationLevelsMapper;

    private final MedicineRecommendationLevelsConverter medicineRecommendationLevelsConverter;

    public MedicineRecommendationLevelsServiceImpl(MedicineRecommendationLevelsMapper medicineRecommendationLevelsMapper,
                                                   MedicineRecommendationLevelsConverter medicineRecommendationLevelsConverter) {
        this.medicineRecommendationLevelsMapper = medicineRecommendationLevelsMapper;
        this.medicineRecommendationLevelsConverter = medicineRecommendationLevelsConverter;
    }

    /**
     * 分页查询推荐级别
     *
     * @param param 分页参数和查询条件
     * @return 分页结果
     */
    @Override
    public PageBean<MedicineRecommendationLevelsVo> pageQuery(PageParam<MedicineRecommendationLevelsQuery> param) {
        Page<MedicineRecommendationLevelsEntity> page = new Page<>(param.getPageNum(), param.getPageSize());

        LambdaQueryWrapper<MedicineRecommendationLevelsEntity> queryWrapper = Wrappers.lambdaQuery();

        if (param.getQuery() != null) {
            MedicineRecommendationLevelsQuery query = param.getQuery();

            if (StringUtils.hasText(query.getName())) {
                queryWrapper.like(MedicineRecommendationLevelsEntity::getName, query.getName());
            }

            if (query.getIsEnabled() != null) {
                queryWrapper.eq(MedicineRecommendationLevelsEntity::getIsEnabled, query.getIsEnabled());
            }
        }

        queryWrapper.orderByAsc(MedicineRecommendationLevelsEntity::getSortOrder)
                .orderByDesc(MedicineRecommendationLevelsEntity::getCreatedAt);

        Page<MedicineRecommendationLevelsEntity> resultPage =
                medicineRecommendationLevelsMapper.selectPage(page, queryWrapper);

        List<MedicineRecommendationLevelsVo> list = resultPage.getRecords().stream()
                .map(medicineRecommendationLevelsConverter::entityToVo)
                .toList();

        return PageBean.of(list, resultPage.getTotal(), param);
    }

    /**
     * 根据ID查询推荐级别
     *
     * @param id 推荐级别ID
     * @return 推荐级别详情
     */
    @Override
    public MedicineRecommendationLevelsVo getById(Long id) {
        MedicineRecommendationLevelsEntity entity = medicineRecommendationLevelsMapper.selectById(id);
        return medicineRecommendationLevelsConverter.entityToVo(entity);
    }

    /**
     * 新增推荐级别
     *
     * @param dto 推荐级别数据传输对象
     * @return 新增记录ID
     */
    @Override
    public Long add(MedicineRecommendationLevelsDto dto) {
        MedicineRecommendationLevelsEntity entity = medicineRecommendationLevelsConverter.dtoToEntity(dto);
        medicineRecommendationLevelsMapper.insert(entity);
        return entity.getId();
    }

    /**
     * 更新推荐级别
     *
     * @param dto 推荐级别数据传输对象
     * @return 是否更新成功
     */
    @Override
    public Boolean update(MedicineRecommendationLevelsDto dto) {
        MedicineRecommendationLevelsEntity entity = medicineRecommendationLevelsConverter.dtoToEntity(dto);
        return medicineRecommendationLevelsMapper.updateById(entity) > 0;
    }

    /**
     * 删除推荐级别
     *
     * @param id 推荐级别ID
     * @return 是否删除成功
     */
    @Override
    public Boolean delete(Long id) {
        return medicineRecommendationLevelsMapper.deleteById(id) > 0;
    }
}

