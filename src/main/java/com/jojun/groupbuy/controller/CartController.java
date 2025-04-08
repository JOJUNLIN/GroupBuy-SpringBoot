package com.jojun.groupbuy.controller;

import com.jojun.groupbuy.dto.CartAddRequest;
import com.jojun.groupbuy.pojo.Cart;
import com.jojun.groupbuy.pojo.Result;
import com.jojun.groupbuy.service.CartService;
import com.jojun.groupbuy.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
     * @param request HTTP请求对象，用于获取请求头中的 token
     * @return 购物车列表
     */
    @GetMapping("/list")
    public Result<List<Cart>> getUserCart(HttpServletRequest request) {
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

            // 将 Integer 转换为 String 以匹配 cartService 的参数类型
            List<Cart> cartList = cartService.getUserCart(userId.toString());
            return Result.success(cartList);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("获取购物车失败: " + e.getMessage());
        }
    }

    /**
     * 更新购物车商品
     * @param request HTTP请求对象，用于获取请求头中的 token
     * @param skuId 单品Id
     * @param updateData 包含 selected 和 count 的请求体
     * @return 结果
     */
    @PutMapping("/count/{skuId}")
    public Result<Void> updateCartItem(HttpServletRequest request,
                                       @PathVariable String skuId,
                                       @RequestBody Map<String, Object> updateData) {

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

        if (skuId == null) {
            return Result.fail("单品Id不能为空");
        }

        // 获取更新数据
        Boolean selected = updateData.get("selected") != null ? (Boolean) updateData.get("selected") : null;
        Integer count = updateData.get("count") != null ? ((Number) updateData.get("count")).intValue() : null;

        boolean success = true;

        // 如果提供了商品数量，则更新数量
        if (count != null) {
            success = cartService.updateCartItemCount(userId.toString(), skuId, count);
        }

        if (selected != null) {
            success = success && cartService.updateCartItemSelected(userId.toString(), skuId, selected);
        }

        return success ? Result.success() : Result.fail("更新购物车失败");
    }

    /**
     * 删除购物车商品
     * @param request HTTP请求对象，用于获取请求头中的 token
     * @param deleteRequest 包含要删除的商品ID数组的请求体
     * @return 结果
     */
    @DeleteMapping("/delete")
    public Result<Void> deleteCartItems(HttpServletRequest request, @RequestBody Map<String, String[]> deleteRequest) {
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

            // 获取要删除的商品ID数组
            String[] skuIds = deleteRequest.get("ids");
            if (skuIds == null || skuIds.length == 0) {
                return Result.fail("未指定要删除的商品");
            }

            // 删除购物车中的商品
            boolean success = true;
            for (String skuId : skuIds) {
                // 假设 cartService.deleteCartItem 方法需要同时接收用户ID和商品ID
                // 如果您的方法只接收skuId，您需要修改服务层代码以确保只删除当前用户的购物车项
                boolean itemSuccess = cartService.deleteCartItem(userId.toString(), skuId);
                if (!itemSuccess) {
                    success = false;
                }
            }

            return success ? Result.success() : Result.fail("部分或全部商品删除失败");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("删除购物车失败: " + e.getMessage());
        }
    }

    /**
     * 购物车全选/取消全选
     * @param request HTTP请求对象，用于获取请求头中的 token
     * @param updateData 包含 selected 的请求体
     * @return 结果
     */
    @PutMapping("/allselected")
    public Result<Void> updateAllCartItemsSelected(HttpServletRequest request,
                                                   @RequestBody Map<String, Object> updateData) {

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

        // 获取选中状态
        Boolean selected = updateData.get("selected") != null ? (Boolean) updateData.get("selected") : null;
        if (selected == null) {
            return Result.fail("选中状态不能为空");
        }

        boolean success = cartService.updateAllCartItemsSelected(userId.toString(), selected);
        return success ? Result.success() : Result.fail("更新购物车选中状态失败");
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
