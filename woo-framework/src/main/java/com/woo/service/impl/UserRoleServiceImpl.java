package com.woo.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woo.domain.entity.UserRole;
import com.woo.mapper.UserRoleMapper;
import com.woo.service.UserRoleService;
import org.springframework.stereotype.Service;
 /**
 * 用户和角色关联表(UserRole)表服务实现类
 *
 * @author makejava
 * @since 2024-01-25 20:29:44
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
