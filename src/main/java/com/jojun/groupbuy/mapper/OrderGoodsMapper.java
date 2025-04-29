package com.jojun.groupbuy.mapper;

import com.jojun.groupbuy.pojo.OrderGoods;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @interfaceName: OrderGoodsMapper
 * @author: JOJUN-CJL
 * @date: 2025/4/29
 * @Version: 1.0
 * @description: 订单商品Mapper接口
 */
@Mapper
public interface OrderGoodsMapper {

    /**
     * 批量插入订单商品
     * @param orderGoodsList 订单商品列表
     * @return 影响的行数
     */
    @Insert("<script>" +
            "INSERT INTO order_goods (order_id, goods_id, goods_name, sku_id, sku_name, image, price, count, total_price, total_pay_price) VALUES " +
            "<foreach collection='list' item='item' separator=','>" +
            "(#{item.orderId}, #{item.goodsId}, #{item.goodsName}, #{item.skuId}, #{item.skuName}, " +
            "#{item.image}, #{item.price}, #{item.count}, #{item.totalPrice}, #{item.totalPayPrice})" +
            "</foreach>" +
            "</script>")
    int batchInsert(List<OrderGoods> orderGoodsList);

    /**
     * 根据订单ID查询订单商品
     * @param orderId 订单ID
     * @return 订单商品列表
     */
    @Select("SELECT * FROM order_goods WHERE order_id = #{orderId}")
    List<OrderGoods> findByOrderId(String orderId);
}
