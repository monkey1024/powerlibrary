package com.monkey1024.service.impl;

import com.monkey1024.dao.ChartDao;
import com.monkey1024.dao.impl.ChartDaoImpl;
import com.monkey1024.service.ChartService;

import java.util.Map;

public class ChartServiceImpl implements ChartService {

    private ChartDao chartDao = new ChartDaoImpl();

    @Override
    public Map<String, Integer> bookTypeCount() {
        return chartDao.bookTypeCount();
    }
}
