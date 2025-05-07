package com.jojun.groupbuy.service;

import com.jojun.groupbuy.dto.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 更新订单状态
     * @param orderId 订单ID
     * @param state 订单状态
     * @return 是否更新成功
     */
    boolean updateOrderState(String orderId, Integer state);

    /**
     * 获取订单列表
     * @param userId 用户ID
     * @param page 页码
     * @param pageSize 每页条数
     * @param orderState 订单状态，0表示查询全部
     * @return 订单列表结果
     */
    OrderListResult getOrderList(String userId, Integer page, Integer pageSize, Integer orderState);

    /**
     * (管理员) 获取所有订单列表
     * @param page 页码
     * @param pageSize 每页条数
     * @param orderState 订单状态，null或0表示查询全部
     * @return 管理员订单列表结果
     */
    AdminOrderListResultDto getAllOrdersForAdmin(Integer page, Integer pageSize, Integer orderState);

    /**
     * (管理员) 根据站点ID获取订单列表
     * @param addressId 站点ID
     * @param page 页码
     * @param pageSize 每页条数
     * @param orderState 订单状态，null或0表示查询全部
     * @return 管理员订单列表结果
     */
    AdminOrderListResultDto getOrdersBySiteForAdmin(Integer addressId, Integer page, Integer pageSize, Integer orderState);

    /**
     * (管理员 - 发货管理) 批量将指定站点下的一批待发货订单更新为配送中
     * @param siteId 站点ID
     * @param orderIds 要发货的订单ID列表
     * @return 实际更新成功的订单数量
     */
    int dispatchOrders(@Param("siteId") Integer siteId, @Param("orderIds") List<String> orderIds);

    /**
     * (管理员 - 送达管理) 批量将指定站点下的一批配送中订单更新为待收货
     * @param siteId 站点ID
     * @param orderIds 要确认送达的订单ID列表
     * @return 实际更新成功的订单数量
     */
    int receiveOrders(@Param("siteId") Integer siteId, @Param("orderIds") List<String> orderIds);
}