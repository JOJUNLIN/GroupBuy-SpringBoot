package com.jojun.groupbuy.controller;

import com.jojun.groupbuy.pojo.HotRecommendation;
import com.jojun.groupbuy.pojo.Result;
import com.jojun.groupbuy.service.HotRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @className: HotRecommendationController
 * @author: JOJUN-CJL
 * @date: 2025/4/1
 * @Version: 1.0
 * @description: 热门推荐控制器
 */
@RestController
@RequestMapping("/user/recommendation")
public class HotRecommendationController {

    @Autowired
    private HotRecommendationService hotRecommendationService;

    /**
     * 获取所有热门推荐
     * @return 热门推荐列表
     */
    @GetMapping("/list")
    public Result<List<HotRecommendation>> getHotRecommendationList() {
        List<HotRecommendation> recommendationList = hotRecommendationService.findAll();
        return Result.success(recommendationList);
    }

    /**
     * 根据类型获取热门推荐
     * @param type 推荐类型
     * @return 热门推荐列表
     */
    @GetMapping("/type/{type}")
    public Result<List<HotRecommendation>> getHotRecommendationByType(@PathVariable Integer type) {
        List<HotRecommendation> recommendationList = hotRecommendationService.findByType(type);
        return Result.success(recommendationList);
    }

    /**
     * 根据ID获取热门推荐
     * @param id 推荐ID
     * @return 热门推荐信息
     */
    @GetMapping("/{id}")
    public Result<HotRecommendation> getHotRecommendationById(@PathVariable String id) {
        HotRecommendation recommendation = hotRecommendationService.findById(id);
        return Result.success(recommendation);
    }
}
