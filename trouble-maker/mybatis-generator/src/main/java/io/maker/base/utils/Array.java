package io.maker.base.utils;

public class Array<T> {

    private int order;

    // n个1维度数组 -》 n维数组
    private Array<Array<T>> array; //一维数组，存放其他维度的数组

    public Array(int order, int[] initialCapacity) {
        if (order < 0 || order > 2) {
            throw new UnsupportedOperationException("the order of this array must be 1 or 2.");
        }
        if (order != initialCapacity.length) {
            throw new UnsupportedOperationException(
                    "the order of this array must equals to the count of initial capacity.");
        }
        this.order = order;
    }
}
