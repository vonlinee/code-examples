package io.devpl.toolkit.utils;

import org.springframework.lang.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 集合工具类
 */
public abstract class CollectionUtils {

    public static <K, T> Map<K, T> toMap(List<T> list, Function<T, K> keyMapper, int threshold) {
        return toMap(list, keyMapper, list.size() > threshold);
    }

    public static <K, T> Map<K, T> toMap(List<T> list, Function<T, K> keyMapper) {
        return toMap(list, keyMapper, list.size() > 100);
    }

    public static <K, T> Map<K, T> toMap(List<T> list, Function<T, K> keyMapper, boolean stream) {
        return stream ? streamToMap(list, keyMapper) : foreachToMap(list, keyMapper);
    }

    public static <K, T> Map<K, T> streamToMap(List<T> list, Function<T, K> keyMapper) {
        return list.stream().collect(Collectors.toMap(keyMapper, Function.identity()));
    }

    public static <K, T> Map<K, T> foreachToMap(List<T> list, Function<T, K> keyMapper) {
        int len = list.size();
        Map<K, T> map = new HashMap<>(len);
        for (int i = 0; i < len; i++) {
            T item = list.get(i);
            map.put(keyMapper.apply(item), item);
        }
        return map;
    }

    /**
     * 判断Collection是否为空或者Null
     *
     * @param collection 集合
     * @return 是否为空或者Null
     */
    public static boolean isNullOrEmpty(@Nullable Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 判断Collection是否不为Null但是为空
     *
     * @param collection 集合
     * @return 是否不为Null但是为空
     */
    public static boolean isEmptyNotNull(@Nullable Collection<?> collection) {
        return collection != null && collection.isEmpty();
    }

    public static <E> List<E> asList(E[] elements) {
        if (elements == null || elements.length == 0) {
            return Collections.emptyList();
        }
        return addAll(new ArrayList<>(elements.length), elements);
    }

    public static <E> List<E> addAll(List<E> list, E... elements) {
        for (int i = 0; i < elements.length; i++) {
            list.add(elements[i]);
        }
        return list;
    }

    public static <T> boolean replaceAll(List<T> list, T oldVal, T newVal) {
        if (isNullOrEmpty(list)) {
            return false;
        }
        return Collections.replaceAll(list, oldVal, newVal);
    }

    public static <T> T findFirst(List<T> list, Predicate<? super T> predicate, T defaultValue) {
        return list.stream().filter(predicate).findFirst().orElse(defaultValue);
    }
}
