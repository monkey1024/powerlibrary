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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        books.add(new Book(1, "javaʵս����", "����", Constant.TYPE_COMPUTER, "12-987", "XX������", Constant.STATUS_STORAGE));
        books.add(new Book(2, "���֮��", "����", Constant.TYPE_COMPUTER, "1245-987", "XX������", Constant.STATUS_STORAGE));
        books.add(new Book(3, "��׵������ָ��", "����", Constant.TYPE_COMPUTER, "08712-987", "XX������", Constant.STATUS_STORAGE));
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
            this.books.remove(book);
            Alerts.success("�ɹ�", "ͼ���޸ĳɹ�");
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.error("ʧ��","ͼ���޸�ʧ��");
        }
    }

    /*
        ��ѯ
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
