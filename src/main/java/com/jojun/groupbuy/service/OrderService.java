package com.jojun.groupbuy.service;

import com.jojun.groupbuy.dto.OrderCreateParams;
import com.jojun.groupbuy.dto.OrderCreateResult;
import com.jojun.groupbuy.dto.OrderPreResult;
import com.jojun.groupbuy.dto.OrderResult;

/**
 * @interfaceName: OrderService
 * @author: JOJUN-CJL
 * @date: 2025/4/24
 * @Version: 1.0
 * @description: 订单服务接口
 */
public interface OrderService {

    /**
     * 获取预付订单信息
     * @param userId 用户ID
     * @return 预付订单信息
     */
    OrderPreResult getOrderPre(String userId);

    /**
     * 创建订单
     * @param userId 用户ID
     * @param params 创建订单参数
     * @return 创建订单结果
     */
    OrderCreateResult createOrder(String userId, OrderCreateParams params);

    /**
     * 获取订单详情
     * @param userId 用户ID
     * @param id 订单ID
     * @return 订单详情
     */
    OrderResult getOrderById(String userId, String id);
}