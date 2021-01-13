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
        ��Ҫ��ִ�����main������ʼ��һЩ����
     */
    public static void main(String[] args) {
        initBook();
        initUser(null);

        initLend(null);
    }

    /*
        �������ݳ�ʼ��
     */
    public static void initLend(List<Lend> lendList) {
        ObjectOutputStream oos = null;
        try {
            File directory = new File("lend/");
            File file = new File(PathConstant.LEND_PATH);
            if (!directory.exists()) {
                directory.mkdir();
            }
            if (!file.exists()) {
                file.createNewFile();
                List<Lend> list = null;
                Lend lend = null;
                if (lendList == null) {
                    list = new ArrayList<>();

                    User user = new User(1001, "��С��", Constant.USER_OK, BigDecimal.TEN,false);
                    Book book = new Book(1, "java����", "С��", Constant.TYPE_COMPUTER, "123-456", "��е��ҵ������", Constant.STATUS_STORAGE);

                    lend = new Lend();
                    lend.setId(UUID.randomUUID().toString());
                    lend.setStatus(Constant.STATUS_LEND);
                    lend.setUser(user);
                    lend.setBook(book);
                    LocalDate begin = LocalDate.now();
                    lend.setLendDate(begin);
                    lend.setReturnDate(begin.plusDays(30));
                    list.add(lend);
                }else {
                    list = lendList;
                }

                oos = new ObjectOutputStream(new FileOutputStream(PathConstant.LEND_PATH));
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

    /*
        �û����ݳ�ʼ��
     */
    public static void initUser(List<User> userList) {
        ObjectOutputStream oos = null;
        try {
            File directory = new File("user/");
            File file = new File(PathConstant.USER_PATH);
            if (!directory.exists()) {
                directory.mkdir();
            }
            if (!file.exists()) {
                file.createNewFile();
                List<User> list = new ArrayList<>();
                if (userList == null) {
                    list.add(new User(1001, "��С��", Constant.USER_OK, BigDecimal.TEN,false));
                }else {
                    list = userList;
                }
                oos = new ObjectOutputStream(new FileOutputStream(PathConstant.USER_PATH));
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

    /*
        ͼ�����ݳ�ʼ��
     */
    public static void initBook() {
        ObjectOutputStream oos = null;
        try {
            File directory = new File("book/");
            File file = new File(PathConstant.BOOK_PATH);
            if (!directory.exists()) {
                directory.mkdir();
            }
            if (!file.exists()) {
                file.createNewFile();
                ArrayList<Book> list = new ArrayList<>();
                list.add(new Book(1, "java����", "С��", Constant.TYPE_COMPUTER, "123-456", "��е��ҵ������", Constant.STATUS_STORAGE));
                oos = new ObjectOutputStream(new FileOutputStream((PathConstant.BOOK_PATH)));
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
