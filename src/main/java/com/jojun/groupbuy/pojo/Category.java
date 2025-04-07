package com.jojun.groupbuy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: Category
 * @author: JOJUN-CJL
 * @date: 2025/4/1
 * @Version: 1.0
 * @description: 分类实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private String id;      // 分类ID
    private String name;    // 分类名称
    private String icon;    // 分类图标URL
}