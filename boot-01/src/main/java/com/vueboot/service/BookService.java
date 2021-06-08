package com.vueboot.service;

import com.vueboot.bean.Book;

import java.util.List;

public interface BookService {
    List<Book> selectAll();

    int add(Book book);

    Book selectById(Integer id);

    int update(Book book);

    int deteleById(Integer id);
}
