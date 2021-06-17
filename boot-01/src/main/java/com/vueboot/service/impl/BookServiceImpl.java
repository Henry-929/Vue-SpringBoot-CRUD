package com.vueboot.service.impl;

import com.vueboot.bean.Book;
import com.vueboot.mapper.BookMapper;
import com.vueboot.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    @Autowired
    BookMapper bookMapper;

    @Override
    public List<Book> selectAll() {
        return bookMapper.selectAll();
    }

    @Override
    public int add(Book book) {
        return bookMapper.add(book);
    }

    @Override
    public Book selectById(Integer id) {
        return bookMapper.selectById(id);
    }

    @Override
    public int update(Book book) {
        return bookMapper.update(book);
    }

    @Override
    public int deteleById(Integer id) {
        return bookMapper.deteleById(id);
    }
}
