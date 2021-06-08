package com.vueboot.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vueboot.bean.Book;
import com.vueboot.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/selectAll/{pageNum}/{pageSize}")
    public Map<String, Object> selectAll(@PathVariable("pageNum") Integer pageNum,
                                         @PathVariable("pageSize") Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Book> bookList = bookService.selectAll();
        PageInfo<Book> pageInfo = new PageInfo<>(bookList);

        Map<String,Object> map = new HashMap<>();
        int totalPages = pageInfo.getPages();
        long totalElements = pageInfo.getTotal();
        List<Book> content = pageInfo.getList();
        map.put("content", content);
        map.put("totalPages",totalPages);
        map.put("totalElements",totalElements);
        
        return map;
    }

    @PostMapping("/add")
    public String add(@RequestBody Book book){
        int count = bookService.add(book);
        if (count == 1){
            return "success";
        }else {
            return "error";
        }
    }

    @GetMapping("/selectById/{id}")
    public Book selectById(@PathVariable("id") Integer id){
        return bookService.selectById(id);
    }

    @PutMapping("/update")
    public String update(@RequestBody Book book){
        int count = bookService.update(book);
        if (count == 1){
            return "success";
        }else {
            return "error";
        }
    }

    @DeleteMapping("/deteleById/{id}")
    public String deteleById(@PathVariable("id") Integer id){
        int count = bookService.deteleById(id);
        if (count == 1){
            return "success";
        }else {
            return "error";
        }
    }

}
