package io.devpl.sdk.util;

import java.util.Map;

/**
 * 针对DataObject的工具类
 */
public final class DataClass {

    /**
     * 默认初始化数据个数
     */
    private static final int DEFAULT_INITIAL_CAPACITY = 5;

    private DataClass() {
    }

    public static DataObject newObject(boolean strict) {
        return new DefaultDataObject(strict, 5);
    }

    public static DataObject newObject() {
        return new DefaultDataObject(5);
    }

    public static DataObject newObject(int initialCapacity) {
        return new DefaultDataObject(initialCapacity);
    }

    public static DataObject newObject(Map<String, Object> data) {
        return new DefaultDataObject(data);
    }
}
