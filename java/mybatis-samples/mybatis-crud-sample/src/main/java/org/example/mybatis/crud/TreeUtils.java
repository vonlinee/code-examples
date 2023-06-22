package org.example.mybatis.crud;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TreeUtils {

    /**
     * 可以先排序
     * @param list list
     * @param <E>  list数据类型
     * @param <T>  树形结构节点类型
     * @return 树节点list
     */
    public static <E, T> List<T> tree(List<E> list, Function<E, String> key) {
        List<T> result = new ArrayList<>();
        for (E element : list) {

        }
        return result;
    }
}
