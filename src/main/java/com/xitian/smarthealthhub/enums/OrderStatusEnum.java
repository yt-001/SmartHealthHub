package com.xitian.smarthealthhub.enums;

import lombok.Getter;

/**
 * 订单状态枚举
 */
@Getter
public enum OrderStatusEnum {
    PENDING_PAYMENT(0, "待支付"),
    PAID(1, "已支付"),
    PICKED_UP(2, "已取药"),
    CANCELLED(3, "已取消");

    private final int code;
    private final String description;

    OrderStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static OrderStatusEnum getByCode(int code) {
        for (OrderStatusEnum status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null;
    }
}
