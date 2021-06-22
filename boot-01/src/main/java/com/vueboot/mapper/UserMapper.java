package com.vueboot.mapper;

import com.vueboot.bean.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User queryUserByUsername(String username);

    User queryById(Integer userID);
}
