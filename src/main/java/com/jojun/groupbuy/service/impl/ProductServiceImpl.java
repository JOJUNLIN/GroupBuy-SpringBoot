package com.jojun.groupbuy.service.impl;

import com.jojun.groupbuy.mapper.ProductMapper;
import com.jojun.groupbuy.pojo.PageResult;
import com.jojun.groupbuy.pojo.Product;
import com.jojun.groupbuy.pojo.ProductQuery;
import com.jojun.groupbuy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @className: ProductServiceImpl
 * @author: JOJUN-CJL
 * @date: 2025/4/1
 * @Version: 1.0
 * @description: 商品服务实现类
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public PageResult<Product> findByPage(ProductQuery query) {
        // 查询总记录数
        Long total = productMapper.countProducts(query.getKeyword());

        // 计算总页数
        Integer pages = (int) Math.ceil(total * 1.0 / query.getPageSize());

        // 计算偏移量
        Integer offset = (query.getPage() - 1) * query.getPageSize();

        // 查询当前页数据
        List<Product> items = productMapper.findByPage(
                query.getKeyword(),
                offset,
                query.getPageSize(),
                query.getSortField(),
                query.getSortOrder()
        );

        // 封装分页结果
        return new PageResult<>(total, query.getPageSize(), pages, query.getPage(), items);
    }

    @Override
    public Product findById(String id) {
        return productMapper.findById(id);
    }
}
