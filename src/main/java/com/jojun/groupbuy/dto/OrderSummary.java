package com.jojun.groupbuy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @className: OrderSummary
 * @author: JOJUN-CJL
 * @date: 2025/4/7
 * @Version: 1.0
 * @description: 结算信息DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSummary {
    /** 商品总价 */
    private BigDecimal totalPrice;

    /** 邮费 */
    private BigDecimal postFee;

    /** 应付金额 */
    private BigDecimal totalPayPrice;
}
