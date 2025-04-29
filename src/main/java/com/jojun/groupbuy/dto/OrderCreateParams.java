package com.jojun.groupbuy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @className: OrderCreateParams
 * @author: JOJUN-CJL
 * @date: 2025/4/7
 * @Version: 1.0
 * @description: 提交订单请求参数DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateParams {
    /** 所选地址Id */
    private Integer addressId;

    /** 商品集合[ 商品信息 ] */
    private List<OrderPreGoods> goods;

    /** 商品总价 */
    private Double totalPrice;

    /** 邮费 */
    private Double postFee;

    /** 应付金额 */
    private Double totalPayPrice;
}
