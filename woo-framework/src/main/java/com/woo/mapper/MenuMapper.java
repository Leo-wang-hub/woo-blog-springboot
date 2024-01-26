package com.woo.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woo.domain.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2024-01-22 16:07:56
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByOtherUserId(Long id);


    List<Menu> selectAllRouterMenu();

    List<Menu> selectOtherRouterMenuTreeByUserId(Long userId);
    List<Long> selectMenuListByRoleId(@Param("roleId") Long roleId);
}
