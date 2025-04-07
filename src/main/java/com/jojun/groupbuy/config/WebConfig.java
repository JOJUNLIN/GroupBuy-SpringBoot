package com.jojun.groupbuy.config;

import com.jojun.groupbuy.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @className: WebConfig
 * @author: JOJUN-CJL
 * @date: 2025/3/27
 * @Version: 1.0
 * @description: 拦截器注册
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {
 
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //不拦截
        registry.addInterceptor(loginInterceptor).excludePathPatterns("/user/**");
    }
}
