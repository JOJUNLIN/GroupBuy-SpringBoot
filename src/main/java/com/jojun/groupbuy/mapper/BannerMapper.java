package com.jojun.groupbuy.mapper;

import com.jojun.groupbuy.pojo.Banner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @interfaceName: BannerMapper
 * @author: JOJUN-CJL
 * @date: 2025/3/31
 * @Version: 1.0
 * @description:
 */

@Mapper
public interface BannerMapper {
    // 查询所有轮播图
    @Select("select * from banner where type = #{type}")
    List<Banner> findByType(String type);

    // 查询所有轮播图
    @Select("select * from banner")
    List<Banner> findAll();
}
