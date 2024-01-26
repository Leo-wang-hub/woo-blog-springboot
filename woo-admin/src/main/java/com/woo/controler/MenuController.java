package com.woo.controler;

import com.woo.domain.ResponseResult;
import com.woo.domain.entity.Menu;
import com.woo.domain.vo.MenuTreeVo;
import com.woo.domain.vo.MenuVo;
import com.woo.domain.vo.RoleMenuSelectVo;
import com.woo.service.MenuService;
import com.woo.util.BeanCopyUtils;
import com.woo.util.SystemConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;
    @GetMapping("/list")
    public ResponseResult list(Menu menu) {
        return menuService.selectMenuList(menu);
    }
    @PostMapping
    public ResponseResult add(@RequestBody Menu menu) {
        menuService.save(menu);
        return ResponseResult.okResult();
    }
    @GetMapping("{menuId}")
    public ResponseResult getInfo(@PathVariable("menuId") Long menuId) {
        return ResponseResult.okResult(menuService.getById(menuId));
    }
    @PutMapping
    public ResponseResult edit(@RequestBody Menu menu){
        if(menu.getId().equals(menu.getParentId())){
            return ResponseResult.errorResult(500,"修改菜单"+menu.getMenuName()+"失败");
        }
        menuService.updateById(menu);
        return ResponseResult.okResult();
    }
    @DeleteMapping("{menuId}")
    public ResponseResult remove(@PathVariable("menuId") Long menuId)  {
        if(menuService.hasChild(menuId)){
            return ResponseResult.errorResult(500,"存在子菜单不允许进行删除");
        }
        menuService.removeById(menuId);
        return ResponseResult.okResult();
    }
    @GetMapping("/treeselect")
    public ResponseResult treeselect () {
        System.out.println("进入treeselect方法");
        List<MenuVo> menus = (List<MenuVo>) menuService.selectMenuList(new Menu()).getData();
        List<MenuTreeVo> options = SystemConverter.buildMenuSelectTree(menus);
        return ResponseResult.okResult(options);
    }

    /**根据角色id查询查询对应的菜单列表数
     * @param roleId
     * @return {@link ResponseResult}
     */
    @GetMapping("/roleMenuTreeselect/{roleId}")
    public ResponseResult roleMenuTreeSelect(@PathVariable("roleId") Long roleId){
        List<Menu> menus = (List<Menu>) menuService.selectMenuList(new Menu()).getData();
        List<Long> checkedKeys = menuService.selectMenuListByRoleId(roleId);
        List<MenuTreeVo> menuTreeVos = SystemConverter.buildMenuSelectTree(BeanCopyUtils.copyBeanList(menus, MenuVo.class));
        return ResponseResult.okResult(new RoleMenuSelectVo(checkedKeys, menuTreeVos));
    }
}
