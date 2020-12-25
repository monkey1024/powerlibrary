package com.monkey1024.module.user;

import com.monkey1024.bean.Constant;
import com.monkey1024.bean.User;
import com.monkey1024.global.util.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.math.BigDecimal;

public class UserChargeViewCtrl {

    @FXML
    private TextField moneyField;

    private Stage stage;

    private User user;

    private TableView<User> userTableView;

    /*
        充值
     */
    @FXML
    private void charge() {
        try {
            //获取充值之前的余额
            BigDecimal before = user.getMoney();
            //本次充值的金额
            BigDecimal money = new BigDecimal(moneyField.getText());
            //计算充值之后余额是否大于0
            BigDecimal after = before.add(money);

            if (after.compareTo(BigDecimal.ZERO) >= 0) {
                //修改用户状态
                user.setStatus(Constant.USER_OK);
            }
            userTableView.refresh();
            stage.close();
            Alerts.success("成功", "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.error("失败","操作失败");
        }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public TableView<User> getUserTableView() {
        return userTableView;
    }

    public void setUserTableView(TableView<User> userTableView) {
        this.userTableView = userTableView;
    }
}
