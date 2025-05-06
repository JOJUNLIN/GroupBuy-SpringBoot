package com.jojun.groupbuy.controller;

import com.jojun.groupbuy.pojo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: FirstController
 * @author: JOJUN-CJL
 * @date: 2025/3/27
 * @Version: 1.0
 * @description:
 */

@RestController
@RequestMapping("/user")
public class FirstController {
    @GetMapping("/firstpage")
    public Result<String> firstPage() {
        return Result.success("首页");
    }
}
