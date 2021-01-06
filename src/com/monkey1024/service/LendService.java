package com.monkey1024.service;

import com.monkey1024.bean.Lend;

import java.util.List;

public interface LendService {
    void add(int bookId,int userId);

    List<Lend> select(Lend lend);

    List<Lend> returnBook(Lend lend);

    void update(Lend lend);
}
