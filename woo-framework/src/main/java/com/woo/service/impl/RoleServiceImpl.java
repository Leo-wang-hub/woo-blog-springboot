package com.woo.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woo.constants.SystemCantants;
import com.woo.domain.ResponseResult;
import com.woo.domain.dto.AddRoleDto;
import com.woo.domain.entity.Role;
import com.woo.domain.entity.RoleMenu;
import com.woo.domain.vo.PageVo;
import com.woo.mapper.RoleMapper;
import com.woo.service.RoleMenuService;
import com.woo.service.RoleService;
import com.woo.util.BeanCopyUtils;
import com.woo.util.SystemConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* 角色信息表(Role)表服务实现类
*
* @author makejava
* @since 2024-01-22 16:09:03
*/
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<String> selectRoleByUserId(Long id) {
        //判断是否使管理员，如果使返回集合中只需要有admin
        if(id == 1L){
            List<String> roleKeys = new ArrayList<String>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //否则查询用户所具有的角色信息
        return getBaseMapper().selectRoleByUserId(id);
    }

    @Override
    public ResponseResult selectPage(Role role, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(role.getRoleName()), Role::getRoleName, role.getRoleName());
        queryWrapper.like(StringUtils.hasText(role.getStatus()), Role::getStatus, role.getStatus());
        queryWrapper.orderByAsc(Role:: getRoleSort);
        Page<Role> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        return ResponseResult.okResult(new PageVo(page.getRecords(), page.getTotal()));
    }

    @Override
    @Transactional
    public void insertRole(AddRoleDto roleDto) {
        Role role = BeanCopyUtils.copyBean(roleDto, Role.class);
        //在调用mp的save方法之前 已经给role设置了值
        save(role);
        if(roleDto.getMenuIds() != null && roleDto.getMenuIds().size() > 0 ) {
            insertRoleMenu(roleDto,role.getId());
        }
    }

    @Override
    public void updateRole(Role role){
        updateById(role);
        roleMenuService.deleteRoleMenuByRoleId(role.getId());
        insertRoleMenu(BeanCopyUtils.copyBean(role, AddRoleDto.class), role.getId());
    }

    @Override
    public List<Role> selectRoleAll() {
       return list(new LambdaQueryWrapper<Role>().eq(Role::getStatus, SystemCantants.NORMAL));
    }

    @Autowired
    RoleMenuService roleMenuService;
    private void insertRoleMenu(AddRoleDto roleDto, Long roleId) {
        List<RoleMenu> roleMenuList = roleDto.getMenuIds().stream().map(id -> new RoleMenu(roleId, id.longValue())).collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenuList);
    }
}
