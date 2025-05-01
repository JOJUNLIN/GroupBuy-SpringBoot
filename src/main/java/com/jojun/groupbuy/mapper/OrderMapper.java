package com.jojun.groupbuy.mapper;

import com.jojun.groupbuy.pojo.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

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

    /**
     * 根据用户ID和订单状态查询订单列表
     * @param userId 用户ID
     * @param orderState 订单状态，0表示查询全部
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 订单列表
     */
    @Select("<script>" +
            "SELECT * FROM `order` WHERE user_id = #{userId} " +
            "<if test='orderState != null and orderState > 0'>" +
            "AND order_state = #{orderState} " +
            "</if>" +
            "ORDER BY create_time DESC " +
            "LIMIT #{offset}, #{limit}" +
            "</script>")
    List<Order> findByUserIdAndOrderState(@Param("userId") String userId,
                                          @Param("orderState") Integer orderState,
                                          @Param("offset") Integer offset,
                                          @Param("limit") Integer limit);

    /**
     * 根据用户ID和订单状态统计订单数量
     * @param userId 用户ID
     * @param orderState 订单状态，0表示查询全部
     * @return 订单数量
     */
    @Select("<script>" +
            "SELECT COUNT(*) FROM `order` WHERE user_id = #{userId} " +
            "<if test='orderState != null and orderState > 0'>" +
            "AND order_state = #{orderState} " +
            "</if>" +
            "</script>")
    Long countByUserIdAndOrderState(@Param("userId") String userId, @Param("orderState") Integer orderState);
}
