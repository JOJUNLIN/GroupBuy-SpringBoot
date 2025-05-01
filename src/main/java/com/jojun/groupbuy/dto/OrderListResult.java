package com.jojun.groupbuy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * @className: OrderListResult
 * @author: JOJUN-CJL
 * @date: 2025/5/01
 * @Version: 1.0
 * @description: 订单列表返回结果DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderListResult {
    /** 总记录数 */
    private Long counts;
    /** 数据集合 [订单信息] */
    private List<OrderItem> items;
    /** 当前页码 */
    private Integer page;
    /** 总页数 */
    private Integer pages;
    /** 页尺寸 */
    private Integer pageSize;
}
