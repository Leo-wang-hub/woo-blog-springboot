package com.woo.util;

import com.woo.domain.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Certificate;

public class SecurityUtils {
    /**获得用户
     * @return {@link LoginUser}
     */
    public static LoginUser getLoginUser(){
        return (LoginUser)getAuthentication().getPrincipal();
    }

    /**获取Authentication
     * @return {@link Authentication}
     */
    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**判断是否是管理员
     * @return {@link Boolean}
     */
    public static Boolean isAdmin(){
        Long id = getLoginUser().getUser().getId();
        return id != null && 1L == id;
    }

    /**获取用户id
     * @return {@link Long}
     */
    public static Long getUserId(){
        return getLoginUser().getUser().getId();
    }
}
