package com.woo.service.impl;

import com.woo.util.SecurityUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ps")
public class PermissionService {
    /**如果是超级管理员 直接返回true
     * @param permission
     * @return boolean
     */
    public boolean hasPermission(String permission){
        if (SecurityUtils.isAdmin()){
            return true;
        }
       //否则 获取当前用户所具有的全部权限
        List<String> permissions = SecurityUtils.getLoginUser().getPermissions();
        return permissions.contains(permission);
    }
}
