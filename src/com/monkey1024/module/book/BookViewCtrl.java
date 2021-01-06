
package com.monkey1024.module.book;

import com.gn.App;
import com.monkey1024.bean.Book;
import com.monkey1024.bean.Constant;
import com.monkey1024.global.util.Alerts;
import com.monkey1024.service.BookService;
import com.monkey1024.service.impl.BookServiceImpl;
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
import java.util.List;
import java.util.ResourceBundle;

/**
 * ͼ�����
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

    private BookService bookService = new BookServiceImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //��ѯͼ��
        List<Book> bookList = bookService.select(null);

        books.addAll(bookList);

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
        ����
     */
    @FXML
    private void lendBook() {
        try {
            Book book = this.bookTableView.getSelectionModel().getSelectedItem();
            if (book == null){
                Alerts.warning("δѡ��","����ѡ��Ҫ���ĵ��鼮");
                return;
            }
            if (!Constant.STATUS_STORAGE.equals(book.getStatus())){
                Alerts.warning("���ܳ���","���鼮�Ѿ�������");
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
                Alerts.warning("δѡ��","����ѡ��Ҫɾ��������");
                return;
            }
            bookService.delete(book.getId());
            List<Book> bookList = bookService.select(null);
            books.clear();
            books.addAll(bookList);
            bookTableView.setItems(books);
            Alerts.success("�ɹ�", "ͼ��ɾ���ɹ�");
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.error("ʧ��","ͼ��ɾ��ʧ��");
        }
    }

    /*
        ��ѯ
     */
    @FXML
    private void bookSelect(){
        String bookName = bookNameField.getText();
        String isbn = isbnField.getText();
        Book book = new Book();
        book.setBookName(bookName);
        book.setIsbn(isbn);
        List<Book> bookList = bookService.select(book);

        books = new ObservableListWrapper<Book>(new ArrayList<Book>(bookList));
        bookTableView.setItems(books);
    }

    /*
        �޸�
     */
    @FXML
    private void bookEditView(MouseEvent event) {
        try {
            Book book = this.bookTableView.getSelectionModel().getSelectedItem();
            if (book == null){
                Alerts.warning("δѡ��","����ѡ��Ҫ�޸ĵ�����");
                return;
            }

           initStage(book);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
        ���
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
        ��ʼ������stage
     */
    private void initLendStage(Book book) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("/com/monkey1024/module/book/BookLendView.fxml"));
        StackPane target = (StackPane) loader.load();
        Scene scene = new Scene(target);

        Stage stage = new Stage();//������̨��
        BookLendViewCtrl controller = (BookLendViewCtrl)loader.getController();
        controller.setStage(stage);
        controller.setBook(book);
        controller.setBookTableView(bookTableView);
        stage.setHeight(800);
        stage.setWidth(700);
        //���ô���ͼ��
        stage.getIcons().add(new Image("icon.png"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene); //������������̨��
        stage.show(); //��ʾ���ڣ�
    }

    /*
        ��ʼ��stage
     */
    private void initStage(Book book) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("/com/monkey1024/module/book/BookHandleView.fxml"));
        StackPane target = (StackPane) loader.load();
        //Scene scene1 = App.getDecorator().getScene();
        Scene scene = new Scene(target);


        Stage stage = new Stage();//������̨��
        BookHandleViewCtrl controller = (BookHandleViewCtrl)loader.getController();
        controller.setStage(stage);
        controller.setBooks(books);
        controller.setBook(book);
        controller.setBookTableView(bookTableView);
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
