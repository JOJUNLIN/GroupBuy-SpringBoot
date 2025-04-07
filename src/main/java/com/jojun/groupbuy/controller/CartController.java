package com.jojun.groupbuy.controller;

import com.jojun.groupbuy.dto.CartAddRequest;
import com.jojun.groupbuy.pojo.Cart;
import com.jojun.groupbuy.pojo.Result;
import com.jojun.groupbuy.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @className: CartController
 * @author: JOJUN-CJL
 * @date: 2025/4/7
 * @Version: 1.0
 * @description: 购物车控制器
 */

@RestController
@RequestMapping("/user/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 添加商品到购物车
     * @param cartAddRequest 购物车添加请求
     * @return 结果
     */
    @PostMapping
    public Result<Void> addToCart(@RequestBody CartAddRequest cartAddRequest) {
        if (cartAddRequest.getUserId() == null || cartAddRequest.getSkuId() == null) {
            return Result.fail("用户ID和商品SKU ID不能为空");
        }

        boolean success = cartService.addToCart(cartAddRequest);
        return success ? Result.success() : Result.fail("添加购物车失败");
    }

    /**
     * 获取用户购物车列表
     * @param userId 用户ID
     * @return 购物车列表
     */
    @GetMapping("/list")
    public Result<List<Cart>> getUserCart(@RequestParam String userId) {
        if (userId == null) {
            return Result.fail("用户ID不能为空");
        }

        List<Cart> cartList = cartService.getUserCart(userId);
        return Result.success(cartList);
    }

    /**
     * 更新购物车商品数量
     * @param id 购物车ID
     * @param count 数量
     * @return 结果
     */
    @PutMapping("/count")
    public Result<Void> updateCartItemCount(@RequestParam Long id, @RequestParam Integer count) {
        if (id == null || count == null) {
            return Result.fail("购物车ID和数量不能为空");
        }

        boolean success = cartService.updateCartItemCount(id, count);
        return success ? Result.success() : Result.fail("更新购物车失败");
    }

    /**
     * 删除购物车商品
     * @param id 购物车ID
     * @return 结果
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteCartItem(@PathVariable Long id) {
        boolean success = cartService.deleteCartItem(id);
        return success ? Result.success() : Result.fail("删除购物车失败");
    }

    /**
     * 清空用户购物车
     * @param userId 用户ID
     * @return 结果
     */
    @DeleteMapping("/clear")
    public Result<Void> clearUserCart(@RequestParam String userId) {
        if (userId == null) {
            return Result.fail("用户ID不能为空");
        }

        boolean success = cartService.clearUserCart(userId);
        return success ? Result.success() : Result.fail("清空购物车失败");
    }
}
