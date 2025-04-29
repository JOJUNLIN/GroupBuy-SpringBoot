package com.jojun.groupbuy.mapper;

import com.jojun.groupbuy.pojo.AddressItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

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
}