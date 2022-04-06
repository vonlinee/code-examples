package sample.dynamic.datasource.common;

import java.util.function.Supplier;

/**
 * 数据源决策
 */
public final class DataSourceDecision {

    private static final ThreadLocal<String> dataSource = ThreadLocal.withInitial(() -> "main");

    public static <T> T invoke(Supplier<T> supplier) {
        if (dataSource.get() != null) {
            System.out.println(dataSource.get());
        }
        T t = supplier.get();
        System.out.println(t);
        return t;
    }

    public static void reset() {
        dataSource.remove();
    }
}
