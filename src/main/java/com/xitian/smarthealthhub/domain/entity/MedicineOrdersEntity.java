package com.xitian.smarthealthhub.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xitian.smarthealthhub.bean.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 药品订单实体类
 *
 * @author 
 * @date 2025/02/04
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@TableName("medicine_orders")
public class MedicineOrdersEntity extends BaseEntity {
    /**
     * 订单号
     */
    @TableField("order_no")
    @Schema(description = "订单号")
    private String orderNo;

    /**
     * 用户ID
     */
    @TableField("user_id")
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 药品ID
     */
    @TableField("medicine_id")
    @Schema(description = "药品ID")
    private Long medicineId;

    /**
     * 药品名称快照
     */
    @TableField("medicine_name")
    @Schema(description = "药品名称快照")
    private String medicineName;

    /**
     * 价格快照
     */
    @TableField("price")
    @Schema(description = "价格快照")
    private BigDecimal price;

    /**
     * 数量
     */
    @TableField("quantity")
    @Schema(description = "数量")
    private Integer quantity;

    /**
     * 总金额
     */
    @TableField("total_amount")
    @Schema(description = "总金额")
    private BigDecimal totalAmount;

    /**
     * 状态：0-待支付, 1-已支付, 2-已取药, 3-已取消
     */
    @TableField("status")
    @Schema(description = "状态：0-待支付, 1-已支付, 2-已取药, 3-已取消")
    private Integer status;

    /**
     * 取药地点ID
     */
    @TableField("pharmacy_location_id")
    @Schema(description = "取药地点ID")
    private Long pharmacyLocationId;

    /**
     * 支付方式: wechat, alipay
     */
    @TableField("payment_method")
    @Schema(description = "支付方式: wechat, alipay")
    private String paymentMethod;

    /**
     * 支付时间
     */
    @TableField("payment_time")
    @Schema(description = "支付时间")
    private LocalDateTime paymentTime;
}
