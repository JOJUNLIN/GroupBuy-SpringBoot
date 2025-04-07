package com.jojun.groupbuy.service.impl;

import com.jojun.groupbuy.mapper.CategoryMapper;
import com.jojun.groupbuy.pojo.Category;
import com.jojun.groupbuy.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @className: CategoryServiceImpl
 * @author: JOJUN-CJL
 * @date: 2025/4/1
 * @Version: 1.0
 * @description: 分类服务实现类
 */

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> findAll() {
        return categoryMapper.findAll();
    }

    @Override
    public Category findById(String id) {
        return categoryMapper.findById(id);
    }
}