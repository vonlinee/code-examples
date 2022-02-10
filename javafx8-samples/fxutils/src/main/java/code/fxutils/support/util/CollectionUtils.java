package code.fxutils.support.util;

import java.io.File;
import java.util.*;

public class CollectionUtils {
    public static final List<File> EMPTY_FILE_LIST = new ArrayList<>(0);
    public static final List<String> EMPTY_STR_LIST = new ArrayList<>(0);
    public static final Map<?, ?> EMPTY_HASH_MAP = new HashMap<>(0);
    public static final Set<?> EMPTY_HASH_SET = new HashSet<>(0);
    public static final List<?> EMPTY_LINKED_LIST = new ArrayList<>(0);
    public static final Map<?, ?> EMPTY_LINKED_HASHMAP = new LinkedHashMap<>(0);

    public static <K> boolean existKey(K key, Map<K, ?> map) {
        return map.containsKey(key);
    }

    @SuppressWarnings("unchecked")
    public static <K, V> HashMap<K, V> newHashMap(int intialCapacity) {
        if (intialCapacity == 0) return (HashMap<K, V>) EMPTY_HASH_MAP;
        return new HashMap<>(intialCapacity);
    }
}
