package com.monkey1024.module.book;

import com.gn.App;
import com.monkey1024.bean.Book;
import com.monkey1024.bean.Constant;
import com.monkey1024.bean.Lend;
import com.monkey1024.bean.User;
import com.monkey1024.module.user.UserSelectViewCtrl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;


public class BookLendViewCtrl {

    @FXML
    private TextField bookIdField;

    @FXML
    private TextField bookNameField;

    @FXML
    private TextField userIdField;

    @FXML
    private TextField userNameField;

    private Stage stage;

    //借阅的书
    private Book book;

    //借阅者
    private User user;


    @FXML
    private void closeView() {
        stage.close();
    }

    @FXML
    private void add() {
        Lend lend = new Lend();
        LocalDate now = LocalDate.now();
        lend.setId(5);
        lend.setLendDate(now);
        lend.setReturnDate(now.plusDays(30));
        lend.setStatus(Constant.LEND_LEND);

        stage.close();
    }

    /*
        初始化借阅用户选择的stage
    */
    @FXML
    private void initSelectUserStage() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("/com/monkey1024/module/user/UserSelectView.fxml"));
        StackPane target = (StackPane) loader.load();
        Scene scene = new Scene(target);

        Stage stage = new Stage();//创建舞台；
        UserSelectViewCtrl controller = (UserSelectViewCtrl)loader.getController();
        controller.setStage(stage);
        controller.setBookLendViewCtrl(this);
        stage.setHeight(800);
        stage.setWidth(700);
        //设置窗口图标
        stage.getIcons().add(new Image("icon.png"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene); //将场景载入舞台；
        stage.show(); //显示窗口；
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
        bookIdField.setText(String.valueOf(book.getId()));
        bookNameField.setText(book.getBookName());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        userIdField.setText(String.valueOf(user.getId()));
        userNameField.setText(user.getName());
    }
}
