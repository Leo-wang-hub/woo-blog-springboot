package com.woo.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class RoleMenuSelectVo {
    private List<Long> checkedKeys;
    private List<MenuTreeVo> menus;
}
