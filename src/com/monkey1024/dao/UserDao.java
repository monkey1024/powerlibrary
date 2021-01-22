package com.monkey1024.dao;

import com.monkey1024.bean.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserDao {
    void add(User user);

    void update(User user);

    void delete(int id);

    List<User> select(User user);

    List<User> selectUserToLend();


    void frozen(int id);


}
