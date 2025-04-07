package com.jojun.groupbuy.service;

import com.jojun.groupbuy.dto.CartAddRequest;
import com.jojun.groupbuy.pojo.Cart;

import java.util.List;

/**
 * @interfaceName: CartService
 * @author: JOJUN-CJL
 * @date: 2025/4/7
 * @Version: 1.0
 * @description: 购物车服务接口
 */

public interface CartService {

    /**
     * 添加商品到购物车
     * @param cartAddRequest 购物车添加请求
     * @return 是否添加成功
     */
    boolean addToCart(CartAddRequest cartAddRequest);

    /**
     * 获取用户购物车列表
     * @param userId 用户ID
     * @return 购物车列表
     */
    List<Cart> getUserCart(String userId);

    /**
     * 更新购物车商品数量
     * @param id 购物车ID
     * @param count 数量
     * @return 是否更新成功
     */
    boolean updateCartItemCount(Long id, Integer count);

    /**
     * 删除购物车商品
     * @param id 购物车ID
     * @return 是否删除成功
     */
    boolean deleteCartItem(Long id);

    /**
     * 清空用户购物车
     * @param userId 用户ID
     * @return 是否清空成功
     */
    boolean clearUserCart(String userId);
}
