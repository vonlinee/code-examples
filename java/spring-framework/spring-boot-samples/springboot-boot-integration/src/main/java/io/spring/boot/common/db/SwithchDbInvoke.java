package io.spring.boot.common.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 线程绑定的数据源
 */
public final class SwithchDbInvoke {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();
    public static List<String> dataSourceIds = new ArrayList<>();

    public static void setDataSourceType(String dataSourceType) {
        contextHolder.set(dataSourceType);
    }

    public static void addOptionalDataSourceId(String... dataSourceId) {
        dataSourceIds.addAll(Arrays.asList(dataSourceId));
    }

    public static void addOptionalDataSourceId(Collection<String> dataSourceId) {
        dataSourceIds.addAll(dataSourceId);
    }

    public static String getDataSourceType() {
        return contextHolder.get();
    }

    public static void clearDataSourceType() {
        contextHolder.remove();
    }

    /**
     * 判断指定DataSrouce当前是否存在
     * @param dataSourceId
     */
    public static boolean containsDataSource(String dataSourceId) {
        return dataSourceIds.contains(dataSourceId);
    }
}
