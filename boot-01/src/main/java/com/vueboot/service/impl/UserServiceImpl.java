package com.vueboot.service.impl;

import com.vueboot.bean.User;
import com.vueboot.mapper.UserMapper;
import com.vueboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public User queryUserByUsername(String username) {
        return userMapper.queryUserByUsername(username);
    }

    @Override
    public User queryById(Integer userID) {
        return userMapper.queryById(userID);
    }
}
