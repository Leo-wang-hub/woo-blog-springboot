package com.woo.service;

import com.woo.domain.ResponseResult;
import com.woo.domain.entity.User;

public interface WooLoginService {

    ResponseResult login(User user);

    ResponseResult logout();
}
