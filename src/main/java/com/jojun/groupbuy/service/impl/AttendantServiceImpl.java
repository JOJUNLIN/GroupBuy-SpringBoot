package com.jojun.groupbuy.service.impl;

import com.jojun.groupbuy.mapper.AttendantMapper;
import com.jojun.groupbuy.pojo.Attendant;
import com.jojun.groupbuy.service.AttendantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @className: AttendantServiceImpl
 * @author: JOJUN-CJL
 * @date: 2025/5/6
 * @Version: 1.0
 * @description: 管理员信息服务实现类
 */

@Service
public class AttendantServiceImpl implements AttendantService {
    @Autowired
    private AttendantMapper attendantMapper;

    /**
     * 根据用户名查询用户
     * @param name 用户名
     * @return 管理员信息
     */
    @Override
    public Attendant findBYAttendantName(String name){
        Attendant attendant = attendantMapper.findByAttendantName(name);
        return attendant;
    }

    /**
     * 添加用户
     * @param name 用户名
     * @param password 密码
     */
    @Override
    public void register(String name, String password) {
        //添加
        attendantMapper.add_attendant(name,password);
    }
}
