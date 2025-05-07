package com.jojun.groupbuy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: DispatchResultDto
 * @author: JOJUN-CJL
 * @date: 2025/5/7
 * @Version: 1.0
 * @description: 管理员发货DTO
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DispatchResultDto {
    private int dispatchedCount; // 成功发货的数量
    private String operationMessage; // 操作结果消息
}
