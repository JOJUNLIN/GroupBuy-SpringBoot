package com.jojun.groupbuy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @className: CartAddRequest
 * @author: JOJUN-CJL
 * @date: 2025/4/7
 * @Version: 1.0
 * @description: 购物车添加请求DTO
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartAddRequest {

    private String userId;   // 用户ID

    private String skuId;   // 商品SKU ID

    private Integer count;   // 数量

    @JsonProperty("goods_id")
    private String goodsId;   // 商品ID

    @JsonProperty("goods_name")
    private String goodsName;   // 商品名称

    private String image;   // 商品图片

    private BigDecimal price;   // 价格

    @JsonProperty("sku_name")
    private List<String> skuName;   // SKU规格名称列表

    private Integer stock;   // 库存

    private Boolean selected = false; // 是否选中，默认为false

}
