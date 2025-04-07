package com.jojun.groupbuy.mapper;

import com.jojun.groupbuy.pojo.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @interfaceName: ProductMapper
 * @author: JOJUN-CJL
 * @date: 2025/4/1
 * @Version: 1.0
 * @description: 商品数据访问接口
 */
@Mapper
public interface ProductMapper {
    /**
     * 查询商品总数
     * @param keyword 搜索关键字
     * @return 商品总数
     */
    @Select("<script>"
            + "SELECT COUNT(*) FROM product"
            + "<if test='keyword != null and keyword != \"\"'>"
            + " WHERE name LIKE CONCAT('%', #{keyword}, '%')"
            + " OR `desc` LIKE CONCAT('%', #{keyword}, '%')"  // 使用反引号包裹desc关键字
            + "</if>"
            + "</script>")
    Long countProducts(@Param("keyword") String keyword);

    /**
     * 分页查询商品列表
     * @param keyword 搜索关键字
     * @param offset 偏移量
     * @param pageSize 每页条数
     * @param sortField 排序字段
     * @param isAsc 是否升序
     * @return 商品列表
     */
    @Select("<script>"
            + "SELECT id, name, `desc`, price, picture, order_num as orderNum, discount FROM product"  // 显式映射order_num到orderNum
            + "<if test='keyword != null and keyword != \"\"'>"
            + " WHERE name LIKE CONCAT('%', #{keyword}, '%')"
            + " OR `desc` LIKE CONCAT('%', #{keyword}, '%')"  // 使用反引号包裹desc关键字
            + "</if>"
            + "<if test='sortField != null and sortField != \"\"'>"
            + " ORDER BY ${sortField} <if test='isAsc'>ASC</if><if test='!isAsc'>DESC</if>"
            + "</if>"
            + "<if test='sortField == null or sortField == \"\"'>"
            + " ORDER BY order_num DESC" // 使用数据库列名order_num而不是Java属性名
            + "</if>"
            + " LIMIT #{offset}, #{pageSize}"
            + "</script>")
    List<Product> findByPage(
            @Param("keyword") String keyword,
            @Param("offset") Integer offset,
            @Param("pageSize") Integer pageSize,
            @Param("sortField") String sortField,
            @Param("isAsc") Boolean isAsc
    );

    /**
     * 根据ID查询商品
     * @param id 商品ID
     * @return 商品信息
     */
    @Select("SELECT id, name, `desc`, price, picture, order_num as orderNum, discount FROM product WHERE id = #{id}")  // 显式映射
    Product findById(String id);
}
