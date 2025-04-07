package com.jojun.groupbuy.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @className: Cart
 * @author: JOJUN-CJL
 * @date: 2025/4/7
 * @Version: 1.0
 * @description: 购物车实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    private Long id;          // 购物车ID
    private String userId;    // 用户ID
    private String skuId;     // 商品SKU ID
    private String goodsId;   // 商品ID
    private String goodsName; // 商品名称
    private String image;     // 商品图片
    private BigDecimal price; // 商品价格
    private Integer count;    // 商品数量
    private List<String> skuName; // 规格名称
    private Integer stock;    // 库存
    private Date createTime;  // 创建时间
    private Date updateTime;  // 更新时间

    @JsonIgnore
    private String skuNameJson; // 数据库中的 JSON 字符串

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // 将数据库中的 JSON 字符串转换为 Java List
    public void setSkuNameJson(String skuNameJson) {
        this.skuNameJson = skuNameJson;
        if (skuNameJson != null) {
            try {
                this.skuName = objectMapper.readValue(skuNameJson, new TypeReference<List<String>>() {});
            } catch (JsonProcessingException e) {
                // 处理异常
                this.skuName = null;
            }
        }
    }

    // 将 Java List 转换为 JSON 字符串存入数据库
    public String getSkuNameJson() {
        if (this.skuName != null) {
            try {
                return objectMapper.writeValueAsString(this.skuName);
            } catch (JsonProcessingException e) {
                // 处理异常
                return null;
            }
        }
        return null;
    }
}
