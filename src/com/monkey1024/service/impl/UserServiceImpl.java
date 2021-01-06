package com.monkey1024.service.impl;

import com.monkey1024.bean.Constant;
import com.monkey1024.bean.Lend;
import com.monkey1024.bean.User;
import com.monkey1024.dao.LendDao;
import com.monkey1024.dao.UserDao;
import com.monkey1024.dao.impl.LendDaoImpl;
import com.monkey1024.dao.impl.UserDaoImpl;
import com.monkey1024.service.UserService;

import java.math.BigDecimal;
import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    private LendDao lendDao = new LendDaoImpl();

    @Override
    public void add(User user) {
        userDao.add(user);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void delete(int id) {
        userDao.delete(id);
    }

    @Override
    public List<User> select(User user) {
        return userDao.select(user);
    }

    @Override
    public List<User> selectUserToLend() {
        return userDao.selectUserToLend();
    }

    /**
     * 充值，判断用户充值之后的余额是否大于0
     * @param id
     * @param money
     * @return
     */
    @Override
    public User charge(int id, BigDecimal money) {

        User paramUser = new User();
        paramUser.setId(id);
        List<User> selectUser = userDao.select(paramUser);
        User user = selectUser.get(0);
        BigDecimal sum = money.add(user.getMoney());
        if (BigDecimal.ZERO.compareTo(sum) < 0) {
            //余额大于0
            //修改用户状态
            user.setStatus(Constant.USER_OK);//解冻
            user.setMoney(sum);
            userDao.update(user);
        }else {
            //充值之后余额仍然小于0，不能解冻
            user.setMoney(sum);
            userDao.update(user);
        }

        //将充值之后的数据同步到lendList集合中
        List<Lend> lendList = lendDao.select(null);

        for (int i = 0; i < lendList.size(); i++) {
            Lend lend = lendList.get(i);
            if (lend.getUser().getId() == user.getId()) {
                lend.setUser(user);
                lendDao.update(lend);
                break;
            }
        }

        return user;
    }

    @Override
    public void frozen(int id) {
        userDao.frozen(id);
    }
}
