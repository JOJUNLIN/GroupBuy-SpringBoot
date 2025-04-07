package com.jojun.groupbuy.service;

import com.jojun.groupbuy.pojo.HotRecommendation;

import java.util.List;

/**
 * @interfaceName: HotRecommendationService
 * @author: JOJUN-CJL
 * @date: 2025/4/1
 * @Version: 1.0
 * @description: 热门推荐服务接口
 */
public interface HotRecommendationService {
    /**
     * 查询所有热门推荐
     * @return 热门推荐列表
     */
    List<HotRecommendation> findAll();

    /**
     * 根据类型查询热门推荐
     * @param type 推荐类型
     * @return 热门推荐列表
     */
    List<HotRecommendation> findByType(Integer type);

    /**
     * 根据ID查询热门推荐
     * @param id 推荐ID
     * @return 热门推荐信息
     */
    HotRecommendation findById(String id);
}
