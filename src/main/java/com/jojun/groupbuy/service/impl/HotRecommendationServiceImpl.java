package com.jojun.groupbuy.service.impl;

import com.jojun.groupbuy.mapper.HotRecommendationMapper;
import com.jojun.groupbuy.pojo.HotRecommendation;
import com.jojun.groupbuy.service.HotRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @className: HotRecommendationServiceImpl
 * @author: JOJUN-CJL
 * @date: 2025/4/1
 * @Version: 1.0
 * @description: 热门推荐服务实现类
 */
@Service
public class HotRecommendationServiceImpl implements HotRecommendationService {

    @Autowired
    private HotRecommendationMapper hotRecommendationMapper;

    @Override
    public List<HotRecommendation> findAll() {
        return hotRecommendationMapper.findAll();
    }

    @Override
    public List<HotRecommendation> findByType(Integer type) {
        return hotRecommendationMapper.findByType(type);
    }

    @Override
    public HotRecommendation findById(String id) {
        return hotRecommendationMapper.findById(id);
    }
}
