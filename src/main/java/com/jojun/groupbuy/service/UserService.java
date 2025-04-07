package com.jojun.groupbuy.service;

import com.jojun.groupbuy.pojo.User;

/**
 * @interfaceName: UserService
 * @author: JOJUN-CJL
 * @date: 2025/3/27
 * @Version: 1.0
 * @description:
 */

public interface UserService {
    // 根据用户名查询用户
    User findBYUserName(String username);

    //注册
    void register(String username, String password);

    // 微信小程序简易登录
    User wxMinSimpleLogin(String phoneNumber);

    // 根据用户ID查询用户
    User findById(Integer id);

    /**
     * 更新用户个人信息
     * @param userId 用户ID
     * @param nickname 昵称
     * @param gender 性别
     * @param email 邮箱
     * @param account 账号
     * @return 更新后的用户对象
     */
    User updateUserProfile(Integer userId, String nickname, String gender, String email, String account);
}
