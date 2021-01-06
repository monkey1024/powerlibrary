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
 * ����
 */
public class LendServiceImpl implements LendService {

    private UserService userService = new UserServiceImpl();

    private BookService bookService = new BookServiceImpl();

    private LendDao lendDao = new LendDaoImpl();

    /**
     * �޸�ͼ����Ϣ
     * @param lend
     */
    @Override
    public void update(Lend lend) {
        lendDao.update(lend);
    }

    /**
     * ��ӽ�����Ϣ
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
        //����uuid��Ϊ�������ݵ�����
        UUID uuid = UUID.randomUUID();
        lend.setId(uuid.toString());

        //����book��user����
        Book book = bookList.get(0);
        book.setStatus(Constant.STATUS_LEND);
        lend.setBook(book);

        User user = userList.get(0);
        user.setLend(true);//�޸Ľ���״̬
        lend.setUser(user);

        //���ý������ں͹黹����
        LocalDate begin = LocalDate.now();
        lend.setLendDate(begin);
        lend.setReturnDate(begin.of(2020,12,20));

        //����״̬
        lend.setStatus(Constant.STATUS_LEND);


        //����ͼ��״̬
        bookService.update(book);

        //����user״̬
        userService.update(user);

        //��ӳ����¼
        lendDao.add(lend);
    }

    @Override
    public List<Lend> select(Lend lend) {
        return lendDao.select(lend);
    }

    /**
     *  ����
     * @param lend
     */
    @Override
    public List<Lend> returnBook(Lend lend) {

        User user = lend.getUser();
        Book book = lend.getBook();
        //�޸��û�״̬
        user.setLend(false);
        //�޸�ͼ��״̬
        book.setStatus(Constant.STATUS_STORAGE);

        userService.update(user);
        bookService.update(book);
        //ɾ����������
        lendDao.delete(lend.getId());

        List<Lend> lendList = select(null);

        return lendList;
    }
}
