package com.jojun.groupbuy.controller;

import com.jojun.groupbuy.pojo.Result;
import com.jojun.groupbuy.pojo.User;
import com.jojun.groupbuy.pojo.UserUpdateRequest;
import com.jojun.groupbuy.pojo.WxLoginRequest;
import com.jojun.groupbuy.service.UserService;
import com.jojun.groupbuy.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @className: UserController
 * @author: JOJUN-CJL
 * @date: 2025/3/27
 * @Version: 1.0
 * @description:
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(String username, String password) {
        // 查询用户
        User u = userService.findBYUserName(username);
        if (u == null) {
            // 没有占用
            // 注册
            userService.register(username,password);
            return Result.success();
        }else {
            // 占用
            return Result.fail("用户已经被占用");
        }
    }

    @PostMapping("/login")
    public Result<String> login(String username, String password) {
        // 查询用户
        User loginU = userService.findBYUserName(username);
        // 判断用户是否存在
        if (loginU == null) {
            return Result.fail("用户名错误");
        }
        // 判断密码是否正确
        if (loginU.getPassword().equals(password)) {
            //登录成功
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginU.getId());
            claims.put("username", loginU.getUsername());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }

        return Result.fail("密码错误");
    }

    /**
     * 微信小程序简易登录接口
     * @param request 包含手机号的请求对象
     * @return 登录结果
     */
    @PostMapping("/login/wxMin/simple")
    public Result<User> wxMinSimpleLogin(@RequestBody WxLoginRequest request) {
        try {
            // 调用服务层进行简易登录
            User loginResult = userService.wxMinSimpleLogin(request.getPhoneNumber());
            return Result.success(loginResult);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("登录失败: " + e.getMessage());
        }
    }

    /**
     * 获取当前登录用户信息
     * @param request HTTP请求对象，用于获取请求头中的 token
     * @return 包含用户信息的响应对象
     */
    @GetMapping("/current/profile")
    public Result<User> getCurrentUser(HttpServletRequest request) {
        try {
            // 从请求头获取 token
            String token = request.getHeader("Authorization");
            if (token == null || token.isEmpty()) {
                return Result.fail("未提供登录凭证");
            }

            // 解析 token 获取用户ID
            Map<String, Object> claims = JwtUtil.parseToken(token);
            Integer userId = (Integer) claims.get("id");

            // 根据用户ID查询用户信息
            User user = userService.findById(userId);
            if (user == null) {
                return Result.fail("用户不存在");
            }

            // 返回用户信息，但不返回密码
            user.setPassword(null);
            return Result.success(user);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("获取用户信息失败: " + e.getMessage());
        }
    }

    /**
     * 更新用户个人信息
     * @param request HTTP请求对象，用于获取请求头中的 token
     * @param userUpdateRequest 包含要更新信息的请求体
     * @return 更新结果
     */
    @PutMapping("/update/profile")
    public Result<User> updateUserProfile(HttpServletRequest request,
                                          @RequestBody UserUpdateRequest userUpdateRequest) {
        try {
            // 从请求头获取 token
            String token = request.getHeader("Authorization");
            if (token == null || token.isEmpty()) {
                return Result.fail("未提供登录凭证");
            }

            // 解析 token 获取用户ID
            Map<String, Object> claims = JwtUtil.parseToken(token);
            Integer userId = (Integer) claims.get("id");

            // 调用服务更新用户信息
            User updatedUser = userService.updateUserProfile(userId,
                    userUpdateRequest.getNickname(),
                    userUpdateRequest.getGender(),
                    userUpdateRequest.getEmail(),
                    userUpdateRequest.getAccount());

            // 返回更新后的用户信息，但不返回密码
            updatedUser.setPassword(null);
            return Result.success(updatedUser);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("更新用户信息失败: " + e.getMessage());
        }
    }
}