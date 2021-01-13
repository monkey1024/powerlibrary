package com.monkey1024.dao.impl;

import com.monkey1024.bean.Constant;
import com.monkey1024.bean.PathConstant;
import com.monkey1024.bean.User;
import com.monkey1024.dao.UserDao;
import com.monkey1024.util.BeanPopulateUtil;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserDaoImpl implements UserDao {

    /**
     * ����û�
     *
     * @param user
     */
    @Override
    public void add(User user) {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(PathConstant.USER_PATH));
            List<User> list = (List<User>) ois.readObject();

            if (list != null) {
                User lastUser = list.get(list.size() - 1);
                user.setId(lastUser.getId() + 1);
                list.add(user);
                oos = new ObjectOutputStream(new FileOutputStream(PathConstant.USER_PATH));
                oos.writeObject(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //���ϲ��׳��쳣��Ϣ
            throw new RuntimeException("����û���������");
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (oos != null) {
                    oos.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * �޸��û�
     *
     * @param user
     */
    @Override
    public void update(User user) {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(PathConstant.USER_PATH));
            List<User> list = (List<User>) ois.readObject();
            if (list != null) {
                User originUser = list.stream().filter(u -> u.getId() == user.getId()).findFirst().get();
                BeanPopulateUtil.populate(originUser, user);
                oos = new ObjectOutputStream(new FileOutputStream(PathConstant.USER_PATH));
                oos.writeObject(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //���ϲ��׳��쳣��Ϣ
            throw new RuntimeException("�޸��û���������");
        }finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (oos != null) {
                    oos.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * ɾ���û�
     *
     * @param id
     */
    @Override
    public void delete(int id) {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(PathConstant.USER_PATH));
            List<User> list = (List<User>) ois.readObject();
            if (list != null) {
                User originUser = list.stream().filter(u -> u.getId() == id).findFirst().get();
                list.remove(originUser);
                oos = new ObjectOutputStream(new FileOutputStream(PathConstant.USER_PATH));
                oos.writeObject(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //���ϲ��׳��쳣��Ϣ
            throw new RuntimeException("ɾ���û���������");
        }finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (oos != null) {
                    oos.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * ��ѯ�û�
     *
     * @param user
     * @return
     */
    @Override
    public List<User> select(User user) {
        try (
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PathConstant.USER_PATH))
        ) {
            List<User> list = (List<User>) ois.readObject();
            if (list != null) {
                if (user == null || (0 == user.getId())) {
                    return list;
                } else {
                    List<User> conditionList = new ArrayList<>();
                    if (!"".equals(user.getId()) && 0 != user.getId()) {
                        conditionList = list.stream().filter(u -> u.getId() == user.getId()).collect(Collectors.toList());
                    }
                    return conditionList;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<User>();
    }

    /**
     * ��ѯ�û�״̬������δ���������
     *
     * @return
     */
    @Override
    public List<User> selectUserToLend() {
        try (
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PathConstant.USER_PATH))
        ) {
            List<User> list = (List<User>) ois.readObject();
            if (list != null) {
                List<User> conditionList = list.stream().filter(u -> u.getStatus().equals(Constant.USER_OK) && false == u.isLend()).collect(Collectors.toList());

                return conditionList;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<User>();
    }

    /**
     * ��ֵ
     *
     * @param money
     */
    @Override
    public void charge(int id, BigDecimal money) {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(PathConstant.USER_PATH));
            List<User> list = (List<User>) ois.readObject();
            if (list != null) {
                User originUser = list.stream().filter(u -> u.getId() == id).findFirst().get();
                BigDecimal sum = originUser.getMoney().add(money);
                originUser.setMoney(sum);
                oos = new ObjectOutputStream(new FileOutputStream(PathConstant.USER_PATH));
                oos.writeObject(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //���ϲ��׳��쳣��Ϣ
            throw new RuntimeException("��ֵ�û���������");
        }finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (oos != null) {
                    oos.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * ����
     *
     * @param id
     */
    @Override
    public void frozen(int id) {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(PathConstant.USER_PATH));
            List<User> list = (List<User>) ois.readObject();
            if (list != null) {
                User originUser = list.stream().filter(u -> u.getId() == id).findFirst().get();
                originUser.setStatus(Constant.USER_FROZEN);
                oos = new ObjectOutputStream(new FileOutputStream(PathConstant.USER_PATH));
                oos.writeObject(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //���ϲ��׳��쳣��Ϣ
            throw new RuntimeException("�����û���������");
        }finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (oos != null) {
                    oos.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
