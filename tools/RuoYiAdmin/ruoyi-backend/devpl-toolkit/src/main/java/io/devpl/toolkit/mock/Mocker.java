package io.devpl.toolkit.mock;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Mocker {

    private static final String PROJECT_BASE_PACKAGE = "自己项目的包名";

    public static <T> void fillDefaultValue(T obj) {
        if (obj == null) return;
        final Class<?> clazz = obj.getClass();
    }

    public static <T> T getObjDefault(Class<T> clazz) throws IllegalAccessException, InstantiationException {
        T instance = clazz.newInstance();
        Field[] fs = clazz.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            f.setAccessible(true); // 设置属性是访问权限
            if (Modifier.isStatic(f.getModifiers())) {
                continue;
            }
            try {
                Class<?> type = f.getType();
                // 是基本数据类型，不用手动填充默认值
                if (type.isPrimitive()) continue;
                if (type.isInterface()) {
                    // 接口没办法知道具体的类型
                    continue;
                }
                // 得到此属性的值
                Object val = f.get(instance);
                // 得到此属性的类型
                String typeStr = type.toString();
                if (typeStr.endsWith("String") && val == null) {
                    f.set(instance, "");
                } else if ((typeStr.endsWith("Integer") || typeStr.endsWith("Double")) && val == null) {
                    f.set(instance, 0);
                } else if (typeStr.endsWith("Long") && val == null) {
                    f.set(instance, 0L);
                } else if (type == LocalDateTime.class) {
                    f.set(instance, LocalDateTime.now());
                } else if (type == Date.class && val == null) {
                    f.set(instance, new Date());
                } else if (typeStr.endsWith("Timestamp") && val == null) {
                    f.set(instance, Timestamp.valueOf("1970-01-01 00:00:00"));
                } else if (typeStr.endsWith("BigDecimal") && val == null) {
                    f.set(instance, new BigDecimal(0));
                } else if (type.isAssignableFrom(List.class)) {
                    f.set(instance, getObjDefaultList(f));
                } else {
                    // 对象类型
                    f.set(instance, getObjDefault(type));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public static List<Object> getObjDefaultList(Field field) throws IllegalAccessException, InstantiationException {
        // 获取List的泛型
        Type genericType = field.getGenericType();
        List<Object> list = new ArrayList<>();
        // 无泛型
        if (genericType instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) genericType;
            // 得到泛型里的class类型对象
            Class<?> actualTypeArgument = (Class<?>) pt.getActualTypeArguments()[0];
            Object obj = getObjDefault(actualTypeArgument);
            // List属性默认只填入一个
            list.add(obj);
        }
        return list;
    }
}
