package com.vueboot.mapper;

import com.vueboot.bean.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface BookMapper {

    List<Book> selectAll();

    @Options(useGeneratedKeys = true, keyProperty = "id")
    int add(Book book);

    Book selectById(Integer id);

    int update(Book book);

    int deteleById(Integer id);
}
