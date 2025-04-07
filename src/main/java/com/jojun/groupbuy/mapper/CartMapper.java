package com.jojun.groupbuy.mapper;

import com.jojun.groupbuy.pojo.Cart;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @interfaceName: CartMapper
 * @author: JOJUN-CJL
 * @date: 2025/4/7
 * @Version: 1.0
 * @description: 购物车Mapper接口
 */

@Mapper
public interface CartMapper {

    /**
     * 添加商品到购物车
     * @param cart 购物车数据
     * @return 影响行数
     */
    @Insert("INSERT INTO user_cart (user_id, sku_id, goods_id, goods_name, image, price, count, sku_name, stock) " +
            "VALUES (#{userId}, #{skuId}, #{goodsId}, #{goodsName}, #{image}, #{price}, #{count}, #{skuNameJson}, #{stock})")
    int add(Cart cart);

    /**
     * 查询用户购物车中是否已存在该商品
     * @param userId 用户ID
     * @param skuId SKU ID
     * @return 购物车项
     */
    @Select("SELECT * FROM user_cart WHERE user_id = #{userId} AND sku_id = #{skuId}")
    @Results({
            @Result(property = "skuNameJson", column = "sku_name")
    })
    Cart findByUserIdAndSkuId(@Param("userId") String userId, @Param("skuId") String skuId);

    /**
     * 更新购物车商品数量
     * @param id 购物车ID
     * @param count 数量
     * @return 影响行数
     */
    @Update("UPDATE user_cart SET count = #{count} WHERE id = #{id}")
    int updateCount(@Param("id") Long id, @Param("count") Integer count);

    /**
     * 获取用户购物车列表
     * @param userId 用户ID
     * @return 购物车列表
     */
    @Select("SELECT * FROM user_cart WHERE user_id = #{userId}")
    @Results({
            @Result(property = "skuNameJson", column = "sku_name")
    })
    List<Cart> findByUserId(String userId);

    /**
     * 删除购物车商品
     * @param id 购物车ID
     * @return 影响行数
     */
    @Delete("DELETE FROM user_cart WHERE id = #{id}")
    int deleteById(Long id);

    /**
     * 清空用户购物车
     * @param userId 用户ID
     * @return 影响行数
     */
    @Delete("DELETE FROM user_cart WHERE user_id = #{userId}")
    int clearCart(String userId);
}
