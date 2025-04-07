package com.jojun.groupbuy.service;

import com.jojun.groupbuy.pojo.Promotion;

/**
 * @interfaceName: PromotionService
 * @author: JOJUN-CJL
 * @date: 2025/4/4
 * @Version: 1.0
 * @description: 推荐服务接口
 */
public interface PromotionService {
    /**
     * 获取特惠推荐信息
     * @param page 页码
     * @param pageSize 每页条数
     * @return 特惠推荐数据
     */
    Promotion getSpecialPromotion(Integer page, Integer pageSize);

    /**
     * 获取爆款推荐信息
     * @param page 页码
     * @param pageSize 每页条数
     * @return 爆款推荐数据
     */
    Promotion getHotPromotion(Integer page, Integer pageSize);

    /**
     * 获取一站全买推荐信息
     * @param page 页码
     * @param pageSize 每页条数
     * @return 一站全买推荐数据
     */
    Promotion getOneStopPromotion(Integer page, Integer pageSize);

    /**
     * 获取新鲜好物推荐信息
     * @param page 页码
     * @param pageSize 每页条数
     * @return 新鲜好物推荐数据
     */
    Promotion getNewGoodsPromotion(Integer page, Integer pageSize);

    /**
     * 根据类型获取推荐信息
     * @param type 推荐类型
     * @param page 页码
     * @param pageSize 每页条数
     * @return 推荐数据
     */
    Promotion getPromotionByType(String type, Integer page, Integer pageSize);
}
