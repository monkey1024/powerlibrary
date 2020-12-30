
package com.monkey1024.module.book;

import com.gn.App;
import com.monkey1024.bean.Book;
import com.monkey1024.bean.Constant;
import com.monkey1024.global.util.Alerts;
import com.sun.javafx.collections.ObservableListWrapper;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * 图书管理
 *
 * @author admin
 */
public class BookViewCtrl implements Initializable {

    @FXML
    private TableView<Book> bookTableView;
    @FXML
    private TableColumn<Book, String> c1;
    @FXML
    private TableColumn<Book, String> c2;
    @FXML
    private TableColumn<Book, String> c3;
    @FXML
    private TableColumn<Book, String> c4;
    @FXML
    private TableColumn<Book, String> c5;
    @FXML
    private TableColumn<Book, String> c6;
    @FXML
    private TableColumn<Book, String> c7;

    @FXML
    private TextField bookNameField;

    @FXML
    private TextField isbnField;

    ObservableList<Book> books = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        books.add(new Book(1, "java实战入门", "张三", Constant.TYPE_COMPUTER, "12-987", "XX出版社", Constant.STATUS_STORAGE));
        books.add(new Book(2, "编程之道", "李四", Constant.TYPE_COMPUTER, "1245-987", "XX出版社", Constant.STATUS_STORAGE));
        books.add(new Book(3, "颈椎病康复指南", "王五", Constant.TYPE_COMPUTER, "08712-987", "XX出版社", Constant.STATUS_STORAGE));
        c1.setCellValueFactory(new PropertyValueFactory<>("id"));
        c2.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        c3.setCellValueFactory(new PropertyValueFactory<>("author"));
        c4.setCellValueFactory(new PropertyValueFactory<>("type"));
        c5.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        c6.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        c7.setCellValueFactory(new PropertyValueFactory<>("status"));
        bookTableView.setItems(books);

    }

    /*
        借书
     */
    @FXML
    private void lendBook() {
        try {
            Book book = this.bookTableView.getSelectionModel().getSelectedItem();
            if (book == null){
                Alerts.warning("未选择","请先选择要借阅的书籍");
                return;
            }

            initLendStage(book);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteBook() {
        try {
            Book book = this.bookTableView.getSelectionModel().getSelectedItem();
            if (book == null){
                Alerts.warning("未选择","请先选择要删除的数据");
                return;
            }
            this.books.remove(book);
            Alerts.success("成功", "图书修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.error("失败","图书修改失败");
        }
    }

    /*
        查询
     */
    @FXML
    private void bookSelect(){
        String bookName = bookNameField.getText();
        String isbn = isbnField.getText();
        boolean bookFlag = "".equals(bookName);
        boolean isbnFlag = "".equals(isbn);
        ObservableList<Book> result = books;
        if (bookFlag && isbnFlag) {
            return;
        }else {
            if (!bookFlag){
                result = books.filtered(s -> s.getBookName().contains(bookName));
            }
            if (!isbnFlag) {
                result = books.filtered(s -> s.getIsbn().contains(isbn));
            }
        }

        books = new ObservableListWrapper<Book>(new ArrayList<Book>(result));
        bookTableView.setItems(books);
    }

    /*
        修改
     */
    @FXML
    private void bookEditView(MouseEvent event) {
        try {
            Book book = this.bookTableView.getSelectionModel().getSelectedItem();
            if (book == null){
                Alerts.warning("未选择","请先选择要修改的数据");
                return;
            }

           initStage(book);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
        添加
     */
    @FXML
    private void bookAddView() {
        try {
            initStage(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    初始化借阅stage
 */
    private void initLendStage(Book book) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("/com/monkey1024/module/book/BookLendView.fxml"));
        StackPane target = (StackPane) loader.load();
        Scene scene = new Scene(target);

        Stage stage = new Stage();//创建舞台；
        BookLendViewCtrl controller = (BookLendViewCtrl)loader.getController();
        controller.setStage(stage);
        controller.setBook(book);
        stage.setHeight(800);
        stage.setWidth(700);
        //设置窗口图标
        stage.getIcons().add(new Image("icon.png"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene); //将场景载入舞台；
        stage.show(); //显示窗口；
    }

    /*
        初始化stage
     */
    private void initStage(Book book) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("/com/monkey1024/module/book/BookHandleView.fxml"));
        StackPane target = (StackPane) loader.load();
        //Scene scene1 = App.getDecorator().getScene();
        Scene scene = new Scene(target);


        Stage stage = new Stage();//创建舞台；
        BookHandleViewCtrl controller = (BookHandleViewCtrl)loader.getController();
        controller.setStage(stage);
        controller.setBooks(books);
        controller.setBook(book);
        controller.setBookTableView(bookTableView);
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
