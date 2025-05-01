package com.jojun.groupbuy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

/**
 * @className: OrderItem
 * @author: JOJUN-CJL
 * @date: 2025/5/01
 * @Version: 1.0
 * @description: 订单列表项DTO
 */
@Data
@NoArgsConstructor
public class OrderItem extends OrderResult {
    /** 总件数 */
    private Integer totalNum;

    // 因为继承了OrderResult，不需要重复定义其他字段
    // 构造函数需要处理所有字段
    public OrderItem(String id, Integer orderState, List<OrderSkuItem> skus, String address,
                     String createTime, BigDecimal totalMoney, BigDecimal postFee,
                     BigDecimal payMoney, Integer totalNum) {
        super(id, orderState, skus, address, createTime, totalMoney, postFee, payMoney);
        this.totalNum = totalNum;
    }
}
