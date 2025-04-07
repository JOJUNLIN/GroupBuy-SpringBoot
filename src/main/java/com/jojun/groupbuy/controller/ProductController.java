package com.jojun.groupbuy.controller;

import com.jojun.groupbuy.pojo.PageResult;
import com.jojun.groupbuy.pojo.Product;
import com.jojun.groupbuy.pojo.ProductQuery;
import com.jojun.groupbuy.pojo.Result;
import com.jojun.groupbuy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @className: ProductController
 * @author: JOJUN-CJL
 * @date: 2025/4/1
 * @Version: 1.0
 * @description: 商品控制器
 */
@RestController
@RequestMapping("/user/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 分页查询商品列表
     * @param query 查询参数
     * @return 分页结果
     */
    @GetMapping("/page")
    public Result<PageResult<Product>> getProductPage(ProductQuery query) {
        PageResult<Product> pageResult = productService.findByPage(query);
        return Result.success(pageResult);
    }

    /**
     * 根据ID查询商品
     * @param id 商品ID
     * @return 商品信息
     */
    @GetMapping("/{id}")
    public Result<Product> getProductById(@PathVariable String id) {
        Product product = productService.findById(id);
        return Result.success(product);
    }
}
