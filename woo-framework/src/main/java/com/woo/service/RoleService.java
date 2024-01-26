package com.woo.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.woo.domain.ResponseResult;
import com.woo.domain.dto.AddRoleDto;
import com.woo.domain.entity.Role;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
* 角色信息表(Role)表服务接口
*
* @author makejava
* @since 2024-01-22 16:09:00
*/
public interface RoleService extends IService<Role> {

    List<String> selectRoleByUserId(Long id);

    ResponseResult selectPage(Role role, Integer pageNum, Integer pageSize);

    void insertRole(AddRoleDto role);

    void updateRole(Role role);

    List<Role> selectRoleAll();
}
