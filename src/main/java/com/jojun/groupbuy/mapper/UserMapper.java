package com.jojun.groupbuy.mapper;

import com.jojun.groupbuy.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @interfaceName: UserMapper
 * @author: JOJUN-CJL
 * @date: 2025/3/27
 * @Version: 1.0
 * @description: 用户信息Mapper接口
 */

@Mapper
public interface UserMapper {
    //根据用户名查询用户
    @Select("select * from user where username=#{username}")
    User findByUserName(String username);

    //添加用户名密码
    @Insert("insert  into  user(username,password,create_time,update_time)" +
            " values (#{username},#{password},now(),now())")
    void add_name(String username, String password);

    //根据电话查询
    @Select("select * from user where mobile=#{mobile}")
    User findByMobile(String mobile);

    //添加微信用户
    @Insert("insert  into  user(mobile,password,create_time,update_time,avatar,nickname,gender)" +
            " values (#{mobile},#{password},now(),now(),#{avatar},#{nickname},#{gender})")
    void add_wxUser(String mobile, String password, String avatar, String nickname, String gender);

    // 根据ID查询用户
    @Select("select * from user where id=#{id}")
    User findById(Integer id);

    /**
     * 更新用户个人信息
     * @param userId 用户ID
     * @param nickname 昵称
     * @param gender 性别
     * @param email 邮箱
     * @param account 账户
     */
    @Update("UPDATE user SET nickname = #{nickname}, gender = #{gender}, " +
            "email = #{email}, account = #{account}, update_time = now() WHERE id = #{userId}")
    void updateUserProfile(Integer userId, String nickname, String gender, String email, String account);
}
