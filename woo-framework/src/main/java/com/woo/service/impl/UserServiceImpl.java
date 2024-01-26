package com.woo.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woo.domain.ResponseResult;
import com.woo.domain.entity.User;
import com.woo.domain.entity.UserRole;
import com.woo.domain.vo.PageVo;
import com.woo.domain.vo.UserInfoVo;
import com.woo.domain.vo.UserVo;
import com.woo.enums.AppHttpCodeEnum;
import com.woo.exception.SystemException;
import com.woo.mapper.UserMapper;
import com.woo.service.UserRoleService;
import com.woo.service.UserService;
import com.woo.util.BeanCopyUtils;
import com.woo.util.SecurityUtils;
import net.sf.jsqlparser.util.validation.metadata.NamedObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2024-01-19 09:08:11
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

   @Override
   public ResponseResult userInfo() {
       //获取当前用户id
       Long userId = SecurityUtils.getUserId();
       //根据用户id查询用户信息
       User user = getById(userId);
       //对用户信息进行封装
       UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
       ResponseResult result = ResponseResult.okResult(userInfoVo);
       return result;
   }

     @Override
     public ResponseResult updateUserInfo(User user) {
       updateById(user) ;
       return ResponseResult.okResult();
     }
     @Autowired
    PasswordEncoder passwordEncoder;
     @Override
     public ResponseResult register(User user) {
       //对数据进行非空判断
         if(!StringUtils.hasText(user.getUserName())){
             throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
         }
         if(!StringUtils.hasText(user.getPassword())){
             throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
         }
         if(!StringUtils.hasText(user.getNickName())){
             throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
         }
         if(!StringUtils.hasText(user.getEmail())){
             throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
         }
         //对数据是否存在进行判断
         if (userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
         }
         if(nickNameExist(user.getNickName())){
             throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
         }
         if(emailExist(user.getEmail())){
             throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
         }
         String passwordEncoded = passwordEncoder.encode(user.getPassword());
         user.setPassword(passwordEncoded);
         //存入数据库
         save(user);
         return ResponseResult.okResult();
     }

    @Override
    public ResponseResult selectUserPage(User user, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(user.getUserName()), User::getUserName, user.getUserName());
        queryWrapper.eq(StringUtils.hasText(user.getStatus()), User::getStatus, user.getStatus());
        queryWrapper.eq(StringUtils.hasText(user.getPhonenumber()), User::getPhonenumber, user.getPhonenumber());
        Page<User> page = new Page<User>(pageNum, pageSize);
        page(page, queryWrapper);
        return ResponseResult.okResult(new PageVo(BeanCopyUtils.copyBeanList(page.getRecords(), UserVo.class), page.getTotal()));
    }

    @Override
    public boolean checkUserNameUnique(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, userName);
        return count(queryWrapper) == 0 ;
    }

    @Override
    public boolean checkPhoneUnique(String phoneNumber) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhonenumber, phoneNumber);
        return count(queryWrapper) == 0;
    }

    @Override
    public boolean checkEmailUnique(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, user.getEmail());
        return count(queryWrapper) == 0;
    }

    @Override
    public ResponseResult addUser(User user) {
         //对密码进行加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        save(user);
        if(user.getRoleIds() != null && user.getRoleIds().length >0){
            insertUserRole(user);
        }
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        //删除用户与角色关联
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, user.getId());
        userRoleService.remove(queryWrapper);
        //新增用户和角色管理
        insertUserRole(user);
        updateById(user);
    }

    @Autowired
    UserRoleService userRoleService;
    private void insertUserRole(User user) {
        List<UserRole> sysUserRoles = Arrays.stream(user.getRoleIds()).map(roleId -> new UserRole(user.getId(), roleId)).collect(Collectors.toList());
        userRoleService.saveBatch(sysUserRoles);
    }

    private boolean emailExist(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail,email);
        return count(queryWrapper) > 0 ? true : false;
    }

    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName, nickName);
        return count(queryWrapper) > 0 ? true : false;
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
        return count(queryWrapper) > 0 ? true : false;
    }

}
