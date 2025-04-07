package com.jojun.groupbuy.mapper;

import com.jojun.groupbuy.pojo.HotRecommendation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @interfaceName: HotRecommendationMapper
 * @author: JOJUN-CJL
 * @date: 2025/4/1
 * @Version: 1.0
 * @description: 热门推荐数据访问接口
 */

@Mapper
public interface HotRecommendationMapper {
    @Select("SELECT id, title, alt, target, type, pictures as picturesJson FROM hot_recommendation")
    List<HotRecommendation> findAll();

    @Select("SELECT id, title, alt, target, type, pictures as picturesJson FROM hot_recommendation WHERE type = #{type}")
    List<HotRecommendation> findByType(Integer type);

    @Select("SELECT id, title, alt, target, type, pictures as picturesJson FROM hot_recommendation WHERE id = #{id}")
    HotRecommendation findById(String id);
}
