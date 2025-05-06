package com.jojun.groupbuy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 管理员订单列表结果 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminOrderListResultDto {
    private Long totalCount;          // 总记录数
    private List<AdminOrderItemDto> items; // 当前页订单列表
    private Integer page;             // 当前页码
    private Integer totalPages;       // 总页数
    private Integer pageSize;         // 每页数量
}