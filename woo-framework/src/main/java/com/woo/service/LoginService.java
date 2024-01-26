package com.woo.service;

import com.woo.domain.ResponseResult;
import com.woo.domain.entity.User;

public interface LoginService {
    public ResponseResult login(User user);

    ResponseResult logout();
}
