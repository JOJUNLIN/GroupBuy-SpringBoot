package com.jojun.groupbuy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: Product
 * @author: JOJUN-CJL
 * @date: 2025/4/1
 * @Version: 1.0
 * @description: 商品实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private String id;      // 商品ID
    private String name;    // 商品名称
    private String desc;    // 商品描述
    private String price;   // 商品价格
    private String picture; // 商品图片
    private Integer orderNum; // 订单数量/销量
    private String discount; //折扣
}
