package io.maker.base.lang;

/**
 * 常用值的工具类
 */
public final class Values {

    public static final String NULL_STRING = "NULL";
    public static final String EMPTY_STRING = "";
    public static final Object NULL_OBJECT = null;

    public static int[] newIntArray(int len) {
        if (len < 0) {
            throw new ArrayIndexOutOfBoundsException("array index cannot be less than 0!");
        }
        return new int[len];
    }

    /**
     * 生成随机值
     */
    private static final class RandomValueGenerator {

    }
}
