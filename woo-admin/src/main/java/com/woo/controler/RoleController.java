package com.woo.controler;

import com.woo.domain.ResponseResult;
import com.woo.domain.dto.AddRoleDto;
import com.woo.domain.dto.ChangeRoleStatusDto;
import com.woo.domain.entity.Menu;
import com.woo.domain.entity.Role;
import com.woo.domain.vo.MenuTreeVo;
import com.woo.service.MenuService;
import com.woo.service.RoleService;
import com.woo.util.SystemConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/system/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @GetMapping("/list")
    public ResponseResult list(Role role, Integer pageNum, Integer pageSize) {
        return roleService.selectPage(role, pageNum, pageSize);
    }
    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody ChangeRoleStatusDto roleStatusDto){
        Role role = new Role();
        role.setId(roleStatusDto.getRoleId());
        role.setStatus((roleStatusDto.getStatus()));
        return ResponseResult.okResult(roleService.updateById(role));
    }
    @PostMapping
    public ResponseResult add(@RequestBody AddRoleDto addRoleDto){
        roleService.insertRole(addRoleDto);
        return ResponseResult.okResult();
    }
    @GetMapping("{id}")
    public ResponseResult getInfo(@PathVariable("id") Long roleId){
        Role role = roleService.getById(roleId);
        return ResponseResult.okResult(role);
    }
    @PutMapping
    public ResponseResult edit (@RequestBody Role role){
        roleService.updateRole(role);
        return ResponseResult.okResult();
    }
    @DeleteMapping("{id}")
    public ResponseResult delete(@PathVariable("id") Long id) {
        roleService.removeById(id);
        return ResponseResult.okResult();
    }
    @GetMapping("/listAllRole")
    public ResponseResult listAllRole(){
       List<Role> roles = roleService.selectRoleAll();
       return ResponseResult.okResult(roles);
    }
}
