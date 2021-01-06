package com.monkey1024.dao.impl;

import com.monkey1024.bean.Lend;
import com.monkey1024.bean.PathConstant;
import com.monkey1024.dao.LendDao;
import com.monkey1024.util.BeanPopulateUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LendDaoImpl implements LendDao {

    /**
     * 添加借阅数据
     *
     * @param lend
     */
    @Override
    public void add(Lend lend) {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(PathConstant.LEND_PATH));
            List<Lend> list = (List<Lend>) ois.readObject();

            if (list != null) {

                list.add(lend);
                oos = new ObjectOutputStream(new FileOutputStream(PathConstant.LEND_PATH));
                oos.writeObject(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //向上层抛出异常信息
            throw new RuntimeException("添加借阅记录出问题了");
        }
    }

    @Override
    public void update(Lend lend) {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(PathConstant.LEND_PATH));
            List<Lend> list = (List<Lend>) ois.readObject();
            if (list != null) {
                Lend originLend = list.stream().filter(u -> u.getId().equals(lend.getId())).findFirst().get();
                BeanPopulateUtil.populate(originLend, lend);
                oos = new ObjectOutputStream(new FileOutputStream(PathConstant.LEND_PATH));
                oos.writeObject(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //向上层抛出异常信息
            throw new RuntimeException("修改借阅数据出问题了");
        }
    }

    /**
     * 查询
     *
     * @param lend
     * @return
     */
    @Override
    public List<Lend> select(Lend lend) {
        try (
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PathConstant.LEND_PATH))
        ) {
            List<Lend> list = (List<Lend>) ois.readObject();
            if (lend == null || "".equals(lend.getId())) {
                return list;
            }else {
                List<Lend> conditionList = new ArrayList<>();
                if (!"".equals(lend.getId())){
                    conditionList = list.stream().filter(u -> Objects.equals(u.getId(),lend.getId())).collect(Collectors.toList());
                }
                return conditionList;
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<Lend>();
    }

    @Override
    public void delete(String id) {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(PathConstant.LEND_PATH));
            List<Lend> list = (List<Lend>) ois.readObject();
            if (list != null) {
                Lend originLend = list.stream().filter(r -> Objects.equals(id, r.getId())).findFirst().get();
                list.remove(originLend);
                oos = new ObjectOutputStream(new FileOutputStream(PathConstant.LEND_PATH));
                oos.writeObject(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //向上层抛出异常信息
            throw new RuntimeException("删除借阅数据出问题了");
        }
    }
}
