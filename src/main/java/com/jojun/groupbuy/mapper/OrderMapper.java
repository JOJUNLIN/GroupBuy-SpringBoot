package com.jojun.groupbuy.mapper;

import com.jojun.groupbuy.pojo.Order;
import org.apache.ibatis.annotations.*;

/**
 * @interfaceName: OrderMapper
 * @author: JOJUN-CJL
 * @date: 2025/4/29
 * @Version: 1.0
 * @description: 订单Mapper接口
 */
@Mapper
public interface OrderMapper {

    /**
     * 创建订单
     * @param order 订单实体
     * @return 影响的行数
     */
    @Insert("INSERT INTO `order` (id, user_id, address_id, order_state, total_money, post_fee, pay_money, create_time, update_time) " +
            "VALUES (#{id}, #{userId}, #{addressId}, #{orderState}, #{totalMoney}, #{postFee}, #{payMoney}, #{createTime}, #{updateTime})")
    int createOrder(Order order);

    /**
     * 根据ID查询订单
     * @param id 订单ID
     * @return 订单实体
     */
    @Select("SELECT * FROM `order` WHERE id = #{id}")
    Order findById(String id);

    /**
     * 根据用户ID和订单ID查询订单
     * @param userId 用户ID
     * @param id 订单ID
     * @return 订单实体
     */
    @Select("SELECT * FROM `order` WHERE user_id = #{userId} AND id = #{id}")
    Order findByUserIdAndId(@Param("userId") String userId, @Param("id") String id);

    /**
     * 更新订单状态
     * @param id 订单ID
     * @param state 订单状态
     * @return 影响的行数
     */
    @Update("UPDATE `order` SET order_state = #{state}, update_time = NOW() WHERE id = #{id}")
    int updateOrderState(@Param("id") String id, @Param("state") Integer state);
}
