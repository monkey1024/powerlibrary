package com.monkey1024.util;

import com.monkey1024.bean.Book;
import com.monkey1024.bean.Constant;
import com.monkey1024.bean.PathConstant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class InitDataUtil {

    public static void main(String[] args) {

    }

    /*
        图书数据初始化
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
                list.add(new Book(1, "java入门", "小明", Constant.TYPE_COMPUTER, "123-456", "机械工业出版社", Constant.STATUS_STORAGE));
                oos = new ObjectOutputStream(new FileOutputStream("book/book.txt"));
                oos.writeObject(list);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
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
