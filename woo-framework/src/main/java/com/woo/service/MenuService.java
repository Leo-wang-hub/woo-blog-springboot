package com.woo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.woo.domain.ResponseResult;
import com.woo.domain.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2024-01-22 16:07:59
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectRouterMenuTreeByUserId(@Param("userId") Long userId);

    ResponseResult selectMenuList(Menu menu);

    boolean hasChild(Long menuId);


    List<Long> selectMenuListByRoleId(Long roleId);
}
