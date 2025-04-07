package com.jojun.groupbuy.controller;

import com.jojun.groupbuy.pojo.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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
@RequestMapping("/userpage")
public class FirstController {
    @GetMapping("/firstpage")
    public Result<String> firstPage() {
        return Result.success("首页");
    }
}
