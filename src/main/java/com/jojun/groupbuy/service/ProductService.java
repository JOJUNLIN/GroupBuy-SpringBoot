package com.jojun.groupbuy.service;

import com.jojun.groupbuy.pojo.PageResult;
import com.jojun.groupbuy.pojo.Product;
import com.jojun.groupbuy.pojo.ProductQuery;

/**
 * @interfaceName: ProductService
 * @author: JOJUN-CJL
 * @date: 2025/4/1
 * @Version: 1.0
 * @description: 商品服务接口
 */
public interface ProductService {
    /**
     * 分页查询商品列表
     * @param query 查询参数
     * @return 分页结果
     */
    PageResult<Product> findByPage(ProductQuery query);

    /**
     * 根据ID查询商品
     * @param id 商品ID
     * @return 商品信息
     */
    Product findById(String id);
}
