package io.devpl.sdk.internal.restful;

import java.util.Map;

/**
 * 创建各种Result的工具类
 */
public final class Results {

    private Results() {
    }

    public static <T> ResultBuilder<T> builder() {
        return new Result<>();
    }

    public static <K, V> ResultBuilder<?> mapBuilder() {
        return Results.<Map<K, V>>builder();
    }

    public static ResultBuilder<?> entityMapBuilder() {
        return Results.<Map<String, Object>>builder();
    }

    public static <T> ListResultBuilder<T> listBuilder() {
        return new ListResult<>();
    }

    public static EntityResult entity() {
        return new EntityResult();
    }

    public static EntityListResult entityList() {
        return new EntityListResult();
    }

    public static <K, V> MapListResult<K, V> mapList() {
        return new MapListResult<>();
    }
}
