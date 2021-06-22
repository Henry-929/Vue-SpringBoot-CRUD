package com.vueboot;

import com.vueboot.bean.Book;
import com.vueboot.service.BookService;
import com.vueboot.service.UserService;
import com.vueboot.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class Boot01ApplicationTests {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    JwtUtil jwtUtil;

    @Test
    void jwtTest(){
        System.out.println(jwtUtil.getTtl());
    }

    @Test
    void queryUserByUsername(){
        System.out.println(userService.queryUserByUsername("kuangshen"));
    }

    @Test
    void add(){
        bookService.add(new Book(null,"redis","张三"));
    }

    @Test
    void selectById(){
        bookService.selectById(1);
        System.out.println("==============");
        bookService.selectById(1);
    }

    @Test
    void contextLoads() {
        bookService.selectAll();
        System.out.println("==============");
        bookService.selectAll();
    }

    @Test
    void queryById(){
        System.out.println(userService.queryById(1));
    }

}
