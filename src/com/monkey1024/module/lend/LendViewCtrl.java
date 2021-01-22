package com.monkey1024.module.lend;

import com.gn.App;
import com.monkey1024.bean.Constant;
import com.monkey1024.bean.Lend;
import com.monkey1024.bean.User;
import com.monkey1024.global.util.Alerts;
import com.monkey1024.service.LendService;
import com.monkey1024.service.UserService;
import com.monkey1024.service.impl.LendServiceImpl;
import com.monkey1024.service.impl.UserServiceImpl;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 图书管理
 *
 * @author admin
 */
public class LendViewCtrl implements Initializable {

    @FXML
    private TableView<Lend> lendTableView;
    @FXML
    private TableColumn<Lend, String> c1;
    @FXML
    private TableColumn<Lend, String> c2;
    @FXML
    private TableColumn<Lend, String> c3;
    @FXML
    private TableColumn<Lend, String> c4;
    @FXML
    private TableColumn<Lend, String> c5;
    @FXML
    private TableColumn<Lend, String> c6;
    @FXML
    private TableColumn<Lend, String> c7;
    @FXML
    private TableColumn<Lend, String> c8;

    @FXML
    private TextField lendNameField;

    @FXML
    private TextField isbnField;

    ObservableList<Lend> lends = FXCollections.observableArrayList();

    private LendService lendService = new LendServiceImpl();
    private UserService userService = new UserServiceImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Lend> lendList = lendService.select(null);
        //计算书籍逾期后的余额
        lendList.forEach(d -> {
            LocalDate returnDate = d.getReturnDate();
            LocalDate now = LocalDate.now();
            Period period = Period.between(returnDate, now);
            User user = d.getUser();
            BigDecimal money = user.getMoney();
            BigDecimal delay;
            if (period.getDays() >= 1) {
                //计算滞纳金，超出一个月的按30天算
                if (period.getDays() >= 30) {
                    delay = new BigDecimal("30");
                } else {
                    delay = new BigDecimal(period.getDays());

                }

                //扣款之后将归还日期改成今日
                d.setReturnDate(now);

                BigDecimal residue = money.subtract(delay);

                user.setMoney(residue);
                //判断余额是否小于0，小于0的话要修改用户状态
                if (BigDecimal.ZERO.compareTo(residue) > 0) {
                    user.setStatus(Constant.USER_FROZEN);
                }

                d.setUser(user);

                userService.update(user);
                lendService.update(d);
            }

        });


        lends.addAll(lendList);

        //获取图书名称
        c1.setCellValueFactory((TableColumn.CellDataFeatures<Lend, String> p) ->
                new SimpleObjectProperty(p.getValue().getBook().getBookName())
        );
        c2.setCellValueFactory((TableColumn.CellDataFeatures<Lend, String> p) ->
                new SimpleObjectProperty(p.getValue().getBook().getIsbn())
        );
        c3.setCellValueFactory((TableColumn.CellDataFeatures<Lend, String> p) ->
                new SimpleObjectProperty(p.getValue().getUser().getName())
        );
        c4.setCellValueFactory(new PropertyValueFactory<>("lendDate"));
        c5.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        c6.setCellValueFactory(new PropertyValueFactory<>("status"));
        c7.setCellValueFactory((TableColumn.CellDataFeatures<Lend, String> p) ->
                new SimpleObjectProperty(p.getValue().getUser().getMoney())
        );
        c8.setCellValueFactory(new PropertyValueFactory<>("id"));

        lendTableView.setItems(lends);

    }


    /*
        查询
     */
    @FXML
    private void lendSelect() {


        List<Lend> lendList = lendService.select(null);

        lends = new ObservableListWrapper<Lend>(new ArrayList<Lend>(lendList));
        lendTableView.setItems(lends);
    }

    /*
        还书
     */
    @FXML
    private void returnBook() {
        try {
            Lend lend = this.lendTableView.getSelectionModel().getSelectedItem();
            if (lend == null) {
                Alerts.warning("未选择", "请先选择要归还的书籍");
                return;
            }
            if (BigDecimal.ZERO.compareTo(lend.getUser().getMoney()) > 0) {
                Alerts.warning("滞纳金", "请先缴纳滞纳金");
                return;
            }
            List<Lend> lendList = lendService.returnBook(lend);
            lends = new ObservableListWrapper<Lend>(new ArrayList<Lend>(lendList));
            lendTableView.setItems(lends);

            Alerts.success("成功", "还书成功");
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.error("失败", "还书失败");
        }
    }

    /*
        续借
     */
    @FXML
    private void renew() {
        Lend lend = this.lendTableView.getSelectionModel().getSelectedItem();
        if (lend == null) {
            Alerts.warning("未选择", "请先选择要续借的书籍");
            return;
        }
        lend.setReturnDate(LocalDate.now().plusDays(30));
    }


    /*
        初始化stage
     */
    private void initStage(Lend lend) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("/com/monkey1024/module/lend/LendHandleView.fxml"));
        StackPane target = (StackPane) loader.load();
        //Scene scene1 = App.getDecorator().getScene();
        Scene scene = new Scene(target);


        Stage stage = new Stage();//创建舞台；
        LendHandleViewCtrl controller = (LendHandleViewCtrl) loader.getController();
        controller.setStage(stage);
        controller.setLends(lends);
        controller.setLend(lend);
        controller.setLendTableView(lendTableView);
//        stage.setResizable(false);
        stage.setHeight(800);
        stage.setWidth(700);
        //设置窗口图标
        stage.getIcons().add(new Image("icon.png"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene); //将场景载入舞台；
        stage.show(); //显示窗口；
    }
}
