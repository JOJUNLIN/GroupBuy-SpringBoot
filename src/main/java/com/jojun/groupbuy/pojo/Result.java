package com.jojun.groupbuy.pojo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: Result
 * @author: JOJUN-CJL
 * @date: 2025/3/27
 * @Version: 1.0
 * @description:
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    // 业务状态码，1 表示成功，0 表示失败
    private int code;
    // 提示信息
    private String msg;
    // 响应数据
    private T result;

    // 成功响应的静态方法，无数据
    public static <T> Result<T> success() {
        return new Result<>(1, "成功", null);
    }

    // 成功响应的静态方法，带数据
    public static <T> Result<T> success(T result) {
        return new Result<>(1, "成功", result);
    }

    // 失败响应的静态方法，带提示信息
    public static <T> Result<T> fail(String msg) {
        return new Result<>(0, msg, null);
    }
}
