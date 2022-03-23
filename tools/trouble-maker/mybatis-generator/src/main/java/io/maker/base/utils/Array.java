package io.maker.base.utils;

public class Array<T> {

    private final int order;

    // n个1维度数组 -》 n维数组
    private Array<Object>[] elements; //一维数组，存放其他维度的数组

    /**
     * 仅支持一维数组或者二维数组
     * @param order           数组维度
     * @param initialCapacity 初始容量
     */
    public Array(int order, int[] initialCapacity) {
        if (order < 0 || order > 2) {
            throw new UnsupportedOperationException("the order of this array must be 1 or 2.");
        }
        if (order != initialCapacity.length) {
            throw new UnsupportedOperationException("the order of this array must equals to the count of initial capacity.");
        }
        this.order = order;
    }

    public T get(int x, int y) {
        return null;
    }

    public static void moveAll() {

    }

    @SuppressWarnings("unchecked")
    public static <T> T[] of(T... elments) {
        int len = elments.length;
        Object[] arr = new Object[len];
        System.arraycopy(elments, 0, arr, 0, len);
        return (T[]) arr;
    }

    public static void main(String[] args) {
        final Integer[] arr = Array.of(1, 2, 3, 4, 5);

    }
}
