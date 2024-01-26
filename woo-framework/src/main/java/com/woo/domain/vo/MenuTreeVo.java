package com.woo.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
/**新增角色 获取菜单下拉书列表
 * @author woo
 * @date 2024/01/25
 */
public class MenuTreeVo {
    private static final Long serialVersionUID = 1L;
    private Long id;
    private String label;
    private Long parentId;
    private List<MenuTreeVo> children;
}
