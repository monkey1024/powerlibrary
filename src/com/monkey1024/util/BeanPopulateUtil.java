package com.monkey1024.util;

import java.lang.reflect.Field;

/**
 *  bean��ֵ������
 */
public class BeanPopulateUtil {

    /**
     *  bean�ĸ�ֵ
     * @param origin ����ֵ��bean
     * @param dest   ����bean�����Ը�ֵ��origin
     */
    public static void populate(Object origin,Object dest) {
        try {
            if (origin.getClass() != dest.getClass()) {
                throw new RuntimeException("�������������ͬһ������");
            }
            Class<?> clazz = origin.getClass();
            Field[] fields = clazz.getDeclaredFields();

            for (Field f : fields) {
                f.setAccessible(true);
                //��ֵ
                f.set(origin,f.get(dest));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
