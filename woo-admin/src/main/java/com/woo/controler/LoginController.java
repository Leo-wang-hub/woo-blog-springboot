package com.woo.controler;

import com.woo.domain.ResponseResult;
import com.woo.domain.entity.LoginUser;
import com.woo.domain.entity.Menu;
import com.woo.domain.entity.User;
import com.woo.domain.vo.AdminUserInfoVo;
import com.woo.domain.dto.RoutersVo;
import com.woo.domain.vo.UserInfoVo;
import com.woo.enums.AppHttpCodeEnum;
import com.woo.exception.SystemException;
import com.woo.service.LoginService;
import com.woo.service.MenuService;
import com.woo.service.RoleService;
import com.woo.util.BeanCopyUtils;
import com.woo.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;
    // 管理端 用户名密码 sg   1234
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;
    @GetMapping("getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo() {
        //获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户id查询权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        //根据用户id查询角色信息
        List <String> roleKeyList = roleService.selectRoleByUserId(loginUser.getUser().getId());
        //获取用户信息
        User user = loginUser.getUser();
        //封装数据返回
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms, roleKeyList, userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);

    }
    @GetMapping("/getRouters")
    public ResponseResult getRouters() {
        //获取用户id
        Long userId = SecurityUtils.getUserId();
        //根据用户id来查询menu 要求查询结果是tree的形式
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        RoutersVo routersVo = new RoutersVo(menus);
        return ResponseResult.okResult(routersVo);
    }
    @PostMapping("/user/logout")
    public ResponseResult  logout() {
        return loginService.logout();
    }


}
