package com.vueboot.mapper;

import com.vueboot.bean.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookMapperTest {

    @Autowired
    BookMapper bookMapper;

    @Test
    void selectAll() {
        for (Book book : bookMapper.selectAll()) {
            System.out.println(book.getId()+book.getName()+book.getAuthor());
        }
    }

    @Test
    void add(){
        int i = bookMapper.add(new Book(null, "spring", "张三"));
        System.out.println(i);
    }

    @Test
    void selectById(){
        System.out.println(bookMapper.selectById(1));
    }

    @Test
    void update(){
        int i = bookMapper.update(new Book(122, "springbbot", "王五"));
        System.out.println(i);
    }
}