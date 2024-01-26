package com.woo.controler;

import com.woo.domain.ResponseResult;
import com.woo.domain.entity.Role;
import com.woo.domain.entity.User;
import com.woo.domain.vo.UserInfoAndRoleIdsVo;
import com.woo.enums.AppHttpCodeEnum;
import com.woo.exception.SystemException;
import com.woo.service.RoleService;
import com.woo.service.UserService;
import com.woo.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/user")
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("list")
    public ResponseResult list(User user, Integer pageNum, Integer pageSize){
        return userService.selectUserPage(user, pageNum, pageSize);
    }
    @PostMapping
    public ResponseResult add(@RequestBody User user) {
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        if(!userService.checkUserNameUnique(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(!userService.checkPhoneUnique(user.getPhonenumber())){
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }
        if(!userService.checkEmailUnique(user)){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        return userService.addUser(user);
    }
    @DeleteMapping("{userIds}")
    public ResponseResult delete(@PathVariable("userIds") List<Long> userIds) {
        //不能删除正在使用的用户
        if(userIds.contains(SecurityUtils.getUserId())){
            return ResponseResult.errorResult(500, "不能删除正在使用的用户");
        }
        userService.removeByIds(userIds);
        return ResponseResult.okResult();
    }
    @Autowired
    private RoleService roleService;
    @GetMapping("{userId}")
    public ResponseResult getUserInfoAndRoles(@PathVariable("userId") Long userId) {
        List<Role> roles = roleService.selectRoleAll();
        User user = userService.getById(userId);
        //当前用户所具有的用户角色
        List<String> roleIds = roleService.selectRoleByUserId(userId);
        return ResponseResult.okResult(new UserInfoAndRoleIdsVo(user, roles, roleIds));
    }
    @PutMapping
    public ResponseResult edit(@RequestBody User user) {
        userService.updateUser(user);
        return ResponseResult.okResult();

    }
    @PutMapping("changeStatus")
    public ResponseResult changeStatus(@RequestBody User user) {
        userService.updateById(user);
        return ResponseResult.okResult();
    }

}
