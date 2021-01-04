package com.monkey1024.dao.impl;

import com.monkey1024.bean.Book;
import com.monkey1024.dao.ChartDao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;
import java.util.stream.Collectors;

public class ChartDaoImpl implements ChartDao {
    @Override
    public Map<String, Integer> bookTypeCount() {

        try (
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("book/book.txt"))
        ) {
            List<Book> list = (List<Book>) ois.readObject();
            Map<String, List<Book>> collect = list.stream().collect(Collectors.groupingBy(Book::getType));

            Iterator<Map.Entry<String, List<Book>>> iterator = collect.entrySet().iterator();
            Map<String, Integer> map = new HashMap<>();

            //处理统计结果
            while (iterator.hasNext()) {
                Map.Entry<String, List<Book>> next = iterator.next();
                map.put(next.getKey(), next.getValue() == null ? 0 : next.getValue().size());
            }

            return map;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }
}
