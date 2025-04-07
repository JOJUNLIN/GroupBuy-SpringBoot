package com.jojun.groupbuy.mapper;

import com.jojun.groupbuy.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @interfaceName: CategoryMapper
 * @author: JOJUN-CJL
 * @date: 2025/4/1
 * @Version: 1.0
 * @description: 分类数据访问接口
 */

@Mapper
public interface CategoryMapper {
    // 查询所有分类
    @Select("select * from category")
    List<Category> findAll();

    // 根据ID查询分类
    @Select("select * from category where id = #{id}")
    Category findById(String id);
}
