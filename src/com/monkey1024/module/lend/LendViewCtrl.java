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
 * ͼ�����
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
        //�����鼮���ں�����
        lendList.forEach(d -> {
            LocalDate returnDate = d.getReturnDate();
            LocalDate now = LocalDate.now();
            Period period = Period.between(returnDate, now);
            User user = d.getUser();
            BigDecimal money = user.getMoney();
            BigDecimal delay;
            if (period.getDays() >= 1) {
                //�������ɽ𣬳���һ���µİ�30����
                if (period.getDays() >= 30) {
                    delay = new BigDecimal("30");
                } else {
                    delay = new BigDecimal(period.getDays());

                }

                //�ۿ�֮�󽫹黹���ڸĳɽ���
                d.setReturnDate(now);

                BigDecimal residue = money.subtract(delay);

                user.setMoney(residue);
                //�ж�����Ƿ�С��0��С��0�Ļ�Ҫ�޸��û�״̬
                if (BigDecimal.ZERO.compareTo(residue) > 0) {
                    user.setStatus(Constant.USER_FROZEN);
                }

                d.setUser(user);

                userService.update(user);
                lendService.update(d);
            }

        });


        lends.addAll(lendList);

        //��ȡͼ������
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
        ��ѯ
     */
    @FXML
    private void lendSelect() {


        List<Lend> lendList = lendService.select(null);

        lends = new ObservableListWrapper<Lend>(new ArrayList<Lend>(lendList));
        lendTableView.setItems(lends);
    }

    /*
        ����
     */
    @FXML
    private void returnBook() {
        try {
            Lend lend = this.lendTableView.getSelectionModel().getSelectedItem();
            if (lend == null) {
                Alerts.warning("δѡ��", "����ѡ��Ҫ�黹���鼮");
                return;
            }
            if (BigDecimal.ZERO.compareTo(lend.getUser().getMoney()) > 0) {
                Alerts.warning("���ɽ�", "���Ƚ������ɽ�");
                return;
            }
            List<Lend> lendList = lendService.returnBook(lend);
            lends = new ObservableListWrapper<Lend>(new ArrayList<Lend>(lendList));
            lendTableView.setItems(lends);

            Alerts.success("�ɹ�", "����ɹ�");
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.error("ʧ��", "����ʧ��");
        }
    }

    /*
        ����
     */
    @FXML
    private void renew() {
        Lend lend = this.lendTableView.getSelectionModel().getSelectedItem();
        if (lend == null) {
            Alerts.warning("δѡ��", "����ѡ��Ҫ������鼮");
            return;
        }
        lend.setReturnDate(LocalDate.now().plusDays(30));
    }


    /*
        ��ʼ��stage
     */
    private void initStage(Lend lend) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("/com/monkey1024/module/lend/LendHandleView.fxml"));
        StackPane target = (StackPane) loader.load();
        //Scene scene1 = App.getDecorator().getScene();
        Scene scene = new Scene(target);


        Stage stage = new Stage();//������̨��
        LendHandleViewCtrl controller = (LendHandleViewCtrl) loader.getController();
        controller.setStage(stage);
        controller.setLends(lends);
        controller.setLend(lend);
        controller.setLendTableView(lendTableView);
//        stage.setResizable(false);
        stage.setHeight(800);
        stage.setWidth(700);
        //���ô���ͼ��
        stage.getIcons().add(new Image("icon.png"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene); //������������̨��
        stage.show(); //��ʾ���ڣ�
    }
}
