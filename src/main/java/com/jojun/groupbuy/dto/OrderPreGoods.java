package com.jojun.groupbuy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: OrderPreGoods
 * @author: JOJUN-CJL
 * @date: 2025/4/7
 * @Version: 1.0
 * @description: 商品信息DTO
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPreGoods {
    /** 属性文字，例如"颜色:瓷白色 尺寸：8寸" */
    private String skuName;

    /** 数量 */
    private Integer count;

    /** 商品 ID */
    private String goodsId;

    /** 商品名称 */
    private String goodsName;

    /** 图片 */
    private String image;

    /** 单价 */
    private String price;

    /** SKUID */
    private String skuId;

    /** 实付价格小计 */
    private String totalPayPrice;

    /** 小计总价 */
    private String totalPrice;
}
