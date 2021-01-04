package com.monkey1024.util;

import java.lang.reflect.Field;

/**
 *  bean赋值工具类
 */
public class BeanPopulateUtil {

    /**
     *  bean的赋值
     * @param origin 被赋值的bean
     * @param dest   将该bean中属性赋值给origin
     */
    public static void populate(Object origin,Object dest) {
        try {
            if (origin.getClass() != dest.getClass()) {
                throw new RuntimeException("两个对象必须是同一种类型");
            }
            Class<?> clazz = origin.getClass();
            Field[] fields = clazz.getDeclaredFields();

            for (Field f : fields) {
                f.setAccessible(true);
                //赋值
                f.set(origin,f.get(dest));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
