package io.maker.base.utils;

import io.maker.base.annotation.Nullable;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * List工具类
 */
public final class Lists {

    public static boolean isEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

    /**
     * Arrays.asList(items)
     * @param items
     * @param <E>
     * @return
     */
    @SafeVarargs
    public static <E> List<E> asList(E... items) {
        return Arrays.asList(items);
    }

    /**
     * 手动拷贝数组对象到集合
     * Arrays.asList(items)构造的List是不可变的，因此使用此方法构造一个普通的ArrayList
     * @param items
     * @param <E>
     * @return
     */
    @SafeVarargs
    public static <E> List<E> of(E ... items) {
        List<E> list = new ArrayList<>();
        for (E item : items) {
            list.add(item);
        }
        return list;
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
     *
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

    public static <E> List<E> doFilter(List<E> list, Predicate<E> rule) {
        return list.stream().filter(rule).collect(Collectors.toList());
    }

    public static void printlnDoubleList(List<List<?>> list) {
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

    public static void printlnMapList(List<Map<?, ?>> list) {
        list.forEach(System.out::println);
    }

    /**
     * Creates a <i>mutable</i> {@code ArrayList} instance containing the given
     * elements; a very thin shortcut for creating an empty list and then calling
     * {@link Iterators#addAll}.
     *
     * <p>
     * <b>Note:</b> if mutability is not required and the elements are non-null, use
     * {@link ImmutableList#copyOf(Iterator)} instead.
     */
    public static <E extends @Nullable Object> ArrayList<E> newArrayList(Iterator<? extends E> elements) {
        ArrayList<E> list = newArrayList();
        addAll(list, elements);
        return list;
    }

    public static <T> ArrayList<T> newArrayList() {
        return new ArrayList<>();
    }

    public static <T> ArrayList<T> newArrayList(int initialCapacity) {
        return new ArrayList<>(initialCapacity);
    }

    public static <E extends @Nullable Object> LinkedList<E> newLinkedList() {
        return new LinkedList<>();
    }

    public static <T extends @Nullable Object> boolean addAll(Collection<T> addTo, Iterator<? extends T> iterator) {
        Objects.requireNonNull(addTo);
        Objects.requireNonNull(iterator);
        boolean wasModified = false;
        while (iterator.hasNext()) {
            wasModified |= addTo.add(iterator.next());
        }
        return wasModified;
    }
}
