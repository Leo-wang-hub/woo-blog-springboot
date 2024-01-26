package com.woo.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.woo.domain.ResponseResult;
import com.woo.domain.entity.User;
import net.sf.jsqlparser.util.validation.metadata.NamedObject;

/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2024-01-19 09:08:11
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult selectUserPage(User user, Integer pageNum, Integer pageSize);

    boolean checkUserNameUnique(String userName);

    boolean checkPhoneUnique(String phoneNumber);

    boolean checkEmailUnique(User user);

    ResponseResult addUser(User user);

    void updateUser(User user);
}
