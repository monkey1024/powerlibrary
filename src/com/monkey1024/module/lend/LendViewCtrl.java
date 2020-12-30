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
        Book book = new Book(1, "java实战入门", "张三", Constant.TYPE_COMPUTER, "12-987", "XX出版社", Constant.STATUS_STORAGE);
        User user = new User(1, "张三", "正常", new BigDecimal(("100")));
        LocalDate now = LocalDate.now();
        lends.add(new Lend(1,book,user, Constant.LEND_LEND, now,now.plusDays(30)));

        c1.setCellValueFactory(new PropertyValueFactory<>("id"));
        //获取图书名称
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
        查询
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
        还书
     */
    @FXML
    private void returnBook(){
        Lend lend = this.lendTableView.getSelectionModel().getSelectedItem();
        if (lend == null){
            Alerts.warning("未选择","请先选择要归还的书籍");
            return;
        }
        lend.setStatus(Constant.LEND_RETURN);
        lend.setReturnDate(LocalDate.now());
    }

    /*
        续借
     */
    @FXML
    private void renew(){
        Lend lend = this.lendTableView.getSelectionModel().getSelectedItem();
        if (lend == null){
            Alerts.warning("未选择","请先选择要续借的书籍");
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
        LendHandleViewCtrl controller = (LendHandleViewCtrl)loader.getController();
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
