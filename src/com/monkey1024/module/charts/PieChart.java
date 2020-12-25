package com.monkey1024.module.charts;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author admin
 */
public class PieChart implements Initializable {

    @FXML
    private javafx.scene.chart.PieChart pieChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<javafx.scene.chart.PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new javafx.scene.chart.PieChart.Data("计算机", 20),
                new javafx.scene.chart.PieChart.Data("文学", 12),
                new javafx.scene.chart.PieChart.Data("经济", 25),
                new javafx.scene.chart.PieChart.Data("管理", 22)
        );
        pieChart.setData(pieChartData);
        pieChart.setClockwise(false);
    }
}
