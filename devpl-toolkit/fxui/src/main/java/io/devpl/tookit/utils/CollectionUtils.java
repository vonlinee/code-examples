package io.devpl.tookit.utils;

import java.awt.desktop.PreferencesEvent;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionUtils {

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }

    /**
     * 分组
     *
     * @param collection 集合
     * @param keyMapper  key映射
     * @param <K>        key
     * @param <V>        value
     * @param <C>        集合类型
     * @return 无论什么集合，都会分组为List,因为list可包含重复
     */
    public static <K, V, C extends Collection<V>> Map<K, List<V>> groupingBy(C collection, Function<? super V, ? extends K> keyMapper) {
        return collection.stream().collect(Collectors.groupingBy(keyMapper));
    }
}
