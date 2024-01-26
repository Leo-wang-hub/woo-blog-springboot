package com.woo.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woo.domain.entity.Role;

import java.util.List;

/**
* 角色信息表(Role)表数据库访问层
*
* @author makejava
* @since 2024-01-22 16:08:55
*/
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleByUserId(Long id);
}
