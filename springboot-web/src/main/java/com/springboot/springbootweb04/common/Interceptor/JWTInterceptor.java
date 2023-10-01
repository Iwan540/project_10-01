package com.springboot.springbootweb04.common.Interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.springboot.springbootweb04.entity.SysUser;
import com.springboot.springbootweb04.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JWTInterceptor implements HandlerInterceptor {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 如果是特定的排除路径，直接返回true，不进行进一步的令牌验证
        if (request.getRequestURI().equals("/sysuser/register")) {
            return true;
        }

        String token = request.getHeader("token");
        //如果不是方法则直接通过
        if(!(handler instanceof HandlerMethod)) {
            return true;
        }
        //执行认证
        if(StrUtil.isBlank(token)) {
            System.out.println("没有token，需要用户登录");
            throw new Exception("无token，请登录");
        }

        String userId;
        userId = JWT.decode(token).getAudience().get(0);

        SysUser user = sysUserService.getById(userId);
        if(user == null) {
            System.out.println("这是无效的token，需要用户登录");
            throw new Exception("token无效");
        }
        //用户加签验证token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPasswd())).build();
        try {
            jwtVerifier.verify(token);
        }catch (JWTVerificationException e) {
            throw e;
        }
        return true;
    }
}
