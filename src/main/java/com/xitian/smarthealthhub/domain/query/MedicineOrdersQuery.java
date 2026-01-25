package com.xitian.smarthealthhub.domain.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 药品订单查询类
 *
 * @author 
 * @date 2026/01/25
 */
@Getter
@Setter
@ToString
public class MedicineOrdersQuery {
    /**
     * 订单号（模糊查询）
     */
    @Schema(description = "订单号（模糊查询）")
    private String orderNo;

    /**
     * 药品名称（模糊查询）
     */
    @Schema(description = "药品名称（模糊查询）")
    private String medicineName;

    /**
     * 状态：0-待支付, 1-已支付, 2-已取药, 3-已取消
     */
    @Schema(description = "状态：0-待支付, 1-已支付, 2-已取药, 3-已取消")
    private Integer status;
}
