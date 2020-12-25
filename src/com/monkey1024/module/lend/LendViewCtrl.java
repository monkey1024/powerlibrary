/*
 * Copyright (C) Gleidson Neves da Silveira
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.monkey1024.module.lend;

import com.gn.App;
import com.monkey1024.bean.Book;
import com.monkey1024.bean.Constant;
import com.monkey1024.bean.Lend;
import com.monkey1024.bean.User;
import com.monkey1024.global.util.Alerts;
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
import java.util.ArrayList;
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
    private TableColumn<Lend,String> c2;
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
    private TextField lendNameField;

    @FXML
    private TextField isbnField;

    ObservableList<Lend> lends = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Book book = new Book(1, "javaʵս����", "����", Constant.TYPE_COMPUTER, "12-987", "XX������", Constant.STATUS_STORAGE);
        User user = new User(1, "����", "����", new BigDecimal(("100")));
        LocalDate now = LocalDate.now();
        lends.add(new Lend(1,book,user, Constant.LEND_LEND, now,now.plusDays(30)));

        c1.setCellValueFactory(new PropertyValueFactory<>("id"));
        //��ȡͼ������
        c2.setCellValueFactory((TableColumn.CellDataFeatures<Lend, String> p) ->
            new SimpleObjectProperty(p.getValue().getBook().getBookName())
        );
        c3.setCellValueFactory((TableColumn.CellDataFeatures<Lend, String> p) ->
                new SimpleObjectProperty(p.getValue().getBook().getIsbn())
        );
        c4.setCellValueFactory((TableColumn.CellDataFeatures<Lend, String> p) ->
                new SimpleObjectProperty(p.getValue().getUser().getName())
        );
        c5.setCellValueFactory(new PropertyValueFactory<>("lendDate"));
        c6.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        c7.setCellValueFactory(new PropertyValueFactory<>("status"));
        lendTableView.setItems(lends);

    }


    /*
        ��ѯ
     */
    @FXML
    private void lendSelect(){
        String lendName = lendNameField.getText();
        String isbn = isbnField.getText();
        boolean lendFlag = "".equals(lendName);
        boolean isbnFlag = "".equals(isbn);
        ObservableList<Lend> result = lends;
        if (lendFlag && isbnFlag) {
            return;
        }else {
//            if (!lendFlag){
//                result = lends.filtered(s -> s.getLendName().contains(lendName));
//            }
//            if (!isbnFlag) {
//                result = lends.filtered(s -> s.getIsbn().contains(isbn));
//            }
        }

        lends = new ObservableListWrapper<Lend>(new ArrayList<Lend>(result));
        lendTableView.setItems(lends);
    }

    /*
        ����
     */
    @FXML
    private void returnBook(){
        Lend lend = this.lendTableView.getSelectionModel().getSelectedItem();
        if (lend == null){
            Alerts.warning("δѡ��","����ѡ��Ҫ�黹���鼮");
            return;
        }
        lend.setStatus(Constant.LEND_RETURN);
        lend.setReturnDate(LocalDate.now());
    }

    /*
        ����
     */
    @FXML
    private void renew(){
        Lend lend = this.lendTableView.getSelectionModel().getSelectedItem();
        if (lend == null){
            Alerts.warning("δѡ��","����ѡ��Ҫ������鼮");
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
        LendHandleViewCtrl controller = (LendHandleViewCtrl)loader.getController();
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
