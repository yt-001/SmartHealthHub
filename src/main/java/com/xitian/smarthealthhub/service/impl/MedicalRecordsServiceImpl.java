package com.xitian.smarthealthhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.converter.MedicalRecordPageVOConverter;
import com.xitian.smarthealthhub.domain.entity.MedicalRecords;
import com.xitian.smarthealthhub.domain.query.MedicalRecordQuery;
import com.xitian.smarthealthhub.domain.vo.MedicalRecordPageVO;
import com.xitian.smarthealthhub.mapper.MedicalRecordsMapper;
import com.xitian.smarthealthhub.service.MedicalRecordsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicalRecordsServiceImpl extends ServiceImpl<MedicalRecordsMapper, MedicalRecords> implements MedicalRecordsService {
    
    public PageBean<MedicalRecordPageVO> page(PageParam<MedicalRecordQuery> param) {
        // 创建分页对象
        Page<MedicalRecords> page = new Page<>(param.getPageNum(), param.getPageSize());

        // 构建查询条件
        LambdaQueryWrapper<MedicalRecords> queryWrapper = Wrappers.lambdaQuery();

        // 如果有查询条件，则添加查询条件
        if (param.getQuery() != null) {
            MedicalRecordQuery medicalRecordQuery = param.getQuery();

            // 患者姓名模糊查询
            if (StringUtils.hasText(medicalRecordQuery.getPatientName())) {
                queryWrapper.like(MedicalRecords::getUserName, medicalRecordQuery.getPatientName());
            }

            // 医生姓名模糊查询
            if (StringUtils.hasText(medicalRecordQuery.getDoctorName())) {
                queryWrapper.like(MedicalRecords::getDoctorName, medicalRecordQuery.getDoctorName());
            }

            // 状态精确查询
            if (medicalRecordQuery.getStatus() != null) {
                queryWrapper.eq(MedicalRecords::getStatus, medicalRecordQuery.getStatus());
            }

            // 时间范围查询 - 就诊时间
            if (StringUtils.hasText(medicalRecordQuery.getVisitStart()) || StringUtils.hasText(medicalRecordQuery.getVisitEnd())) {
                if (StringUtils.hasText(medicalRecordQuery.getVisitStart())) {
                    queryWrapper.apply("visit_date >= {0}", medicalRecordQuery.getVisitStart() + " 00:00:00");
                }
                if (StringUtils.hasText(medicalRecordQuery.getVisitEnd())) {
                    queryWrapper.apply("visit_date < {0}", medicalRecordQuery.getVisitEnd() + " 23:59:59");
                }
            }

            // 时间范围查询 - 完成时间
            if (StringUtils.hasText(medicalRecordQuery.getCompletedStart()) || StringUtils.hasText(medicalRecordQuery.getCompletedEnd())) {
                if (StringUtils.hasText(medicalRecordQuery.getCompletedStart())) {
                    queryWrapper.apply("completed_date >= {0}", medicalRecordQuery.getCompletedStart() + " 00:00:00");
                }
                if (StringUtils.hasText(medicalRecordQuery.getCompletedEnd())) {
                    queryWrapper.apply("completed_date < {0}", medicalRecordQuery.getCompletedEnd() + " 23:59:59");
                }
            }

        } else {
            // 默认查询条件：只查询已完成或已归档的病例（状态为1或2）
            queryWrapper.in(MedicalRecords::getStatus, 1, 2); // 1:已完成, 2:已归档
        }

        // 按就诊日期降序排列
        queryWrapper.orderByDesc(MedicalRecords::getVisitDate);

        // 执行分页查询
        Page<MedicalRecords> resultPage = this.page(page, queryWrapper);

        // 转换为PageVO对象
        List<MedicalRecordPageVO> voList = resultPage.getRecords().stream()
                .map(MedicalRecordPageVOConverter::toPageVO)
                .collect(Collectors.toList());

        // 构造并返回PageBean，使用数据库查询返回的总记录数
        return PageBean.of(voList, resultPage.getTotal(), param);
    }
}