package com.jojun.groupbuy.controller;

import com.jojun.groupbuy.pojo.Attendant;
import com.jojun.groupbuy.pojo.Result;
import com.jojun.groupbuy.service.AttendantService;
import com.jojun.groupbuy.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @className: AttendantController
 * @author: JOJUN-CJL
 * @date: 2025/5/6
 * @Version: 1.0
 * @description: 管理员登录控制器
 */

@RestController
@RequestMapping("/attendant")
public class AttendantController {
    @Autowired
    private AttendantService attendantService;

    /**
     * 登录
     * @param name 用户名
     * @param password 密码
     * @return 登录结果
     */
    @PostMapping("/login")
    public Result<String> login(String name, String password) {
        // 查询用户
        Attendant attendant = attendantService.findBYAttendantName(name);
        // 判断用户是否存在
        if (attendant == null) {
            return Result.fail("用户名错误");
        }
        // 判断密码是否正确
        if (attendant.getPassword().equals(password)) {
            //登录成功
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", attendant.getId());
            claims.put("name", attendant.getName());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }

        return Result.fail("密码错误");
    }

    /**
     * 注册
     * @param name 用户名
     * @param password 密码
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result register(String name, String password) {
        // 查询用户
        Attendant attendant = attendantService.findBYAttendantName(name);
        if (attendant == null) {
            // 没有占用
            // 注册
            attendantService.register(name, password);
            return Result.success();
        }else {
            // 占用
            return Result.fail("用户已经被占用");
        }
    }
}
