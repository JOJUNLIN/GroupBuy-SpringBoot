package com.jojun.groupbuy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @className: PageResult
 * @author: JOJUN-CJL
 * @date: 2025/4/1
 * @Version: 1.0
 * @description: 分页结果包装类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    private Long counts;    // 总条数
    private Integer pageSize; // 每页条数
    private Integer pages;    // 总页数
    private Integer page;     // 当前页码
    private List<T> items;    // 当前页数据
}
