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

import java.util.List;
import java.util.Map;

/**
 * @className: OrderController
 * @author: JOJUN-CJL
 * @date: 2025/4/24
 * @Version: 1.0
 * @description: 订单控制器
 */
@RestController
@RequestMapping
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 定义订单状态常量，用于 Controller 中指定查询或更新状态
    private static final int ORDER_STATE_TO_BE_SHIPPED = 2; // 待发货
    private static final int ORDER_STATE_IN_DELIVERY = 4; // 配送中
    private static final int ORDER_STATE_TO_BE_RECEIVED = 3; // 待收货

    /**
     * 获取预付订单信息
     * @param request HTTP请求对象，用于获取请求头中的 token
     * @return 预付订单信息
     */
    @GetMapping("/user/order/pre")
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
    @PostMapping("/user/order")
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
    @GetMapping("/user/order/{id}")
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
    @GetMapping("/user/order/list")
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

    // --- 管理员接口 ---

    /**
     * (管理员) 获取所有订单列表
     * @param page 页码
     * @param pageSize 每页数量
     * @param orderState 订单状态 (可选)
     * @return 订单列表结果 (使用 AdminOrderListResultDto)
     */
    @GetMapping("/attendant/orders")
    public Result<AdminOrderListResultDto> getAllOrdersForAdmin(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Integer orderState) {
//        log.info("Admin request to get all orders. Page: {}, PageSize: {}, State: {}", page, pageSize, orderState);
        try {
            AdminOrderListResultDto result = orderService.getAllOrdersForAdmin(page, pageSize, orderState);
            return Result.success(result);
        } catch (Exception e) {
//            log.error("管理员获取所有订单列表失败", e);
            return Result.fail("获取订单列表失败: " + e.getMessage());
        }
    }

    /**
     * (管理员) 根据站点ID获取订单列表
     * @param siteId 站点ID (路径参数)
     * @param page 页码
     * @param pageSize 每页数量
     * @param orderState 订单状态 (可选)
     * @return 订单列表结果 (使用 AdminOrderListResultDto)
     */
    @GetMapping("/attendant/orders/site/{siteId}")
    public Result<AdminOrderListResultDto> getOrdersBySiteForAdmin(
            @PathVariable("siteId") Integer siteId,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Integer orderState) {
//        log.info("Admin request to get orders for siteId: {}. Page: {}, PageSize: {}, State: {}", siteId, page, pageSize, orderState);
        if (siteId == null) {
            return Result.fail("站点ID不能为空");
        }
        try {
            AdminOrderListResultDto result = orderService.getOrdersBySiteForAdmin(siteId, page, pageSize, orderState);
            return Result.success(result);
        } catch (Exception e) {
//            log.error("管理员获取站点 {} 的订单列表失败", siteId, e);
            return Result.fail("获取订单列表失败: " + e.getMessage());
        }
    }

    // --- 订单发货管理接口 (/admin/dispatch) ---

    /**
     * (管理员 - 发货管理) 查询所有待发货订单
     * @param page 页码
     * @param pageSize 每页数量
     * @return 订单列表结果
     */
    @GetMapping("/attendant/dispatch/orders")
    public Result<AdminOrderListResultDto> getAllDispatchOrdersForAdmin(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
//        log.info("Admin request to get all dispatch orders. Page: {}, PageSize: {}", page, pageSize);
        try {
            AdminOrderListResultDto result = orderService.getAllOrdersForAdmin(page, pageSize, ORDER_STATE_TO_BE_SHIPPED);
            return Result.success(result);
        } catch (Exception e) {
//            log.error("管理员获取所有待发货订单列表失败", e);
            return Result.fail("获取待发货订单列表失败: " + e.getMessage());
        }
    }

    /**
     * (管理员 - 发货管理) 查询特定站点的待发货订单
     * @param siteId 站点ID (路径参数)
     * @param page 页码
     * @param pageSize 每页数量
     * @return 订单列表结果
     */
    @GetMapping("/attendant/dispatch/site/{siteId}/orders") // 新端点，明确是待发货 for site
    public Result<AdminOrderListResultDto> getDispatchOrdersBySiteForAdmin(
            @PathVariable("siteId") Integer siteId,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
//        log.info("Admin request to get dispatch orders for siteId: {}. Page: {}, PageSize: {}", siteId, page, pageSize);
        if (siteId == null) {
            return Result.fail("站点ID不能为空");
        }
        try {
            AdminOrderListResultDto result = orderService.getOrdersBySiteForAdmin(siteId, page, pageSize, ORDER_STATE_TO_BE_SHIPPED);
            return Result.success(result);
        } catch (Exception e) {
//            log.error("管理员获取站点 {} 的待发货订单列表失败", siteId, e);
            return Result.fail("获取待发货订单列表失败: " + e.getMessage());
        }
    }

    /**
     * (管理员 - 发货管理) 为特定站点的待发货订单配送 (批量更新状态为配送中)
     * @param siteId 站点ID (路径参数)
     * @param orderIds 要配送的订单ID列表 (请求体)
     * @return 统一响应结果，包含实际更新成功的订单数量
     */
    @PostMapping("/attendant/dispatch/site/{siteId}/dispatch") // 新端点，执行发货操作
    public Result<DispatchResultDto> dispatchOrdersForSite( // 返回类型改为 DispatchResultDto
                                                            @PathVariable("siteId") Integer siteId,
                                                            @RequestBody List<String> orderIds) {
//        log.info("Admin request to dispatch orders for siteId: {}. Order IDs: {}", siteId, orderIds);
        if (siteId == null) {
            return Result.fail("站点ID不能为空");
        }
        if (orderIds == null || orderIds.isEmpty()) {
            return Result.fail("要发货的订单ID列表不能为空");
        }
        try {
            int updatedCount = orderService.dispatchOrders(siteId, orderIds);
//            log.info("Dispatched {} orders for siteId: {}", updatedCount, siteId);

            String message = "操作成功";
            if (updatedCount < orderIds.size() && updatedCount > 0) {
                message = "部分订单发货成功，部分订单状态不符或不存在。实际发货数量: " + updatedCount;
            } else if (updatedCount == 0 && !orderIds.isEmpty()) {
                message = "没有订单被发货，请检查订单状态或站点ID。";
            }
            // 创建一个新的 DTO 来包含数量和自定义消息
            DispatchResultDto dispatchResult = new DispatchResultDto(updatedCount, message);
            return Result.success(dispatchResult);

        } catch (IllegalArgumentException e) {
//            log.warn("Dispatch orders failed due to invalid argument: {}", e.getMessage());
            return Result.fail(e.getMessage());
        } catch (Exception e) {
//            log.error("管理员批量发货失败 for site {}", siteId, e);
            return Result.fail("批量发货失败: " + e.getMessage());
        }
    }


    // --- 订单送达管理接口 (/admin/delivery) ---

    /**
     * (管理员 - 送达管理) 查询特定站点运送中的订单
     * @param siteId 站点ID (路径参数)
     * @param page 页码
     * @param pageSize 每页数量
     * @return 订单列表结果
     */
    @GetMapping("/attendant/delivery/site/{siteId}/orders") // 新端点，明确是运送中 for site
    public Result<AdminOrderListResultDto> getDeliveryOrdersBySiteForAdmin(
            @PathVariable("siteId") Integer siteId,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
//        log.info("Admin request to get delivery orders for siteId: {}. Page: {}, PageSize: {}", siteId, page, pageSize);
        if (siteId == null) {
            return Result.fail("站点ID不能为空");
        }
        try {
            AdminOrderListResultDto result = orderService.getOrdersBySiteForAdmin(siteId, page, pageSize, ORDER_STATE_IN_DELIVERY);
            return Result.success(result);
        } catch (Exception e) {
//            log.error("管理员获取站点 {} 的运送中订单列表失败", siteId, e);
            return Result.fail("获取运送中订单列表失败: " + e.getMessage());
        }
    }

    /**
     * (管理员 - 送达管理) 确认特定站点某批订单的送达 (批量更新状态为待收货)
     * @param siteId 站点ID (路径参数)
     * @param orderIds 要确认送达的订单ID列表 (请求体)
     * @return 统一响应结果，包含实际更新成功的订单数量
     */
    @PostMapping("/attendant/delivery/site/{siteId}/receive") // 新端点，执行确认送达操作
    public Result<ReceiveResultDto> receiveOrdersForSite( // 返回类型改为 ReceiveResultDto
                                                          @PathVariable("siteId") Integer siteId,
                                                          @RequestBody List<String> orderIds) {
//        log.info("Admin request to receive orders for siteId: {}. Order IDs: {}", siteId, orderIds);
        if (siteId == null) {
            return Result.fail("站点ID不能为空");
        }
        if (orderIds == null || orderIds.isEmpty()) {
            return Result.fail("要确认送达的订单ID列表不能为空");
        }
        try {
            int updatedCount = orderService.receiveOrders(siteId, orderIds);
//            log.info("Received {} orders for siteId: {}", updatedCount, siteId);

            String message = "操作成功";
            if (updatedCount < orderIds.size() && updatedCount > 0) {
                message = "部分订单确认送达成功，部分订单状态不符或不存在。实际送达数量: " + updatedCount;
            } else if (updatedCount == 0 && !orderIds.isEmpty()) {
                message = "没有订单被确认送达，请检查订单状态或站点ID。";
            }
            ReceiveResultDto receiveResult = new ReceiveResultDto(updatedCount, message);
            return Result.success(receiveResult);

        } catch (IllegalArgumentException e) {
//            log.warn("Receive orders failed due to invalid argument: {}", e.getMessage());
            return Result.fail(e.getMessage());
        } catch (Exception e) {
//            log.error("管理员批量确认送达失败 for site {}", siteId, e);
            return Result.fail("批量确认送达失败: " + e.getMessage());
        }
    }
}