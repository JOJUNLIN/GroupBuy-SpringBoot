package com.jojun.groupbuy.service.impl;

import com.jojun.groupbuy.dto.CartAddRequest;
import com.jojun.groupbuy.mapper.CartMapper;
import com.jojun.groupbuy.pojo.Cart;
import com.jojun.groupbuy.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @className: CartServiceImpl
 * @author: JOJUN-CJL
 * @date: 2025/4/7
 * @Version: 1.0
 * @description: 购物车服务实现类
 */

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    /**
     * 添加商品到购物车
     * @param cartAddRequest 购物车添加请求
     * @return 是否添加成功
     */
    @Override
    @Transactional
    public boolean addToCart(CartAddRequest cartAddRequest) {
        // 查询购物车中是否已存在该商品
        Cart existingCart = cartMapper.findByUserIdAndSkuId(cartAddRequest.getUserId(), cartAddRequest.getSkuId());

        if (existingCart != null) {
            // 已存在，更新数量
            int newCount = existingCart.getCount() + cartAddRequest.getCount();
            return cartMapper.updateCount(existingCart.getUserId(), existingCart.getSkuId(), newCount) > 0;
        } else {
            // 不存在，新增购物车项
            Cart cart = new Cart();
            cart.setUserId(cartAddRequest.getUserId());
            cart.setSkuId(cartAddRequest.getSkuId());
            cart.setGoodsId(cartAddRequest.getGoodsId());
            cart.setGoodsName(cartAddRequest.getGoodsName());
            cart.setImage(cartAddRequest.getImage());
            cart.setPrice(cartAddRequest.getPrice());
            cart.setCount(cartAddRequest.getCount());
            cart.setSkuName(cartAddRequest.getSkuName());  // 不需要手动设置 skuNameJson，因为会在 getSkuNameJson 方法中自动转换
            cart.setStock(cartAddRequest.getStock());
            cart.setSelected(false);  // 明确设置默认为未选中状态

            return cartMapper.add(cart) > 0;
        }
    }

    /**
     * 获取用户购物车列表
     * @param userId 用户ID
     * @return 购物车列表
     */
    @Override
    public List<Cart> getUserCart(String userId) {
        return cartMapper.findByUserId(userId);
    }

    /**
     * 更新购物车商品数量
     * @param skuId 单品Id
     * @param count 数量
     * @return 是否更新成功
     */
    @Override
    @Transactional
    public boolean updateCartItemCount(String userId, String skuId, Integer count) {
        if (count <= 0) {
            // 如果数量小于等于0，删除该购物车项
            return cartMapper.deleteById(userId, skuId) > 0;
        }
        return cartMapper.updateCount(userId, skuId, count) > 0;
    }

    /**
     * 更新购物车商品选中状态
     * @param userId 用户ID
     * @param skuId 单品Id
     * @param selected 是否选中
     * @return 是否更新成功
     */
    @Override
    @Transactional
    public boolean updateCartItemSelected(String userId, String skuId, Boolean selected) {
        return cartMapper.updateSelected(userId, skuId, selected) > 0;
    }

    /**
     * 删除购物车商品
     * @param userId 用户ID
     * @param skuId 单品Id
     * @return 是否删除成功
     */
    @Override
    @Transactional
    public boolean deleteCartItem(String userId, String skuId) {
        return cartMapper.deleteById(userId, skuId) > 0;
    }

    /**
     * 更新用户所有购物车商品选中状态
     * @param userId 用户ID
     * @param selected 是否选中
     * @return 是否更新成功
     */
    @Override
    @Transactional
    public boolean updateAllCartItemsSelected(String userId, Boolean selected) {
        return cartMapper.updateAllSelected(userId, selected) >= 0;
    }

    /**
     * 清空用户购物车
     * @param userId 用户ID
     * @return 是否清空成功
     */
    @Override
    @Transactional
    public boolean clearUserCart(String userId) {
        return cartMapper.clearCart(userId) >= 0;
    }
}
