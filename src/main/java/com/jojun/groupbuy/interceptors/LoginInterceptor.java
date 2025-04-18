package com.jojun.groupbuy.interceptors;

import com.jojun.groupbuy.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

/**
 * @className: LoginInterceptor
 * @author: JOJUN-CJL
 * @date: 2025/3/27
 * @Version: 1.0
 * @description: 拦截器
 */

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //令牌验证
        String token = request.getHeader("Authorization");
        //验证token
        try {
            Map<String, Object> claims = JwtUtil.parseToken(token);
            //放行
            return true;
        }catch (Exception e) {
            //http响应码为401
            response.setStatus(401);
            //不放行
            return false;
        }
    }

}
