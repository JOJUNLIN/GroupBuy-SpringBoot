package com.jojun.groupbuy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @className: Order
 * @author: JOJUN-CJL
 * @date: 2025/4/7
 * @Version: 1.0
 * @description: 订单实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    /** 订单ID */
    private String id;

    /** 用户ID */
    private String userId;

    /** 收货地址ID */
    private Integer addressId;

    /** 订单状态，1为待付款、2为待发货、3为待收货、4为配送中、5为已完成 */
    private Integer orderState;

    /** 商品总价 */
    private BigDecimal totalMoney;

    /** 运费 */
    private BigDecimal postFee;

    /** 应付金额 */
    private BigDecimal payMoney;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;
}
