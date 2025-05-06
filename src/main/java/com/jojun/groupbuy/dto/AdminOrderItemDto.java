package com.jojun.groupbuy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime; // 如果直接返回 LocalDateTime
import java.math.BigDecimal;    // 如果订单状态有对应金额展示（虽然你没要求，但OrderItem有）


/**
 * 管理员订单列表项 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminOrderItemDto {
    private String id;          // 订单ID
    private String userId;      // 用户ID
    private String siteAddress; // 站点地址/名称
    private Integer orderState; // 订单状态 (1:待付款, 2:待发货, 3:待收货, 4:配送中, 5:已完成)
    private String createTime;  // 创建时间 (格式化后的字符串)
}
