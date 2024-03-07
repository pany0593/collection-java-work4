package com.pany.interceptors;

import com.pany.util.JwtUtils;
import com.pany.util.RedisUtil;
import com.pany.util.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptors implements HandlerInterceptor {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private ThreadLocalUtil threadLocalUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getMethod().equals("OPTIONS")){
            response.setStatus(200);
            return true;
        }
        String token=request.getHeader("Authorization");
        try {
            String redisToken = redisUtil.get(token);
            if(redisToken==null){
                throw new RuntimeException();
            }
            Map<String, Object> claims = jwtUtils.parseToken(token);
            threadLocalUtil.set(claims);
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        threadLocalUtil.remove();
    }
}
