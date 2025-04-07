package com.jojun.groupbuy.controller;

import com.jojun.groupbuy.pojo.Banner;
import com.jojun.groupbuy.pojo.Result;
import com.jojun.groupbuy.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @className: BannerController
 * @author: JOJUN-CJL
 * @date: 2025/3/31
 * @Version: 1.0
 * @description: 轮播图控制器
 */

@RestController
@RequestMapping("/user/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    /**
     * 获取所有轮播图
     * @return 轮播图列表
     */
    @GetMapping("/list")
    public Result<List<Banner>> getBannerList() {
        List<Banner> bannerList = bannerService.findAll();
        return Result.success(bannerList);
    }

    /**
     * 根据类型获取轮播图
     * @param type 轮播图类型
     * @return 轮播图列表
     */
    @GetMapping("/type/{type}")
    public Result<List<Banner>> getBannerByType(@PathVariable String type) {
        List<Banner> bannerList = bannerService.findByType(type);
        return Result.success(bannerList);
    }
}
