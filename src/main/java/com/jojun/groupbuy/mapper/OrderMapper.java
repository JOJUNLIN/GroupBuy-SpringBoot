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

    /**
     * (管理员) 查询所有订单 (可按状态过滤，可分页)
     * @param orderState 订单状态 (null 或 0 表示查询全部)
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 订单列表
     */
    @Select("<script>" +
            "SELECT * FROM `order` " +
            "<where>" +
            "  <if test='orderState != null and orderState > 0'>" +
            "    AND order_state = #{orderState} " +
            "  </if>" +
            "</where>" +
            "ORDER BY create_time DESC " +
            "LIMIT #{offset}, #{limit}" +
            "</script>")
    List<Order> findAllOrdersForAdmin(@Param("orderState") Integer orderState,
                                      @Param("offset") Integer offset,
                                      @Param("limit") Integer limit);

    /**
     * (管理员) 统计所有订单数量 (可按状态过滤)
     * @param orderState 订单状态 (null 或 0 表示查询全部)
     * @return 订单数量
     */
    @Select("<script>" +
            "SELECT COUNT(*) FROM `order` " +
            "<where>" +
            "  <if test='orderState != null and orderState > 0'>" +
            "    AND order_state = #{orderState} " +
            "  </if>" +
            "</where>" +
            "</script>")
    Long countAllOrdersForAdmin(@Param("orderState") Integer orderState);

    /**
     * (管理员) 根据站点ID查询订单 (可按状态过滤，可分页)
     * @param addressId 站点ID (收货地址ID)
     * @param orderState 订单状态 (null 或 0 表示查询全部)
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 订单列表
     */
    @Select("<script>" +
            "SELECT * FROM `order` " +
            "<where>" +
            "  address_id = #{addressId} " +
            "  <if test='orderState != null and orderState > 0'>" +
            "    AND order_state = #{orderState} " +
            "  </if>" +
            "</where>" +
            "ORDER BY create_time DESC " +
            "LIMIT #{offset}, #{limit}" +
            "</script>")
    List<Order> findOrdersByAddressIdForAdmin(@Param("addressId") Integer addressId,
                                              @Param("orderState") Integer orderState,
                                              @Param("offset") Integer offset,
                                              @Param("limit") Integer limit);

    /**
     * (管理员) 根据站点ID统计订单数量 (可按状态过滤)
     * @param addressId 站点ID (收货地址ID)
     * @param orderState 订单状态 (null 或 0 表示查询全部)
     * @return 订单数量
     */
    @Select("<script>" +
            "SELECT COUNT(*) FROM `order` " +
            "<where>" +
            "  address_id = #{addressId} " +
            "  <if test='orderState != null and orderState > 0'>" +
            "    AND order_state = #{orderState} " +
            "  </if>" +
            "</where>" +
            "</script>")
    Long countOrdersByAddressIdForAdmin(@Param("addressId") Integer addressId,
                                        @Param("orderState") Integer orderState);

    /**
     * 根据站点ID和订单状态，计算这些订单中所有商品的总数量。
     * 这需要连接 order 表和 order_goods 表。
     * @param addressId 站点ID (对应 order.address_id)
     * @param orderState 订单状态
     * @return 商品总数，如果没有任何匹配的订单或商品，则返回0
     */
    @Select("SELECT IFNULL(SUM(og.count), 0) " +
            "FROM `order` o " +
            "JOIN order_goods og ON o.id = og.order_id " +
            "WHERE o.address_id = #{addressId} AND o.order_state = #{orderState}")
    Integer sumProductCountByAddressIdAndOrderState(@Param("addressId") Integer addressId,
                                                    @Param("orderState") Integer orderState);

    /**
     * 根据站点ID、订单状态列表和当前状态，批量更新订单状态
     * 用于管理员批量发货或确认送达
     * @param orderIds 要更新的订单ID列表
     * @param targetState 目标订单状态
     * @param addressId 站点ID
     * @param currentState 订单当前应处于的状态，更新时进行校验
     * @return 实际更新成功的订单数量
     */
    @Update("<script>" +
            "UPDATE `order` SET order_state = #{targetState}, update_time = NOW() " +
            "WHERE id IN " +
            "<foreach collection='orderIds' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "AND address_id = #{addressId} " + // 确保是该站点的订单
            "AND order_state = #{currentState}" + // 确保订单当前状态正确
            "</script>")
    int batchUpdateOrderStateByAddressIdAndCurrentState(
            @Param("orderIds") List<String> orderIds,
            @Param("targetState") Integer targetState,
            @Param("addressId") Integer addressId,
            @Param("currentState") Integer currentState);
}
