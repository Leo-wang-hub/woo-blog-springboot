package com.woo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class updateRoleDto {
    private String id;
    private String roleName;
    private String roleKey;
    private Integer roleSort;
    private String status;
    private List<Integer> menuIds;
    private String remark;
}
