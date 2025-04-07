package com.jojun.groupbuy.service.impl;

import com.jojun.groupbuy.mapper.UserMapper;
import com.jojun.groupbuy.pojo.User;
import com.jojun.groupbuy.service.UserService;
import com.jojun.groupbuy.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @className: UserServiceImpl
 * @author: JOJUN-CJL
 * @date: 2025/3/27
 * @Version: 1.0
 * @description:
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findBYUserName(String username) {
        User u = userMapper.findByUserName(username);
        return u;
    }

    @Override
    public void register(String username, String password) {
        //添加
        userMapper.add_name(username,password);
    }

    @Override
    public User findById(Integer id) {
        return userMapper.findById(id);
    }

    @Override
    public User updateUserProfile(Integer userId, String nickname, String gender, String email, String account) {
        // 更新用户信息
        userMapper.updateUserProfile(userId, nickname, gender, email, account);

        // 查询并返回更新后的用户信息
        return userMapper.findById(userId);
    }

    @Override
    public User wxMinSimpleLogin(String phoneNumber) {
        // 查询是否已有该手机号用户
        User user = userMapper.findByMobile(phoneNumber);

        // 如果不存在则创建一个新用户
        if (user == null) {
            // 生成随机密码
            String password = String.valueOf((int)((Math.random() * 9 + 1) * 100000));
            //默认头像
            String avatar = "https://yanxuan-item.nosdn.127.net/188e05584a056b0e2f17e8c568da8c00.png";
            //默认昵称
            String nickname = "拼团淘儿用户";
            //默认性别：男
            String gender = "男";
            // 注册
            userMapper.add_wxUser(phoneNumber, password, avatar, nickname,gender);
            // 查询新创建的用户
            user = userMapper.findByMobile(phoneNumber);
        }

        // 生成 token
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        String token = JwtUtil.genToken(claims);

        // 构建返回结果
        return User.builder()
                .id(user.getId())
                .account(user.getAccount())
                .mobile(phoneNumber)
                .token(token)
                .password(user.getPassword())
                .createdTime(user.getCreatedTime())
                .updatedTime(user.getUpdatedTime())
                .avatar(user.getAvatar())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .gender(user.getGender())
                .build();
    }
}
