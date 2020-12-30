package com.monkey1024.module.user;

import com.monkey1024.bean.User;
import com.monkey1024.global.util.Alerts;
import com.monkey1024.module.book.BookLendViewCtrl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * 用户查询
 *
 * @author admin
 */
public class UserSelectViewCtrl implements Initializable {

    @FXML
    private TableView<User> userTableView;
    @FXML
    private TableColumn<User, String> c1;
    @FXML
    private TableColumn<User, String> c2;

    ObservableList<User> users = FXCollections.observableArrayList();

    private Stage stage;

    private BookLendViewCtrl bookLendViewCtrl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        users.add(new User(1, "张三", "正常", new BigDecimal(("100"))));
        users.add(new User(2, "李四", "正常", new BigDecimal(("100"))));
        users.add(new User(3, "王五", "正常", new BigDecimal(("100"))));
        c1.setCellValueFactory(new PropertyValueFactory<>("id"));
        c2.setCellValueFactory(new PropertyValueFactory<>("name"));
        userTableView.setItems(users);

    }

    @FXML
    private void userSelect() {
        User user = this.userTableView.getSelectionModel().getSelectedItem();
        if (user == null){
            Alerts.warning("未选择","请先选择用户");
            return;
        }

        bookLendViewCtrl.setUser(user);

        stage.close();

    }

    public BookLendViewCtrl getBookLendViewCtrl() {
        return bookLendViewCtrl;
    }

    public void setBookLendViewCtrl(BookLendViewCtrl bookLendViewCtrl) {
        this.bookLendViewCtrl = bookLendViewCtrl;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
