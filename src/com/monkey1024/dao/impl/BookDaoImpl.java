package com.monkey1024.dao.impl;

import com.monkey1024.bean.Book;
import com.monkey1024.bean.PathConstant;
import com.monkey1024.dao.BookDao;
import com.monkey1024.util.BeanPopulateUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
    ͼ��dao
 */
public class BookDaoImpl implements BookDao {


    /**
     * ͼ�����
     *
     * @param book
     */
    @Override
    public void add(Book book) {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(PathConstant.BOOK_PATH));
            List<Book> list = (List<Book>) ois.readObject();

            if (list != null) {
                Book lastBook = list.get(list.size() - 1);
                book.setId(lastBook.getId() + 1);
                list.add(book);
                oos = new ObjectOutputStream(new FileOutputStream(PathConstant.BOOK_PATH));
                oos.writeObject(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //���ϲ��׳��쳣��Ϣ
            throw new RuntimeException("���ͼ���������");
        }

    }

    @Override
    public void update(Book book) {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(PathConstant.BOOK_PATH));
            List<Book> list = (List<Book>) ois.readObject();
            if (list != null) {
                Book originBook = list.stream().filter(b -> b.getId() == book.getId()).findFirst().get();
                BeanPopulateUtil.populate(originBook, book);
                oos = new ObjectOutputStream(new FileOutputStream(PathConstant.BOOK_PATH));
                oos.writeObject(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //���ϲ��׳��쳣��Ϣ
            throw new RuntimeException("�޸�ͼ���������");
        }
    }

    @Override
    public void delete(int id) {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(PathConstant.BOOK_PATH));
            List<Book> list = (List<Book>) ois.readObject();
            if (list != null) {
                Book originBook = list.stream().filter(b -> b.getId() == id).findFirst().get();
                list.remove(originBook);
                oos = new ObjectOutputStream(new FileOutputStream(PathConstant.BOOK_PATH));
                oos.writeObject(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //���ϲ��׳��쳣��Ϣ
            throw new RuntimeException("ɾ��ͼ���������");
        }
    }

    /**
     * ��ѯȫ��ͼ��
     *
     * @return
     */
    @Override
    public List<Book> select(Book book) {

        try (
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("book/book.txt"))
        ) {
            List<Book> list = (List<Book>) ois.readObject();
            if (list != null) {
                if (book == null || ("".equals(book.getBookName()) && "".equals(book.getIsbn()))) {
                    return list;
                } else {
                    List<Book> conditionList = new ArrayList<>();
                    if (!"".equals(book.getBookName()) ){
                        conditionList = list.stream().filter(b -> b.getBookName().equals(book.getBookName())).collect(Collectors.toList());
                    }
                    if (!"".equals(book.getIsbn())){
                        conditionList = list.stream().filter(b -> b.getIsbn().equals(book.getIsbn())).collect(Collectors.toList());
                    }
                    return conditionList;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<Book>();
    }

}
