package com.jojun.groupbuy.dto;

import com.jojun.groupbuy.pojo.AddressItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @className: OrderPreResult
 * @author: JOJUN-CJL
 * @date: 2025/4/24
 * @Version: 1.0
 * @description: 预付订单返回结果DTO
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPreResult {
    /** 商品集合 */
    private List<OrderPreGoods> goods;

    /** 结算信息 */
    private OrderSummary summary;

    /** 地址列表 */
    private List<AddressItem> userAddresses;
}

