package com.xitian.smarthealthhub.enums;

import lombok.Getter;

/**
 * 支付方式枚举
 */
@Getter
public enum PaymentMethodEnum {
    WECHAT("WeChat", "微信支付"),
    ALIPAY("Alipay", "支付宝");

    private final String code;
    private final String description;

    PaymentMethodEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static PaymentMethodEnum getByCode(String code) {
        for (PaymentMethodEnum method : values()) {
            if (method.getCode().equalsIgnoreCase(code)) {
                return method;
            }
        }
        return null;
    }
}
