package com.monkey1024.service;

import com.monkey1024.bean.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {

    void add(User user);

    void update(User user);

    void delete(int id);

    List<User> select(User user);

    List<User> selectUserToLend();

    User charge(int id, BigDecimal money);

    void frozen(int id);
}
