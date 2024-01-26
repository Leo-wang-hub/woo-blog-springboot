package com.woo.controller;

import com.woo.annotation.mySystemlog;
import com.woo.domain.ResponseResult;
import com.woo.domain.entity.User;
import com.woo.enums.AppHttpCodeEnum;
import com.woo.exception.SystemException;
import com.woo.service.WooLoginService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "用户登录的相关接口文档", description = "用户登录接口文档描述")
public class WooLoginController {
    @Autowired
    WooLoginService wooLoginService;
    @PostMapping("/login")
    @mySystemlog(businessName = "登录")
    public ResponseResult login(@RequestBody User user) {
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return wooLoginService.login(user);
    }
    @PostMapping("/logout")
    @mySystemlog(businessName = "登出")
    public ResponseResult logout(){
        return wooLoginService.logout();
    }
}
