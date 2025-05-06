package com.jojun.groupbuy.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @className: User
 * @author: JOJUN-CJL
 * @date: 2025/3/27
 * @Version: 1.0
 * @description: 用户信息实体类
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer id;     // 主键ID
    private String username;// 用户名
    private String password;// 密码
    private String nickname;// 昵称
    private String email;   // 邮箱
    private String userPic; // 用户头像地址
    private LocalDateTime createdTime; // 创建时间
    private LocalDateTime updatedTime; // 更新时间
    private String account;  // 账户名
    private String mobile;   // 手机号
    private String token;    // 登录凭证
    private String avatar;   // 头像
    private String gender;   // 性别
}
