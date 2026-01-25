package com.xitian.smarthealthhub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.domain.entity.MedicineOrdersEntity;
import com.xitian.smarthealthhub.domain.query.MedicineOrdersQuery;

import java.util.List;

/**
 * 药品订单服务接口
 *
 * @author 
 * @date 2025/02/04
 */
public interface MedicineOrdersService extends IService<MedicineOrdersEntity> {
    /**
     * 分页查询
     * @param param 分页参数
     * @return 分页结果
     */
    PageBean<MedicineOrdersEntity> pageQuery(PageParam<MedicineOrdersQuery> param);

    /**
     * 创建订单
     * @param userId 用户ID
     * @param medicineId 药品ID
     * @param quantity 数量
     * @param pharmacyLocationId 取药地点ID
     * @return 订单实体
     */
    MedicineOrdersEntity createOrder(Long userId, Long medicineId, Integer quantity, Long pharmacyLocationId);

    /**
     * 支付订单
     * @param orderId 订单ID
     * @param paymentMethod 支付方式
     * @return 是否成功
     */
    boolean payOrder(Long orderId, String paymentMethod);

    /**
     * 获取用户订单列表
     * @param userId 用户ID
     * @return 订单列表
     */
    List<MedicineOrdersEntity> getUserOrders(Long userId);
}
