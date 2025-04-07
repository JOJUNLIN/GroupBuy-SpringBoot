package com.jojun.groupbuy.service.impl;

import com.jojun.groupbuy.mapper.BannerMapper;
import com.jojun.groupbuy.pojo.Banner;
import com.jojun.groupbuy.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @className: BannerServiceImpl
 * @author: JOJUN-CJL
 * @date: 2025/3/31
 * @Version: 1.0
 * @description: 轮播图服务实现类
 */

@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public List<Banner> findByType(String type) {
        return bannerMapper.findByType(type);
    }

    @Override
    public List<Banner> findAll() {
        return bannerMapper.findAll();
    }
}
