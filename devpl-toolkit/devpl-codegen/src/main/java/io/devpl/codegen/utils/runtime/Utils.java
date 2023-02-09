package io.devpl.codegen.utils.runtime;

import io.devpl.codegen.mbpg.util.ClassUtils;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.util.*;

public class Utils {

    /**
     * 普通对象JSON初始化
     *
     * @param obj 对象
     * @return 填充对象值
     */
    private static Object fillDefaultValue(Object obj) {
        if (obj == null) {
            return null;
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        Method[] methods = obj.getClass().getDeclaredMethods();
        Map<String, Method> methodNameMethodMap = new HashMap<>(methods.length);
        for (Method method : methods) {
            methodNameMethodMap.put(method.getName().toLowerCase(), method);
        }
        for (Field field : fields) {
            Class<?> clazz = field.getType();
            String propertyName = field.getName();
            Method method = methodNameMethodMap.get("set" + propertyName.toLowerCase());
            if (method == null) {
                continue;
            }
            try {
                if (isWrapClass(clazz) || isCommonDataType(clazz)) {
                    if (clazz == Boolean.class || clazz.getName().contains("boolean")) {
                        method.invoke(obj, Boolean.TRUE);
                    } else if (clazz == Byte.class || clazz.getName().contains("byte")) {
                        method.invoke(obj, Byte.valueOf("1"));
                    } else if (clazz == Short.class || clazz.getName().contains("short")) {
                        method.invoke(obj, Short.valueOf("1"));
                    } else if (clazz == Integer.class || clazz.getName().contains("int")) {
                        method.invoke(obj, 1);
                    } else if (clazz == Long.class || clazz.getName().contains("long")) {
                        method.invoke(obj, 1L);
                    } else if (clazz == Float.class || clazz.getName().contains("float")) {
                        method.invoke(obj, 1F);
                    } else if (clazz == Double.class || clazz.getName().contains("double")) {
                        method.invoke(obj, 1D);
                    } else if (clazz == Character.class || clazz.getName().contains("char")) {
                        method.invoke(obj, '1');
                    }
                } else if (BigDecimal.class.isAssignableFrom(clazz)) {
                    method.invoke(obj, new BigDecimal("1"));
                } else if (Date.class.isAssignableFrom(clazz)) {
                    method.invoke(obj, new Date());
                } else if (String.class.isAssignableFrom(clazz)) {
                    method.invoke(obj, "测试");
                } else if (Collection.class.isAssignableFrom(clazz) || Map.class.isAssignableFrom(clazz)) {
                    Type[] type = method.getGenericParameterTypes();
                    ParameterizedType pType = (ParameterizedType) type[0];
                    Collection<Object> collection = null;
                    if (pType.getRawType().toString().endsWith("List")) {
                        collection = new ArrayList<>();
                    } else if (pType.getRawType().toString().endsWith("Set")) {
                        collection = new HashSet<>();
                    }
                    Object objValue = null;
                    try {
                        objValue = Class.forName(pType.getActualTypeArguments()[0].getTypeName()).newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (objValue == null) {
                        continue;
                    }
                    Object childObj = fillDefaultValue(objValue);
                    assert collection != null;
                    collection.add(childObj);
                    method.invoke(obj, collection);
                } else {
                    method.invoke(obj, fillDefaultValue(ClassUtils.instantiate(clazz)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    /**
     * 调用默认方法创建对象实例
     *
     * @param clazz Class对象
     * @return 创建的对象实例
     */
    public static Object newInstance(Class<?> clazz) {
        Object instance;
        try {
            Constructor<?> constructor = clazz.getConstructor((Class<?>) null);
            instance = constructor.newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return instance;
    }

    /**
     * 判断是否是基础数据类型，即 int,double,long等类似格式
     */
    public static boolean isCommonDataType(Class<?> clazz) {
        return clazz.isPrimitive();
    }

    /**
     * 判断是否是基础数据类型的包装类型
     *
     * @param clz
     * @return
     */
    public static boolean isWrapClass(Class<?> clz) {
        try {
            return ((Class<?>) clz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }
}
