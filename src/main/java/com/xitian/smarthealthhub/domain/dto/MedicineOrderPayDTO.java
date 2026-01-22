package com.xitian.smarthealthhub.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "支付药品订单DTO")
public class MedicineOrderPayDTO {
    @Schema(description = "订单ID")
    private Long orderId;

    @Schema(description = "支付方式: wechat, alipay")
    private String paymentMethod;
}
