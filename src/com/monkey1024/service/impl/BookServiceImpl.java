package com.monkey1024.service.impl;

import com.monkey1024.bean.Book;
import com.monkey1024.dao.BookDao;
import com.monkey1024.dao.impl.BookDaoImpl;
import com.monkey1024.service.BookService;

import java.util.List;

/*
    ͼ��service
 */
public class BookServiceImpl implements BookService {

    private BookDao bookDao = new BookDaoImpl();

    /*
        ���
     */
    @Override
    public void add(Book book) {
        bookDao.add(book);
    }

    /*
        �޸�
     */
    @Override
    public void update(Book book) {
        bookDao.update(book);
    }

    /*
        ɾ��
     */
    @Override
    public void delete(int id) {
        bookDao.delete(id);
    }

    /*
        ��ѯȫ���鼮
     */
    @Override
    public List<Book> select(Book book) {
        return bookDao.select(book);
    }
}
