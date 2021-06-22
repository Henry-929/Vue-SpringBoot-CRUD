package com.vueboot.service;

import com.vueboot.bean.User;

public interface UserService {
    User queryUserByUsername(String username);

    User queryById(Integer userID);
}
