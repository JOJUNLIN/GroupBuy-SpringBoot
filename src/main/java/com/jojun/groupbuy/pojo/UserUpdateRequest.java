package com.jojun.groupbuy.pojo;

import lombok.Data;

/**
 * @className: UserUpdateRequest
 * @author: JOJUN-CJL
 * @date: 2025/4/6
 * @Version: 1.0
 * @description: 用户信息更新请求
 */

@Data
public class UserUpdateRequest {
    private String nickname;
    private String gender;
    private String email;
    private String account;
}
