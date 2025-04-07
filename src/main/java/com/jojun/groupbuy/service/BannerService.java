package com.jojun.groupbuy.service;

import com.jojun.groupbuy.pojo.Banner;

import java.util.List;

/**
 * @interfaceName: BannerService
 * @author: JOJUN-CJL
 * @date: 2025/3/31
 * @Version: 1.0
 * @description:
 */

public interface BannerService {
    // 根据类型查询轮播图列表
    List<Banner> findByType(String type);

    // 查询所有轮播图
    List<Banner> findAll();
}
