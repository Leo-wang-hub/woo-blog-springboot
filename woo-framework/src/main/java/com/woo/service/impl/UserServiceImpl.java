package com.woo.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woo.domain.ResponseResult;
import com.woo.domain.entity.User;
import com.woo.domain.vo.UserInfoVo;
import com.woo.enums.AppHttpCodeEnum;
import com.woo.exception.SystemException;
import com.woo.mapper.UserMapper;
import com.woo.service.UserService;
import com.woo.util.BeanCopyUtils;
import com.woo.util.SecurityUtils;
import net.sf.jsqlparser.util.validation.metadata.NamedObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
