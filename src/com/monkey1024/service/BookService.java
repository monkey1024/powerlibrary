package com.monkey1024.service;

import com.monkey1024.bean.Book;

import java.util.List;

public interface BookService {
    void add(Book book);

    void update(Book book);

    void delete(int id);

    List<Book> select(Book book);

}
