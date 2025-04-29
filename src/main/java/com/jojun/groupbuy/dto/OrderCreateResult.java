package com.jojun.groupbuy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: OrderCreateResult
 * @author: JOJUN-CJL
 * @date: 2025/4/7
 * @Version: 1.0
 * @description: 提交订单返回信息DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateResult {
    /** 订单Id */
    private String id;
}
