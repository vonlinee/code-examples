package io.maker.base.utils;

/**
 * 类型转换工具类
 */
public final class TypeUtils {

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
            throw exception;
        }
    }

    /**
     * 打印日志信息
     * @param srcType
     * @param destType
     * @return
     */
    private static UnsupportedOperationException reportException(Object srcType, Class<?> destType) {
        if (srcType == null) {
            return new UnsupportedOperationException(String.format("[%s]=>[%s]", "null", destType.toString()));
        }
        return new UnsupportedOperationException(String.format("[%s]=>[%s]", srcType.getClass().toString(), destType.toString()));
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
    
	@SuppressWarnings("unchecked")
	public static <V> V cast(Object object) {
		return (V) object;
	}
    

    public static void main(String[] args) {
        Integer i = TypeUtils.toInterger(1203L, 12);
        System.out.println(i);
    }

}
