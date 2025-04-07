package com.jojun.groupbuy.service;

import com.jojun.groupbuy.pojo.Category;

import java.util.List;

/**
 * @interfaceName: CategoryService
 * @author: JOJUN-CJL
 * @date: 2025/4/1
 * @Version: 1.0
 * @description: 分类服务接口
 */

public interface CategoryService {
    // 查询所有分类
    List<Category> findAll();

    // 根据ID查询分类
    Category findById(String id);
}
