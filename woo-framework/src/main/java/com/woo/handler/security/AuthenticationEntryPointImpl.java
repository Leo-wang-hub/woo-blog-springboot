package com.woo.handler.security;

import com.alibaba.fastjson.JSON;
import com.woo.domain.ResponseResult;
import com.woo.enums.AppHttpCodeEnum;
import com.woo.util.WebUtils;
import jdk.management.resource.internal.ResourceNatives;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //输出异常信息
        authException.printStackTrace();
        ResponseResult result = null;
        if(authException instanceof BadCredentialsException) {
            result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN.getCode(), authException.getMessage());
        } else if (authException instanceof InsufficientAuthenticationException) {
            result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }else {
            result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), "认证或授权失败");
        }
        //响应给前端
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
