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

    @Override
    @Transactional
    public boolean addToCart(CartAddRequest cartAddRequest) {
        // 查询购物车中是否已存在该商品
        Cart existingCart = cartMapper.findByUserIdAndSkuId(cartAddRequest.getUserId(), cartAddRequest.getSkuId());

        if (existingCart != null) {
            // 已存在，更新数量
            int newCount = existingCart.getCount() + cartAddRequest.getCount();
            return cartMapper.updateCount(existingCart.getId(), newCount) > 0;
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

            return cartMapper.add(cart) > 0;
        }
    }

    @Override
    public List<Cart> getUserCart(String userId) {
        return cartMapper.findByUserId(userId);
    }

    @Override
    @Transactional
    public boolean updateCartItemCount(Long id, Integer count) {
        if (count <= 0) {
            // 如果数量小于等于0，删除该购物车项
            return cartMapper.deleteById(id) > 0;
        }
        return cartMapper.updateCount(id, count) > 0;
    }

    @Override
    @Transactional
    public boolean deleteCartItem(Long id) {
        return cartMapper.deleteById(id) > 0;
    }

    @Override
    @Transactional
    public boolean clearUserCart(String userId) {
        return cartMapper.clearCart(userId) >= 0;
    }
}
