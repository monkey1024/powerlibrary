package com.monkey1024.service.impl;

import com.monkey1024.bean.Book;
import com.monkey1024.bean.Constant;
import com.monkey1024.bean.Lend;
import com.monkey1024.bean.User;
import com.monkey1024.dao.LendDao;
import com.monkey1024.dao.impl.LendDaoImpl;
import com.monkey1024.service.BookService;
import com.monkey1024.service.LendService;
import com.monkey1024.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * 借书
 */
public class LendServiceImpl implements LendService {

    private UserService userService = new UserServiceImpl();

    private BookService bookService = new BookServiceImpl();

    private LendDao lendDao = new LendDaoImpl();

    /**
     * 修改图书信息
     * @param lend
     */
    @Override
    public void update(Lend lend) {
        lendDao.update(lend);
    }

    /**
     * 添加借书信息
     * @param bookId
     * @param userId
     */
    @Override
    public void add(int bookId, int userId) {
        Book paramBook = new Book();
        paramBook.setId(bookId);
        List<Book> bookList = bookService.select(paramBook);

        User paramUser = new User();
        paramUser.setId(userId);
        List<User> userList = userService.select(paramUser);

        Lend lend = new Lend();
        //生成uuid作为借书数据的主键
        UUID uuid = UUID.randomUUID();
        lend.setId(uuid.toString());

        //设置book和user数据
        Book book = bookList.get(0);
        book.setStatus(Constant.STATUS_LEND);
        lend.setBook(book);

        User user = userList.get(0);
        user.setLend(true);//修改借阅状态
        lend.setUser(user);

        //设置借书日期和归还日期
        LocalDate begin = LocalDate.now();
        lend.setLendDate(begin);
        lend.setReturnDate(begin.of(2020,12,20));

        //设置状态
        lend.setStatus(Constant.STATUS_LEND);


        //更新图书状态
        bookService.update(book);

        //更新user状态
        userService.update(user);

        //添加出借记录
        lendDao.add(lend);
    }

    @Override
    public List<Lend> select(Lend lend) {
        return lendDao.select(lend);
    }

    /**
     *  还书
     * @param lend
     */
    @Override
    public List<Lend> returnBook(Lend lend) {

        User user = lend.getUser();
        Book book = lend.getBook();
        //修改用户状态
        user.setLend(false);
        //修改图书状态
        book.setStatus(Constant.STATUS_STORAGE);

        userService.update(user);
        bookService.update(book);
        //删除借阅数据
        lendDao.delete(lend.getId());

        List<Lend> lendList = select(null);

        return lendList;
    }
}
