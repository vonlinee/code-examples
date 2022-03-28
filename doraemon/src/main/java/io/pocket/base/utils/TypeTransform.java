package io.pocket.base.utils;

/**
 * 类型转换工具类
 */
public class TypeTransform {

    /**
     * 转为String类型
     * @param obj
     * @param defaultValue
     * @return
     */
    public static String toString(Object obj, String defaultValue) {
        try {
            return (String) obj;
        } catch (ClassCastException exception) {
            return defaultValue;
        }
    }

    public static String toString(Object obj) {
        try {
            return (String) obj;
        } catch (ClassCastException exception) {
            return "";
        }
    }

    public static Integer toInterger(Object obj, Integer defaultValue) {
        try {
            return (Integer) obj;
        } catch (ClassCastException exception) {
            if (obj instanceof String) {
                try {
                    return Integer.parseInt((String) obj);
                } catch (NumberFormatException ex) {
                    return defaultValue;
                }
            }
        }
        return defaultValue;
    }

    public static int toPrimitiveInt(Object obj) {
        return toInterger(obj, 0);
    }

    public static void main(String[] args) {
        Integer i = TypeTransform.toInterger(1203L, 12);
        System.out.println(i);
    }

}
