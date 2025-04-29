package com.jojun.groupbuy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @className: OrderSkuItem
 * @author: JOJUN-CJL
 * @date: 2025/4/7
 * @Version: 1.0
 * @description: 订单商品信息DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSkuItem {
    /** sku id */
    private String skuId;

    /** 商品 id */
    private String goodsId;

    /** 商品名称 */
    private String goodsName;

    /** 商品属性文字 */
    private String skuName;

    /** 数量 */
    private Integer count;

    /** 购买时单价 */
    private BigDecimal price;

    /** 图片地址 */
    private String image;
}
