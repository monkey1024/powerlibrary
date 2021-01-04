package com.monkey1024.service.impl;

import com.monkey1024.bean.Book;
import com.monkey1024.dao.BookDao;
import com.monkey1024.dao.impl.BookDaoImpl;
import com.monkey1024.service.BookService;

import java.util.List;

/*
    图书service
 */
public class BookServiceImpl implements BookService {

    private BookDao bookDao = new BookDaoImpl();

    /*
        添加
     */
    @Override
    public void add(Book book) {
        bookDao.add(book);
    }

    /*
        修改
     */
    @Override
    public void update(Book book) {
        bookDao.update(book);
    }

    /*
        删除
     */
    @Override
    public void delete(int id) {
        bookDao.delete(id);
    }

    /*
        查询全部书籍
     */
    @Override
    public List<Book> select(Book book) {
        return bookDao.select(book);
    }
}
