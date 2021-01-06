package com.monkey1024.module.user;

import com.monkey1024.bean.Constant;
import com.monkey1024.bean.User;
import com.monkey1024.global.util.Alerts;
import com.monkey1024.service.UserService;
import com.monkey1024.service.impl.UserServiceImpl;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.math.BigDecimal;


public class UserHandleViewCtrl {

    @FXML
    private TextField userIdField;

    @FXML
    private TextField userNameField;

    @FXML
    private TextField moneyField;

    private Stage stage;

    private TableView<User> userTableView;

    private ObservableList<User> users;

    //�޸ĵ�user����
    private User user;

    private UserService userService = new UserServiceImpl();

    /*
        ��ӻ��޸�����
     */
    @FXML
    private void addOrEditUser() {
        try {
            String id = userIdField.getText();
            if ("".equals(id) || null == id) {
                //��Ӳ���
                User user = new User();
                populate(user);
                //����״̬Ϊ����
                user.setStatus(Constant.USER_OK);
                userService.add(user);
                users.add(user);
            }else {
                //�޸Ĳ���
                populate(this.user);
                userService.update(user);

                //ˢ��
                userTableView.refresh();
            }

            stage.close();
            Alerts.success("�ɹ�", "�����ɹ�");
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.error("ʧ��","����ʧ��");
        }

    }

    private void populate(User user) {
        user.setMoney(new BigDecimal(moneyField.getText()));
        user.setName(userNameField.getText());
    }

    @FXML
    private void closeView() {
        stage.close();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public ObservableList<User> getUsers() {
        return users;
    }

    public void setUsers(ObservableList<User> users) {
        this.users = users;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        if (user != null) {
            //����޸Ľ����ֵ
            userNameField.setText(user.getName());
            userIdField.setText(String.valueOf(user.getId()));
            moneyField.setText(user.getMoney().toString());
        }

    }

    public TableView<User> getUserTableView() {
        return userTableView;
    }

    public void setUserTableView(TableView<User> userTableView) {
        this.userTableView = userTableView;
    }
}
