package com.jojun.groupbuy.mapper;

import com.jojun.groupbuy.pojo.AddressItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @interfaceName: AddressMapper
 * @author: JOJUN-CJL
 * @date: 2025/4/24
 * @Version: 1.0
 * @description: 地址Mapper接口
 */
@Mapper
public interface AddressMapper {

    /**
     * 获取地址列表
     * @return 地址列表
     */
    @Select("SELECT id, address, IFNULL(group_num, 0) as group_num FROM group_address")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "address", column = "address"),
            @Result(property = "group_num", column = "group_num")
    })
    List<AddressItem> findAddress();

    /**
     * 根据地址id查询地址
     * @return 地址
     */
    @Select("SELECT address FROM group_address WHERE id = #{id}")
    @Results({
            @Result(property = "address", column = "address")
    })
    String findById(int id);

    /**
     * 新增地址 (站点)
     * @param addressItem 要新增的地址对象
     * @return 返回影响的行数，通常是1表示成功
     */
    @Insert("INSERT INTO group_address (address, group_num) VALUES (#{address}, #{group_num})")
    int addAddress(AddressItem addressItem);
}