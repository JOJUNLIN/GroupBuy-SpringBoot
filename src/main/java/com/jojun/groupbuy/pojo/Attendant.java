package com.jojun.groupbuy.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: Attendant
 * @author: JOJUN-CJL
 * @date: 2025/5/6
 * @Version: 1.0
 * @description: 管理员信息实体类
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attendant {
    private int id;// 主键ID
    private String name;// 用户名
    private String password;// 密码
}
