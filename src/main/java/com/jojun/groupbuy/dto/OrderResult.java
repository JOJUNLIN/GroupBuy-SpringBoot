package com.jojun.groupbuy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @className: OrderResult
 * @author: JOJUN-CJL
 * @date: 2025/4/7
 * @Version: 1.0
 * @description: 订单详情返回信息DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResult {
    /** 订单编号 */
    private String id;

    /** 订单状态，1为待付款、2为待发货、3为待收货、4为待评价、5为已完成、6为已取消 */
    private Integer orderState;

    /** 商品集合 [ 商品信息 ] */
    private List<OrderSkuItem> skus;

    /** 下单时间 */
    private String createTime;

    /** 商品总价 */
    private BigDecimal totalMoney;

    /** 运费 */
    private BigDecimal postFee;

    /** 应付金额 */
    private BigDecimal payMoney;
}
