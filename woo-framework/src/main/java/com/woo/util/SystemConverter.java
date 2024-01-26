package com.woo.util;

import com.woo.domain.entity.Menu;
import com.woo.domain.vo.MenuTreeVo;
import com.woo.domain.vo.MenuVo;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SystemConverter {
    /** 新增角色-获取菜单下拉树列表
     * @param menus
     * @return {@link List}<{@link MenuTreeVo}>
     */
    public static List<MenuTreeVo> buildMenuSelectTree(List<MenuVo> menus) {
        List<MenuTreeVo> menuTreeVos = menus.stream().map(menu -> new MenuTreeVo(menu.getId(), menu.getMenuName(), menu.getParentId(), null)).collect(Collectors.toList());
        List<MenuTreeVo> options = menuTreeVos.stream().filter(menuTreeVo -> menuTreeVo.getParentId().equals(0L)).map(menuTreeVo -> menuTreeVo.setChildren(getChild(menuTreeVos, menuTreeVo))).collect(Collectors.toList());
        return options;

    }

    /**
     * 得到子节点列表
     * @param menuTreeVos
     * @param menuTreeVo
     * @return {@link List}<{@link MenuTreeVo}>
     */
    private static List<MenuTreeVo> getChild(List<MenuTreeVo> menuTreeVos, MenuTreeVo menuTreeVo) {

        List<MenuTreeVo> options = menuTreeVos.stream().filter(m -> m.getParentId().equals(menuTreeVo.getId())).map(m -> m.setChildren(getChild(menuTreeVos, m))).collect(Collectors.toList());
        return options;
    }
}
