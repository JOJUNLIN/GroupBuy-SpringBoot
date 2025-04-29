package com.jojun.groupbuy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @className: OrderGoods
 * @author: JOJUN-CJL
 * @date: 2025/4/7
 * @Version: 1.0
 * @description: 订单商品实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderGoods {
    /** 主键ID */
    private Long id;

    /** 订单ID */
    private String orderId;

    /** 商品ID */
    private String goodsId;

    /** 商品名称 */
    private String goodsName;

    /** SKU ID */
    private String skuId;

    /** SKU名称，例如"颜色:瓷白色 尺寸：8寸" */
    private String skuName;

    /** 商品图片 */
    private String image;

    /** 购买时单价 */
    private BigDecimal price;

    /** 购买数量 */
    private Integer count;

    /** 小计总价 */
    private BigDecimal totalPrice;

    /** 实付价格小计 */
    private BigDecimal totalPayPrice;
}
