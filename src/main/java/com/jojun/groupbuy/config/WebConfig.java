package com.jojun.groupbuy.config;

import com.jojun.groupbuy.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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
        registry.addInterceptor(loginInterceptor)
                .excludePathPatterns("/user/**","/attendant/login","/attendant/register");
    }

    // 添加CORS配置
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 对所有路径生效
                .allowedOrigins("http://localhost:12344") // 允许前端源
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的 HTTP 方法
                .allowedHeaders("*") // 允许所有请求头
                .allowCredentials(true) // 是否允许发送 Cookie，如果需要操作 session 或 token cookie
                .maxAge(3600); // 预检请求的有效期，单位秒
    }
}
