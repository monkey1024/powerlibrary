package com.monkey1024.dao;

import com.monkey1024.bean.Book;

import java.util.List;

public interface BookDao {

    void add(Book book);

    void update(Book book);

    void delete(int id);


    List<Book> select(Book book);
}
