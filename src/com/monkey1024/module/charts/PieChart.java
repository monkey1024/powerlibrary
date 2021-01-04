package com.monkey1024.module.charts;

import com.monkey1024.service.ChartService;
import com.monkey1024.service.impl.ChartServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart.Data;

import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author admin
 */
public class PieChart implements Initializable {

    @FXML
    private javafx.scene.chart.PieChart pieChart;

    private ChartService chartService = new ChartServiceImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Map<String, Integer> map = chartService.bookTypeCount();

        Data[] dataArray = new Data[map.size()];

        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
        int i = 0;//数组下标
        //将map中的数据放到数组中
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> next = iterator.next();
            dataArray[i++] = new Data(next.getKey(), next.getValue());
        }

        ObservableList<Data> pieChartData = FXCollections.observableArrayList(dataArray);
        pieChart.setData(pieChartData);
        pieChart.setClockwise(false);
    }
}
