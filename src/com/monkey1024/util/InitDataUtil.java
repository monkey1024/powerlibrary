package com.monkey1024.util;

import com.monkey1024.bean.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InitDataUtil {

    /*
        需要先执行这个main方法初始化一些数据
     */
    public static void main(String[] args) {
        //初始化用户数据
        List<User> userList = new ArrayList<>();
        userList.add(new User(1001, "王小虎", Constant.USER_OK, BigDecimal.TEN,false));
        initData(userList,PathConstant.USER_PATH);


        //图书数据初始化
        ArrayList<Book> bookList = new ArrayList<>();
        bookList.add(new Book(1, "java入门", "小明", Constant.TYPE_COMPUTER, "123-456", "机械工业出版社", Constant.STATUS_STORAGE));
        initData(bookList,PathConstant.BOOK_PATH);


        //借阅数据初始化
        List<Lend> lendList = new ArrayList<>();

        User user = new User(1001, "王小虎", Constant.USER_OK, BigDecimal.TEN,false);
        Book book = new Book(1, "java入门", "小明", Constant.TYPE_COMPUTER, "123-456", "机械工业出版社", Constant.STATUS_STORAGE);

        Lend lend = new Lend();
        lend.setId(UUID.randomUUID().toString());
        lend.setStatus(Constant.STATUS_LEND);
        lend.setUser(user);
        lend.setBook(book);
        LocalDate begin = LocalDate.now();
        lend.setLendDate(begin);
        lend.setReturnDate(begin.plusDays(30));
        lendList.add(lend);
        initData(lendList,PathConstant.LEND_PATH);
    }

    /*
        数据初始化
     */
    public static void initData(List<?> list,String path) {
        ObjectOutputStream oos = null;
        try {
            File directory = new File(path.split("/")[0] + "/");
            File file = new File(path);
            if (!directory.exists()) {
                directory.mkdir();
            }
            if (!file.exists()) {
                file.createNewFile();
                oos = new ObjectOutputStream(new FileOutputStream(path));
                oos.writeObject(list);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
