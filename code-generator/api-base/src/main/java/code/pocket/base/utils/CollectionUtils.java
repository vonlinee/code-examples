package code.pocket.base.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	
	public static <T> List<T> emptyArrayList() {
		return new ArrayList<T>(0);
	}
	
	public static <T> List<T> arrayList(int intialCapacity) {
		return new ArrayList<T>(intialCapacity);
	}
}
