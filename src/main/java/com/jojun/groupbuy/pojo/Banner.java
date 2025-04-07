package com.jojun.groupbuy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: Banner
 * @author: JOJUN-CJL
 * @date: 2025/3/31
 * @Version: 1.0
 * @description: 轮播图实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Banner {
    private Integer id;       // 轮播图ID
    private String imgUrl;   // 图片URL
    private String hrefUrl;  // 跳转链接
    private Integer type;     // 轮播图类型
}
