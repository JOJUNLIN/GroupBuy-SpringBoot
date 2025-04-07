package com.jojun.groupbuy.controller;

import com.jojun.groupbuy.pojo.Promotion;
import com.jojun.groupbuy.pojo.Result;
import com.jojun.groupbuy.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @className: PromotionController
 * @author: JOJUN-CJL
 * @date: 2025/4/4
 * @Version: 1.0
 * @description: 推荐控制器
 */
@RestController
@RequestMapping("/user/promotion")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    /**
     * 获取特惠推荐信息
     * @param page 页码
     * @param pageSize 每页条数
     * @return 特惠推荐数据
     */
    @GetMapping("/special")
    public Result<Promotion> getSpecialPromotion(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        Promotion promotion = promotionService.getSpecialPromotion(page, pageSize);
        return Result.success(promotion);
    }

    /**
     * 获取爆款推荐信息
     * @param page 页码
     * @param pageSize 每页条数
     * @return 爆款推荐数据
     */
    @GetMapping("/hot")
    public Result<Promotion> getHotPromotion(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        Promotion promotion = promotionService.getHotPromotion(page, pageSize);
        return Result.success(promotion);
    }

    /**
     * 获取一站全买推荐信息
     * @param page 页码
     * @param pageSize 每页条数
     * @return 一站全买推荐数据
     */
    @GetMapping("/one-stop")
    public Result<Promotion> getOneStopPromotion(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        Promotion promotion = promotionService.getOneStopPromotion(page, pageSize);
        return Result.success(promotion);
    }

    /**
     * 获取新鲜好物推荐信息
     * @param page 页码
     * @param pageSize 每页条数
     * @return 新鲜好物推荐数据
     */
    @GetMapping("/new")
    public Result<Promotion> getNewGoodsPromotion(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        Promotion promotion = promotionService.getNewGoodsPromotion(page, pageSize);
        return Result.success(promotion);
    }

    /**
     * 根据类型获取推荐信息
     * @param type 推荐类型
     * @param page 页码
     * @param pageSize 每页条数
     * @return 推荐数据
     */
    @GetMapping("/{type}")
    public Result<Promotion> getPromotionByType(
            @PathVariable String type,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        Promotion promotion = promotionService.getPromotionByType(type, page, pageSize);
        return Result.success(promotion);
    }
}
