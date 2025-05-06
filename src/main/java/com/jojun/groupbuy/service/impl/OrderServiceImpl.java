package com.jojun.groupbuy.service.impl;

import com.jojun.groupbuy.mapper.OrderGoodsMapper;
import com.jojun.groupbuy.mapper.OrderMapper;
import com.jojun.groupbuy.pojo.AddressItem;
import com.jojun.groupbuy.dto.OrderPreGoods;
import com.jojun.groupbuy.dto.OrderPreResult;
import com.jojun.groupbuy.dto.OrderSummary;
import com.jojun.groupbuy.mapper.AddressMapper;
import com.jojun.groupbuy.mapper.CartMapper;
import com.jojun.groupbuy.pojo.Cart;
import com.jojun.groupbuy.service.OrderService;
import com.jojun.groupbuy.dto.*;
import com.jojun.groupbuy.pojo.Order;
import com.jojun.groupbuy.pojo.OrderGoods;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @className: OrderServiceImpl
 * @author: JOJUN-CJL
 * @date: 2025/4/24
 * @Version: 1.0
 * @description: 订单服务实现类
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderGoodsMapper orderGoodsMapper;

    /**
     * 获取预付订单信息
     * @param userId 用户ID
     * @return 预付订单信息
     */
    @Override
    public OrderPreResult getOrderPre(String userId) {
        // 获取用户购物车中已选中的商品
        List<Cart> selectedCarts = cartMapper.findByUserId(userId).stream()
                .filter(Cart::getSelected)
                .collect(Collectors.toList());

        // 转换为订单商品列表
        List<OrderPreGoods> orderGoods = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (Cart cart : selectedCarts) {
            OrderPreGoods goods = new OrderPreGoods();

            // 拼接sku属性名称，例如"颜色:瓷白色 尺寸：8寸"
            String skuNameStr = String.join(" ", cart.getSkuName());
            goods.setSkuName(skuNameStr);

            goods.setCount(cart.getCount());
            goods.setGoodsId(cart.getGoodsId());
            goods.setGoodsName(cart.getGoodsName());
            goods.setImage(cart.getImage());
            goods.setPrice(cart.getPrice().toString());
            goods.setSkuId(cart.getSkuId());

            // 计算小计金额
            BigDecimal itemTotal = cart.getPrice().multiply(new BigDecimal(cart.getCount()));
            goods.setTotalPrice(itemTotal.toString());
            goods.setTotalPayPrice(itemTotal.toString()); // 实付价格，可能会考虑优惠

            // 累加总价
            totalPrice = totalPrice.add(itemTotal);

            orderGoods.add(goods);
        }

        // 创建结算信息
//        BigDecimal postFee = BigDecimal.ZERO; // 邮费，可以根据业务规则设置
        BigDecimal postFee = new BigDecimal("500");
        BigDecimal totalPayPrice = totalPrice.add(postFee);
        // 将三个参数都除以100后再传给前端
        BigDecimal divisor = new BigDecimal("100");
        postFee = postFee.divide(divisor, 2, BigDecimal.ROUND_HALF_UP); // 保留两位小数，四舍五入
        totalPrice = totalPrice.divide(divisor, 2, BigDecimal.ROUND_HALF_UP);
        totalPayPrice = totalPayPrice.divide(divisor, 2, BigDecimal.ROUND_HALF_UP);
        OrderSummary summary = new OrderSummary(totalPrice, postFee, totalPayPrice);
        // 获取用户地址列表
        List<AddressItem> userAddresses = addressMapper.findAddress();

        // 构建并返回结果
        return new OrderPreResult(orderGoods, summary, userAddresses);
    }

    /**
     * 创建订单
     * @param userId 用户ID
     * @param params 创建订单参数
     * @return 创建订单结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderCreateResult createOrder(String userId, OrderCreateParams params) {
        // 生成订单ID
        String orderId = generateOrderId();

        // 创建订单实体
        Order order = new Order();
        order.setId(orderId);
        order.setUserId(userId);
        order.setAddressId(params.getAddressId());
        order.setOrderState(1); // 设置初始状态为待付款
        order.setTotalMoney(BigDecimal.valueOf(params.getTotalPrice()));
        order.setPostFee(BigDecimal.valueOf(params.getPostFee()));
        order.setPayMoney(BigDecimal.valueOf(params.getTotalPayPrice()));
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());

        // 保存订单
        orderMapper.createOrder(order);

        // 创建订单商品列表
        List<OrderGoods> orderGoodsList = new ArrayList<>();
        for (OrderPreGoods item : params.getGoods()) {
            OrderGoods orderGoods = new OrderGoods();
            orderGoods.setOrderId(orderId);
            orderGoods.setGoodsId(item.getGoodsId());
            orderGoods.setGoodsName(item.getGoodsName());
            orderGoods.setSkuId(item.getSkuId());
            orderGoods.setSkuName(item.getSkuName());
            orderGoods.setImage(item.getImage());
            orderGoods.setPrice(new BigDecimal(item.getPrice()));
            orderGoods.setCount(item.getCount());
            orderGoods.setTotalPrice(new BigDecimal(item.getTotalPrice()));
            orderGoods.setTotalPayPrice(new BigDecimal(item.getTotalPayPrice()));

            orderGoodsList.add(orderGoods);
        }

        // 批量保存订单商品
        if (!orderGoodsList.isEmpty()) {
            orderGoodsMapper.batchInsert(orderGoodsList);
        }

        // 清空购物车中已选中的商品
        // 注意：这里假设购物车中已选中的商品都是在当前订单中的商品
        cartMapper.deleteSelectedByUserId(userId);

        // 返回结果
        return new OrderCreateResult(orderId);
    }

    /**
     * 获取订单详情
     * @param userId 用户ID
     * @param id 订单ID
     * @return 订单详情
     */
    @Override
    public OrderResult getOrderById(String userId, String id) {
        // 查询订单信息，确保是该用户的订单
        Order order = orderMapper.findByUserIdAndId(userId, id);
        if (order == null) {
            return null;
        }

        // 查询订单商品信息
        List<OrderGoods> orderGoodsList = orderGoodsMapper.findByOrderId(id);

        // 根据地址id查询地址

        // 构建返回结果
        OrderResult result = new OrderResult();
        result.setId(order.getId());
        result.setOrderState(order.getOrderState());
        result.setAddress(addressMapper.findById(order.getAddressId()));
        result.setCreateTime(formatTime(order.getCreateTime()));
        result.setTotalMoney(order.getTotalMoney());
        result.setPostFee(order.getPostFee());
        result.setPayMoney(order.getPayMoney());

        // 转换商品列表
        List<OrderSkuItem> skus = new ArrayList<>();
        for (OrderGoods goods : orderGoodsList) {
            OrderSkuItem sku = new OrderSkuItem();
            sku.setSkuId(goods.getSkuId());
            sku.setGoodsId(goods.getGoodsId());
            sku.setGoodsName(goods.getGoodsName());
            sku.setSkuName(goods.getSkuName());
            sku.setCount(goods.getCount());
            sku.setPrice(goods.getPrice());
            sku.setImage(goods.getImage());

            skus.add(sku);
        }
        result.setSkus(skus);

        return result;
    }

    /**
     * 更新订单状态
     * @param orderId 订单ID
     * @param state 订单状态
     * @return 是否更新成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateOrderState(String orderId, Integer state) {
        // 查询订单是否存在
        Order order = orderMapper.findById(orderId);
        if (order == null) {
            return false;
        }

        // 更新订单状态
        int result = orderMapper.updateOrderState(orderId, state);
        return result > 0;
    }

    /**
     * 获取订单列表
     * @param userId 用户ID
     * @param page 页码
     * @param pageSize 每页条数
     * @param orderState 订单状态，0表示查询全部
     * @return 订单列表结果
     */
    @Override
    public OrderListResult getOrderList(String userId, Integer page, Integer pageSize, Integer orderState) {
        // 设置默认值
        if (page == null || page < 1) {
            page = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }

        // 计算偏移量
        int offset = (page - 1) * pageSize;

        // 查询订单数据
        List<Order> orders = orderMapper.findByUserIdAndOrderState(userId, orderState, offset, pageSize);

        // 查询总记录数
        Long totalCount = orderMapper.countByUserIdAndOrderState(userId, orderState);

        // 计算总页数
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        // 转换为前端需要的数据格式
        List<OrderItem> items = new ArrayList<>();
        for (Order order : orders) {
            // 查询订单商品信息
            List<OrderGoods> orderGoodsList = orderGoodsMapper.findByOrderId(order.getId());

            // 转换商品列表
            List<OrderSkuItem> skus = new ArrayList<>();
            int totalNum = 0; // 计算总件数

            for (OrderGoods goods : orderGoodsList) {
                OrderSkuItem sku = new OrderSkuItem();
                sku.setSkuId(goods.getSkuId());
                sku.setGoodsId(goods.getGoodsId());
                sku.setGoodsName(goods.getGoodsName());
                sku.setSkuName(goods.getSkuName());
                sku.setCount(goods.getCount());
                sku.setPrice(goods.getPrice());
                sku.setImage(goods.getImage());

                skus.add(sku);
                totalNum += goods.getCount(); // 累加商品数量
            }

            // 创建OrderItem对象
            OrderItem item = new OrderItem();
            item.setId(order.getId());
            item.setOrderState(order.getOrderState());
            item.setSkus(skus);
            item.setAddress(addressMapper.findById(order.getAddressId()));
            item.setCreateTime(formatTime(order.getCreateTime()));
            item.setTotalMoney(order.getTotalMoney());
            item.setPostFee(order.getPostFee());
            item.setPayMoney(order.getPayMoney());
            item.setTotalNum(totalNum);

            items.add(item);
        }

        // 创建并返回结果
        return new OrderListResult(totalCount, items, page, totalPages, pageSize);
    }

    /**
     * 生成订单ID
     * @return 订单ID
     */
    private String generateOrderId() {
        // 使用时间戳前缀 + UUID后8位作为订单ID
        String timestamp = String.valueOf(System.currentTimeMillis());
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        return timestamp + uuid;
    }

    /**
     * 格式化时间
     * @param time 时间
     * @return 格式化后的时间字符串
     */
    private String formatTime(LocalDateTime time) {
        if (time == null) {
            return "";
        }
        return time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * (管理员) 获取所有订单列表
     * @param page 页码
     * @param pageSize 每页条数
     * @param orderState 订单状态，null或0表示查询全部
     * @return 管理员订单列表结果
     */
    @Override
    public AdminOrderListResultDto getAllOrdersForAdmin(Integer page, Integer pageSize, Integer orderState) {
        page = (page == null || page < 1) ? 1 : page;
        pageSize = (pageSize == null || pageSize < 1) ? 10 : pageSize;
        int offset = (page - 1) * pageSize;

        List<Order> orders = orderMapper.findAllOrdersForAdmin(orderState, offset, pageSize);
        Long totalCount = orderMapper.countAllOrdersForAdmin(orderState);
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        List<AdminOrderItemDto> adminOrderItems = orders.stream()
                .map(this::convertToAdminOrderItemDto)
                .collect(Collectors.toList());

        return new AdminOrderListResultDto(totalCount, adminOrderItems, page, totalPages, pageSize);
    }

    /**
     * (管理员) 根据站点ID获取订单列表
     * @param addressId 站点ID
     * @param page 页码
     * @param pageSize 每页条数
     * @param orderState 订单状态，null或0表示查询全部
     * @return 管理员订单列表结果
     */
    @Override
    public AdminOrderListResultDto getOrdersBySiteForAdmin(Integer addressId, Integer page, Integer pageSize, Integer orderState) {
        if (addressId == null) {
            return new AdminOrderListResultDto(0L, new ArrayList<>(), 1, 0, pageSize);
        }
        page = (page == null || page < 1) ? 1 : page;
        pageSize = (pageSize == null || pageSize < 1) ? 10 : pageSize;
        int offset = (page - 1) * pageSize;

        List<Order> orders = orderMapper.findOrdersByAddressIdForAdmin(addressId, orderState, offset, pageSize);
        Long totalCount = orderMapper.countOrdersByAddressIdForAdmin(addressId, orderState);
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        List<AdminOrderItemDto> adminOrderItems = orders.stream()
                .map(this::convertToAdminOrderItemDto)
                .collect(Collectors.toList());

        return new AdminOrderListResultDto(totalCount, adminOrderItems, page, totalPages, pageSize);
    }

    /**
     * 辅助方法：将 Order 实体转换为 AdminOrderItemDto
     */
    private AdminOrderItemDto convertToAdminOrderItemDto(Order order) {
        if (order == null) {
            return null;
        }
        AdminOrderItemDto dto = new AdminOrderItemDto();
        dto.setId(order.getId());
        dto.setUserId(order.getUserId()); // 直接使用 Order 中的 userId
        dto.setOrderState(order.getOrderState());
        dto.setCreateTime(formatTime(order.getCreateTime()));

        // 获取站点地址
        if (order.getAddressId() != null) {
            String siteAddress = addressMapper.findById(order.getAddressId());
            dto.setSiteAddress(siteAddress != null ? siteAddress : "未知站点");
        } else {
            dto.setSiteAddress("未指定站点");
        }

        // 如果需要用户名，可以在这里根据 order.getUserId() 查询并设置
        // dto.setUserName(userService.getUserNameById(order.getUserId()));

        return dto;
    }
}