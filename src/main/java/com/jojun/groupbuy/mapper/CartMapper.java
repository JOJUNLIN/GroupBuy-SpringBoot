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
    @Insert("INSERT INTO user_cart (user_id, sku_id, goods_id, goods_name, image, price, count, sku_name, stock, selected) " +
            "VALUES (#{userId}, #{skuId}, #{goodsId}, #{goodsName}, #{image}, #{price}, #{count}, #{skuNameJson}, #{stock}, #{selected} )")
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
     * @param userId 用户ID
     * @param skuId 单品Id
     * @param count 数量
     * @return 影响行数
     */
    @Update("UPDATE user_cart SET count = #{count} WHERE user_Id = #{userId} AND sku_Id = #{skuId} ")
    int updateCount(@Param("userId") String userId, @Param("skuId") String skuId, @Param("count") Integer count);

    /**
     * 更新购物车商品选中状态
     * @param userId 用户ID
     * @param skuId 单品Id
     * @param selected 是否选中
     * @return 影响行数
     */
    @Update("UPDATE user_cart SET selected = #{selected} WHERE user_id = #{userId} AND sku_id = #{skuId}")
    int updateSelected(@Param("userId") String userId, @Param("skuId") String skuId, @Param("selected") Boolean selected);

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
     * @param userId 用户ID
     * @param skuId 单品Id
     * @return 影响行数
     */
    @Delete("DELETE FROM user_cart WHERE user_Id = #{userId} AND sku_Id = #{skuId} ")
    int deleteById(@Param("userId") String userId, @Param("skuId") String skuId);

    /**
     * 更新用户所有购物车商品选中状态
     * @param userId 用户ID
     * @param selected 是否选中
     * @return 影响行数
     */
    @Update("UPDATE user_cart SET selected = #{selected} WHERE user_id = #{userId}")
    int updateAllSelected(@Param("userId") String userId, @Param("selected") Boolean selected);

    /**
     * 清空用户购物车
     * @param userId 用户ID
     * @return 影响行数
     */
    @Delete("DELETE FROM user_cart WHERE user_id = #{userId}")
    int clearCart(String userId);
}
