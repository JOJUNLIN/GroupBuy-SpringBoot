package com.jojun.groupbuy.mapper;

import com.jojun.groupbuy.pojo.Attendant;
import com.jojun.groupbuy.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @interfaceName: AttendantMapper
 * @author: JOJUN-CJL
 * @date: 2025/5/6
 * @Version: 1.0
 * @description: 管理员信息Mapper接口
 */

@Mapper
public interface AttendantMapper {

    /**
     * 根据用户名查询用户
     * @param name 用户名
     * @return 管理员信息
     */
    @Select("select * from attendant where name = #{name}")
    Attendant findByAttendantName(String name);

    /**
     * 添加用户
     * @param name 用户名
     * @param password 密码
     */
    @Insert("insert  into  attendant (name,password)" +
            " values (#{name},#{password})")
    void add_attendant(String name, String password);
}
