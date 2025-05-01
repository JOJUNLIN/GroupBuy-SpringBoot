package com.jojun.groupbuy.controller;

import com.jojun.groupbuy.dto.*;
import com.jojun.groupbuy.pojo.Result;
import com.jojun.groupbuy.service.OrderService;
import com.jojun.groupbuy.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @className: OrderController
 * @author: JOJUN-CJL
 * @date: 2025/4/24
 * @Version: 1.0
 * @description: 订单控制器
 */
@RestController
@RequestMapping("/user/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 获取预付订单信息
     * @param request HTTP请求对象，用于获取请求头中的 token
     * @return 预付订单信息
     */
    @GetMapping("/pre")
    public Result<OrderPreResult> getOrderPre(HttpServletRequest request) {
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

            // 获取预付订单信息
            OrderPreResult orderPreResult = orderService.getOrderPre(userId.toString());
            return Result.success(orderPreResult);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("获取预付订单信息失败: " + e.getMessage());
        }
    }

    /**
     * 提交订单
     * @param params 订单参数
     * @param request HTTP请求对象，用于获取请求头中的 token
     * @return 订单创建结果
     */
    @PostMapping
    public Result<OrderCreateResult> createOrder(@RequestBody OrderCreateParams params,
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
            if (params.getAddressId() == null) {
                return Result.fail("请选择收货地址");
            }

            if (params.getGoods() == null || params.getGoods().isEmpty()) {
                return Result.fail("订单商品不能为空");
            }

            // 创建订单
            OrderCreateResult result = orderService.createOrder(userId.toString(), params);
            return Result.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("创建订单失败: " + e.getMessage());
        }
    }

    /**
     * 获取订单详情
     * @param id 订单ID
     * @param request HTTP请求对象，用于获取请求头中的 token
     * @return 订单详情
     */
    @GetMapping("/{id}")
    public Result<OrderResult> getOrderById(@PathVariable String id, HttpServletRequest request) {
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
            if (id == null || id.isEmpty()) {
                return Result.fail("订单ID不能为空");
            }

            // 获取订单详情
            OrderResult result = orderService.getOrderById(userId.toString(), id);
            if (result == null) {
                return Result.fail("订单不存在或无权访问");
            }

            return Result.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("获取订单详情失败: " + e.getMessage());
        }
    }

    /**
     * 获取订单列表
     * @param page 页码，默认为1
     * @param pageSize 每页显示数量，默认为10
     * @param orderState 订单状态，0表示全部
     * @param request HTTP请求对象，用于获取请求头中的 token
     * @return 订单列表结果
     */
    @GetMapping("/list")
    public Result<OrderListResult> getOrderList(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "orderState", required = false, defaultValue = "0") Integer orderState,
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

            // 获取订单列表
            OrderListResult result = orderService.getOrderList(userId.toString(), page, pageSize, orderState);
            return Result.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("获取订单列表失败: " + e.getMessage());
        }
    }
}