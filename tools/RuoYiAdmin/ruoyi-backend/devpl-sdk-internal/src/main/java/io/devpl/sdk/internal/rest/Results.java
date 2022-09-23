package io.devpl.sdk.internal.rest;

import java.util.List;
import java.util.Map;

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

    public static <T> ListResultBuilder<List<T>> listBuilder() {
        return new ListResult<T>();
    }

    public static EntityListResult ofEntities() {
        return null;
    }

    public static void main(String[] args) {

    }
}
