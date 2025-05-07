package com.jojun.groupbuy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: ReceiveResultDto
 * @author: JOJUN-CJL
 * @date: 2025/5/7
 * @Version: 1.0
 * @description: 送达DTO
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiveResultDto {
    private int receivedCount; // 成功确认送达的数量
    private String operationMessage; // 操作结果消息
}
