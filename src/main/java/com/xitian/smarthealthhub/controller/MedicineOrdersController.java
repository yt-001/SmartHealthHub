package com.xitian.smarthealthhub.controller;

import com.xitian.smarthealthhub.bean.ResultBean;
import com.xitian.smarthealthhub.bean.StatusCode;
import com.xitian.smarthealthhub.domain.dto.MedicineOrderCreateDTO;
import com.xitian.smarthealthhub.domain.dto.MedicineOrderPayDTO;
import com.xitian.smarthealthhub.domain.entity.MedicineOrdersEntity;
import com.xitian.smarthealthhub.service.MedicineOrdersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicine-orders")
@Tag(name = "药品订单管理")
@Slf4j
public class MedicineOrdersController {

    @Autowired
    private MedicineOrdersService medicineOrdersService;

    @PostMapping("/create")
    @Operation(summary = "创建订单")
    public ResultBean<MedicineOrdersEntity> createOrder(@RequestBody MedicineOrderCreateDTO dto) {
        try {
            MedicineOrdersEntity order = medicineOrdersService.createOrder(
                    dto.getUserId(), dto.getMedicineId(), dto.getQuantity(), dto.getPharmacyLocationId());
            return ResultBean.success(order);
        } catch (Exception e) {
            log.error("创建订单失败", e);
            return ResultBean.fail(StatusCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "获取用户订单列表")
    public ResultBean<List<MedicineOrdersEntity>> getUserOrders(@PathVariable Long userId) {
        try {
            List<MedicineOrdersEntity> list = medicineOrdersService.getUserOrders(userId);
            return ResultBean.success(list);
        } catch (Exception e) {
            log.error("获取订单列表失败", e);
            return ResultBean.fail(StatusCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/pay")
    @Operation(summary = "支付订单")
    public ResultBean<Boolean> payOrder(@RequestBody MedicineOrderPayDTO dto) {
        try {
            boolean success = medicineOrdersService.payOrder(dto.getOrderId(), dto.getPaymentMethod());
            if (success) {
                return ResultBean.success(true);
            } else {
                return ResultBean.fail(StatusCode.INTERNAL_SERVER_ERROR, "支付失败");
            }
        } catch (Exception e) {
            log.error("支付订单失败", e);
            return ResultBean.fail(StatusCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
