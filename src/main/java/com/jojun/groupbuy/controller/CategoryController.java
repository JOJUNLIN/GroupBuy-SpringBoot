package com.jojun.groupbuy.controller;

import com.jojun.groupbuy.pojo.Category;
import com.jojun.groupbuy.pojo.Result;
import com.jojun.groupbuy.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @className: CategoryController
 * @author: JOJUN-CJL
 * @date: 2025/4/1
 * @Version: 1.0
 * @description: 分类控制器
 */

@RestController
@RequestMapping("/user/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取所有分类
     * @return 分类列表
     */
    @GetMapping("/list")
    public Result<List<Category>> getCategoryList() {
        List<Category> categoryList = categoryService.findAll();
        return Result.success(categoryList);
    }

    /**
     * 根据ID获取分类
     * @param id 分类ID
     * @return 分类信息
     */
    @GetMapping("/{id}")
    public Result<Category> getCategoryById(@PathVariable String id) {
        Category category = categoryService.findById(id);
        return Result.success(category);
    }
}
