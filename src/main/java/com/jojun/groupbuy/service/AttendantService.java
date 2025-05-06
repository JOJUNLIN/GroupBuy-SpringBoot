package com.jojun.groupbuy.service;

import com.jojun.groupbuy.pojo.Attendant;
import com.jojun.groupbuy.pojo.User;

/**
 * @interfaceName: AttendantService
 * @author: JOJUN-CJL
 * @date: 2025/5/6
 * @Version: 1.0
 * @description: 管理员信息服务类
 */

public interface AttendantService {
    // 根据用户名查询用户
    Attendant findBYAttendantName(String name);

    //注册
    void register(String name, String password);
}
