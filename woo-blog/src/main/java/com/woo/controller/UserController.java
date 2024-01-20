package com.woo.controller;

import com.woo.annotation.mySystemlog;
import com.woo.domain.ResponseResult;
import com.woo.domain.entity.User;
import com.woo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/userInfo")
    @mySystemlog(businessName = "查询用户信息")
    public ResponseResult userInfo() {
        return userService.userInfo();
    }
    @PutMapping("/userInfo")
    @mySystemlog(businessName = "更新用户信息")
    public ResponseResult updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }
    @PostMapping("/register")
    @mySystemlog(businessName = "用户注册")
    public ResponseResult register(@RequestBody User user){
        return userService.register(user);
    }
}
