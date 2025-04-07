package com.jojun.groupbuy.pojo;

import lombok.Data;

/**
 * @className: ProductQuery
 * @author: JOJUN-CJL
 * @date: 2025/4/1
 * @Version: 1.0
 * @description: 商品查询参数类
 */
@Data
public class ProductQuery {
    private Integer page = 1;     // 当前页码，默认为1
    private Integer pageSize = 10; // 每页条数，默认为10
    private String keyword;        // 搜索关键字
    private String sortField;      // 排序字段
    private Boolean sortOrder;     // 排序顺序，true为升序，false为降序
}
