package com.monkey1024.dao;

import com.monkey1024.bean.Lend;

import java.util.List;

public interface LendDao {
    void add(Lend lend);

    List<Lend> select(Lend lend);

    void delete(String id);

    void update(Lend lend);
}
