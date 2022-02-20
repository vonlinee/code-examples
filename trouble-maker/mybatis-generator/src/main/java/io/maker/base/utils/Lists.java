package io.maker.base.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * List工具类
 */
public final class Lists {

    public static boolean isEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

    @SafeVarargs
    public static <E> List<E> of(E... items) {
        return Arrays.asList(items);
    }

    @SafeVarargs
    public static <E> List<List<E>> of(List<E>... itemLists) {
        return Arrays.asList(itemLists);
    }

    @SuppressWarnings("unchecked")
    public static <E> E[][] toArray(List<List<E>> doubleList) {
        int rowCount = doubleList.size();
        int colCount = doubleList.get(0).size();
        Object[][] array = new Object[rowCount][colCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                array[i][j] = doubleList.get(i).get(j);
            }
        }
        return (E[][]) array;
    }

    /**
     * 二维数组的长度是第一个数，即E[i][j].length = i
     * @param array 二维数组
     * @param <E>   元素
     * @return 返回值
     */
    public static <E> List<List<E>> asList(E[][] array) {
        List<List<E>> result = new ArrayList<>();
        for (E[] es : array) {
            List<E> list = Arrays.asList(es);
            result.add(list);
        }
        return result;
    }


    public static void println(List<List<?>> list) {
        list.forEach(row -> {
            List<String> l = new ArrayList<>();
            for (int i = 0; i < row.size(); i++) {
                Object o = row.get(i);
                if (o == null) l.add("NULL");
                else l.add(o.toString());
            }
            List<String> l1 = new ArrayList<>();
            l.stream().max(Comparator.comparingInt(String::length)).ifPresent(s -> {
                l.forEach(item -> {
                    int maxLen = s.length();
                    System.out.printf("| %-" + maxLen + "s%n ", item);
                });
            });
        });
    }

    public static void main(String[] args) {

        //String[] param = {"Tom",
        //        "Bob", "Andrew", "Taylor Swift"};
        //List<List<?>> list = new ArrayList<>();
        //list.add(Arrays.asList(param));
        //list.add(Arrays.asList(param));
        //list.add(Arrays.asList(param));
        //list.add(Arrays.asList(param));

        String[][] arr = new String[3][4];


        //println(list);
    }
}
