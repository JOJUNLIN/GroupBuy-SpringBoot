package com.jojun.groupbuy.controller;

import com.jojun.groupbuy.dto.OrderResult;
import com.jojun.groupbuy.pojo.Result;
import com.jojun.groupbuy.service.OrderService;
import com.jojun.groupbuy.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @className: OrderStatusController
 * @author: JOJUN-CJL
 * @date: 2025/5/01
 * @Version: 1.0
 * @description: 订单状态控制器，处理各种订单状态的修改
 */
@RestController
@RequestMapping("/user/order/status")
public class OrderStatusController {

    @Autowired
    private OrderService orderService;

    /**
     * 修改订单状态
     * @param orderId 订单ID
     * @param status 目标状态值
     * @param request HTTP请求对象，用于获取请求头中的 token
     * @return 修改结果
     */
    @PutMapping("/{orderId}")
    public Result<Boolean> updateOrderStatus(
            @PathVariable String orderId,
            @RequestParam Integer status,
            HttpServletRequest request) {
        try {
            // 从请求头获取 token
            String token = request.getHeader("Authorization");
            if (token == null || token.isEmpty()) {
                return Result.fail("未提供登录凭证");
            }

            // 解析 token 获取用户ID
            Map<String, Object> claims = JwtUtil.parseToken(token);
            Integer userId = (Integer) claims.get("id");

            if (userId == null) {
                return Result.fail("无效的用户信息");
            }

            // 验证参数
            if (orderId == null || orderId.isEmpty()) {
                return Result.fail("订单ID不能为空");
            }

            if (status == null || status < 1 || status > 5) {
                return Result.fail("无效的订单状态值");
            }

            // 修改订单状态
            boolean result = orderService.updateOrderState(orderId, status);
            if (result) {
                return Result.success(true);
            } else {
                return Result.fail("订单不存在或状态修改失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("修改订单状态失败: " + e.getMessage());
        }
    }

    /**
     * 支付（将订单状态从待付款改为待发货）
     * @param orderId 订单ID
     * @param request HTTP请求对象，用于获取请求头中的 token
     * @return 修改结果
     */
    @PutMapping("/pay/{orderId}")
    public Result<Boolean> payOrder(@PathVariable String orderId, HttpServletRequest request) {
        try {
            // 从请求头获取 token
            String token = request.getHeader("Authorization");
            if (token == null || token.isEmpty()) {
                return Result.fail("未提供登录凭证");
            }

            // 解析 token 获取用户ID
            Map<String, Object> claims = JwtUtil.parseToken(token);
            Integer userId = (Integer) claims.get("id");

            if (userId == null) {
                return Result.fail("无效的用户信息");
            }

            // 将订单状态从待付款(1)改为待发货(2)
            boolean result = orderService.updateOrderState(orderId, 2);
            if (result) {
                return Result.success(true);
            } else {
                return Result.fail("订单不存在或状态修改失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("订单支付失败: " + e.getMessage());
        }
    }

    /**
     * 订单发货（将订单状态从待发货改为配送中）
     * @param orderId 订单ID
     * @param request HTTP请求对象，用于获取请求头中的 token
     * @return 修改结果
     */
    @PutMapping("/ship/{orderId}")
    public Result<Boolean> shipOrder(@PathVariable String orderId, HttpServletRequest request) {
        try {
            // 从请求头获取 token
            String token = request.getHeader("Authorization");
            if (token == null || token.isEmpty()) {
                return Result.fail("未提供登录凭证");
            }

            // 解析 token 获取用户ID
            Map<String, Object> claims = JwtUtil.parseToken(token);
            Integer userId = (Integer) claims.get("id");

            if (userId == null) {
                return Result.fail("无效的用户信息");
            }

            // 将订单状态从待发货(2)改为配送中(4)
            boolean result = orderService.updateOrderState(orderId, 4);
            if (result) {
                return Result.success(true);
            } else {
                return Result.fail("订单不存在或状态修改失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("订单发货失败: " + e.getMessage());
        }
    }

    /**
     * 确认收货（将订单状态从待收货改为已完成）
     * @param orderId 订单ID
     * @param request HTTP请求对象，用于获取请求头中的 token
     * @return 修改结果
     */
    @PutMapping("/receive/{orderId}")
    public Result<OrderResult> receiveOrder(@PathVariable String orderId, HttpServletRequest request) {
        try {
            // 从请求头获取 token
            String token = request.getHeader("Authorization");
            if (token == null || token.isEmpty()) {
                return Result.fail("未提供登录凭证");
            }

            // 解析 token 获取用户ID
            Map<String, Object> claims = JwtUtil.parseToken(token);
            Integer userId = (Integer) claims.get("id");

            if (userId == null) {
                return Result.fail("无效的用户信息");
            }

            // 将订单状态从待收货(3)改为已完成(5)
            boolean result = orderService.updateOrderState(orderId, 5);
            if (result) {
                // 获取更新后的订单信息
                OrderResult updatedOrder = orderService.getOrderById(String.valueOf(userId), orderId);
                return Result.success(updatedOrder);
            } else {
                return Result.fail("订单不存在或状态修改失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("确认收货失败: " + e.getMessage());
        }
    }

    /**
     * 送达订单（将订单状态从配送中改为待收货）
     * @param orderId 订单ID
     * @param request HTTP请求对象，用于获取请求头中的 token
     * @return 修改结果
     */
    @PutMapping("/arrive/{orderId}")
    public Result<Boolean> commentOrder(@PathVariable String orderId, HttpServletRequest request) {
        try {
            // 从请求头获取 token
            String token = request.getHeader("Authorization");
            if (token == null || token.isEmpty()) {
                return Result.fail("未提供登录凭证");
            }

            // 解析 token 获取用户ID
            Map<String, Object> claims = JwtUtil.parseToken(token);
            Integer userId = (Integer) claims.get("id");

            if (userId == null) {
                return Result.fail("无效的用户信息");
            }

            // 将订单状态从配送中(4)改为待收货(3)
            boolean result = orderService.updateOrderState(orderId, 3);
            if (result) {
                return Result.success(true);
            } else {
                return Result.fail("订单不存在或状态修改失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("评价订单失败: " + e.getMessage());
        }
    }

    /**
     * 取消订单（将订单状态改为已取消）
     * @param orderId 订单ID
     * @param request HTTP请求对象，用于获取请求头中的 token
     * @return 修改结果
     */
    @PutMapping("/cancel/{orderId}")
    public Result<Boolean> cancelOrder(@PathVariable String orderId, HttpServletRequest request) {
        try {
            // 从请求头获取 token
            String token = request.getHeader("Authorization");
            if (token == null || token.isEmpty()) {
                return Result.fail("未提供登录凭证");
            }

            // 解析 token 获取用户ID
            Map<String, Object> claims = JwtUtil.parseToken(token);
            Integer userId = (Integer) claims.get("id");

            if (userId == null) {
                return Result.fail("无效的用户信息");
            }

            // 将订单状态改为已取消(6)
            boolean result = orderService.updateOrderState(orderId, 6);
            if (result) {
                return Result.success(true);
            } else {
                return Result.fail("订单不存在或状态修改失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("取消订单失败: " + e.getMessage());
        }
    }
}
