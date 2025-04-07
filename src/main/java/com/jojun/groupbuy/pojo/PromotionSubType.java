package com.jojun.groupbuy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: PromotionSubType
 * @author: JOJUN-CJL
 * @date: 2025/4/4
 * @Version: 1.0
 * @description: 推荐子类型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionSubType {
    private String id;      // 子类型ID
    private String title;   // 子类型标题
    private PageResult<Product> goodsItems; // 商品列表
}
