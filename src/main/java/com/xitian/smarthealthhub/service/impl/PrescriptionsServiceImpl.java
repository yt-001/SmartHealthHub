package com.xitian.smarthealthhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.domain.entity.Prescriptions;
import com.xitian.smarthealthhub.mapper.PrescriptionsMapper;
import com.xitian.smarthealthhub.service.PrescriptionsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 处方服务实现类
 */
@Service
public class PrescriptionsServiceImpl extends ServiceImpl<PrescriptionsMapper, Prescriptions> implements PrescriptionsService {

    @Override
    public PageBean<Prescriptions> pageQuery(PageParam<Prescriptions> pageParam) {
        Page<Prescriptions> page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        LambdaQueryWrapper<Prescriptions> queryWrapper = new LambdaQueryWrapper<>();
        
        Prescriptions query = pageParam.getQuery();
        if (query != null) {
            // 根据患者姓名查询
            if (StringUtils.hasText(query.getPatientName())) {
                queryWrapper.like(Prescriptions::getPatientName, query.getPatientName());
            }
            // 根据医生姓名查询
            if (StringUtils.hasText(query.getDoctorName())) {
                queryWrapper.like(Prescriptions::getDoctorName, query.getDoctorName());
            }
            // 根据状态查询
            if (query.getStatus() != null) {
                queryWrapper.eq(Prescriptions::getStatus, query.getStatus());
            }
            // 根据医生ID查询 (医生端查看自己的处方)
            if (query.getDoctorId() != null) {
                queryWrapper.eq(Prescriptions::getDoctorId, query.getDoctorId());
            }
            // 根据患者ID查询 (患者端查看自己的处方)
            if (query.getUserId() != null) {
                queryWrapper.eq(Prescriptions::getUserId, query.getUserId());
            }
        }
        
        // 按创建时间倒序
        queryWrapper.orderByDesc(Prescriptions::getCreatedAt);
        
        this.page(page, queryWrapper);
        return PageBean.of(page.getRecords(), page.getTotal(), pageParam);
    }
}
