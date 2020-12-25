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

package com.monkey1024;

import com.monkey1024.global.Section;
import com.monkey1024.global.User;
import com.monkey1024.global.UserDetail;
import com.monkey1024.global.plugin.SectionManager;
import com.monkey1024.global.plugin.UserManager;
import com.monkey1024.global.plugin.ViewManager;
import com.gn.decorator.GNDecorator;
import com.gn.decorator.options.ButtonType;
import com.monkey1024.module.loader.Loader;
import com.monkey1024.module.main.Main;
import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Init the app class.
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  07/10/2018
 * Version 1.0
 */
public class App extends Application {

    private float  increment = 0;
    private float  progress = 0;
    private Section section;
    private User user;

    @Override
    public synchronized void init(){
        section = SectionManager.get();

        if(section.isLogged()){
            user = UserManager.get(section.getUserLogged());
            userDetail = new UserDetail(section.getUserLogged(), user.getFullName(), "subtitle");
        } else {
            userDetail = new UserDetail();
        }

        float total = 43; // the difference represents the views not loaded
        increment = 100f / total;

        load("book", "BookView");
        load("book", "BookHandleView");
        load("book", "BookLendView");

        load("user","UserView");
        load("user","UserHandleView");
        load("user","UserSelectView");
        load("user","UserChargeView");

        load("lend","LendView");
        load("lend","LendHandleView");

        load("dashboard", "dashboard");

        load("charts", "piechart");

        load("main",     "main");

        load("profile", "profile");

        load("login", "login");
        load("login", "account");

        // delay
        try {
            wait(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop(){

    }

    public static final GNDecorator decorator = new GNDecorator();
    public static final Scene scene = decorator.getScene();

    public static ObservableList<String>    stylesheets;
    public static HostServices              hostServices;
    private static UserDetail userDetail = null;

    public static GNDecorator getDecorator(){
        return decorator;
    }

    private void configServices(){
        hostServices = getHostServices();
    }

    private void initialScene(){

        decorator.setTitle("Í¼Êé¹ÜÀí");
//        decorator.setIcon(null);
        decorator.addButton(ButtonType.FULL_EFFECT);
        decorator.initTheme(GNDecorator.Theme.DEFAULT);
//        decorator.fullBody();

        String log = logged();
        assert log != null;
        //decorator.setContent(ViewManager.getInstance().get("login"));
        if (log.equals("account") || log.equals("login")) {
            decorator.setContent(ViewManager.getInstance().get(log));
        } else {
            App.decorator.addCustom(userDetail);
            userDetail.setProfileAction(event -> {
                Main.ctrl.title.setText("Profile");
                Main.ctrl.body.setContent(ViewManager.getInstance().get("profile"));
                userDetail.getPopOver().hide();
            });

            userDetail.setSignAction(event -> {
                App.decorator.setContent(ViewManager.getInstance().get("login"));
                section.setLogged(false);
                SectionManager.save(section);
                userDetail.getPopOver().hide();
                if(Main.popConfig.isShowing()) Main.popConfig.hide();
                if(Main.popup.isShowing()) Main.popup.hide();
                App.decorator.removeCustom(userDetail);
            });
            decorator.setContent(ViewManager.getInstance().get("main"));
        }

        decorator.getStage().setOnCloseRequest(event -> {
            App.getUserDetail().getPopOver().hide();
            if(Main.popConfig.isShowing()) Main.popConfig.hide();
            if(Main.popup.isShowing()) Main.popup.hide();
            Platform.exit();
        });
    }

    @Override
    public  void start(Stage primary) {

        configServices();
        initialScene();

        stylesheets = decorator.getScene().getStylesheets();

        stylesheets.addAll(
                getClass().getResource("/com/monkey1024/theme/css/fonts.css").toExternalForm(),
                getClass().getResource("/com/monkey1024/theme/css/material-color.css").toExternalForm(),
                getClass().getResource("/com/monkey1024/theme/css/skeleton.css").toExternalForm(),
                getClass().getResource("/com/monkey1024/theme/css/light.css").toExternalForm(),
                getClass().getResource("/com/monkey1024/theme/css/bootstrap.css").toExternalForm(),
                getClass().getResource("/com/monkey1024/theme/css/shape.css").toExternalForm(),
                getClass().getResource("/com/monkey1024/theme/css/typographic.css").toExternalForm(),
                getClass().getResource("/com/monkey1024/theme/css/helpers.css").toExternalForm(),
                getClass().getResource("/com/monkey1024/theme/css/master.css").toExternalForm()
        );

        decorator.setMaximized(true);
        decorator.getStage().getIcons().add(new Image("/com/monkey1024/module/media/logo2.png"));
        decorator.show();

//        ScenicView.show(decorator.getScene());
    }

    public static void main(String[] args) {
        LauncherImpl.launchApplication(App.class, Loader.class, args);
    }

    private void load(String module, String name){
        try {
            ViewManager.getInstance().put(
                    name,
                    FXMLLoader.load(getClass().getResource("/com/monkey1024/module/" + module + "/" + name + ".fxml"))
            );
            preloaderNotify();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void preloaderNotify() {
        progress += increment;
        LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private String logged(){
        try {
            File file = new File("dashboard.properties");
            Properties properties = new Properties();

            if(!file.exists()){
                file.createNewFile();
                return "account";
            } else {
                FileInputStream fileInputStream = new FileInputStream(file);
                properties.load(fileInputStream);
                properties.putIfAbsent("logged", "false");
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                properties.store(fileOutputStream, "Dashboard properties");


                File directory = new File("user/");
                properties.load(fileInputStream);
                if(directory.exists()){
                    if(properties.getProperty("logged").equals("false"))
                        return "login";
                    else
                        return "main";
                } else
                    return "account";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static UserDetail getUserDetail() {
        return userDetail;
    }
}
