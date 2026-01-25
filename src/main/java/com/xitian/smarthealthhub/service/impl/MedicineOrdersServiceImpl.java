package com.xitian.smarthealthhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.domain.entity.MedicineOrdersEntity;
import com.xitian.smarthealthhub.domain.query.MedicineOrdersQuery;
import com.xitian.smarthealthhub.domain.vo.MedicinesVo;
import com.xitian.smarthealthhub.mapper.MedicineOrdersMapper;
import com.xitian.smarthealthhub.service.MedicineOrdersService;
import com.xitian.smarthealthhub.service.MedicinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 药品订单服务实现类
 *
 * @author 
 * @date 2025/02/04
 */
@Service
public class MedicineOrdersServiceImpl extends ServiceImpl<MedicineOrdersMapper, MedicineOrdersEntity> implements MedicineOrdersService {

    @Autowired
    private MedicinesService medicinesService;

    @Override
    public PageBean<MedicineOrdersEntity> pageQuery(PageParam<MedicineOrdersQuery> param) {
        Page<MedicineOrdersEntity> page = new Page<>(param.getPageNum(), param.getPageSize());
        LambdaQueryWrapper<MedicineOrdersEntity> wrapper = new LambdaQueryWrapper<>();
        
        if (param.getQuery() != null) {
            MedicineOrdersQuery query = param.getQuery();
            if (StringUtils.hasText(query.getOrderNo())) {
                wrapper.like(MedicineOrdersEntity::getOrderNo, query.getOrderNo());
            }
            if (StringUtils.hasText(query.getMedicineName())) {
                wrapper.like(MedicineOrdersEntity::getMedicineName, query.getMedicineName());
            }
            if (query.getStatus() != null) {
                wrapper.eq(MedicineOrdersEntity::getStatus, query.getStatus());
            }
        }
        
        wrapper.orderByDesc(MedicineOrdersEntity::getCreatedAt);
        
        this.page(page, wrapper);
        return PageBean.of(page.getRecords(), page.getTotal(), param);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MedicineOrdersEntity createOrder(Long userId, Long medicineId, Integer quantity, Long pharmacyLocationId) {
        MedicinesVo medicine = medicinesService.getById(medicineId);
        if (medicine == null) {
            throw new RuntimeException("药品不存在");
        }

        MedicineOrdersEntity order = new MedicineOrdersEntity();
        order.setUserId(userId);
        order.setMedicineId(medicineId);
        order.setMedicineName(medicine.getName());
        order.setPrice(medicine.getPrice());
        order.setQuantity(quantity);
        order.setTotalAmount(medicine.getPrice().multiply(new BigDecimal(quantity)));
        order.setStatus(0); // 待支付
        order.setPharmacyLocationId(pharmacyLocationId);
        
        // 生成订单号: yyyyMMddHHmmss + 随机数
        String timeStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String randomStr = String.format("%04d", ThreadLocalRandom.current().nextInt(10000));
        order.setOrderNo("M" + timeStr + randomStr);

        this.save(order);
        return order;
    }

    @Override
    public boolean payOrder(Long orderId, String paymentMethod) {
        MedicineOrdersEntity order = this.getById(orderId);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        if (order.getStatus() != 0) {
            throw new RuntimeException("订单状态不正确");
        }

        order.setStatus(1); // 已支付
        order.setPaymentMethod(paymentMethod);
        order.setPaymentTime(LocalDateTime.now());
        return this.updateById(order);
    }

    @Override
    public List<MedicineOrdersEntity> getUserOrders(Long userId) {
        LambdaQueryWrapper<MedicineOrdersEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MedicineOrdersEntity::getUserId, userId)
               .orderByDesc(MedicineOrdersEntity::getCreatedAt);
        return this.list(wrapper);
    }
}
