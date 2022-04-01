package multidatasource.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 持有数据源的上下文，保存当前线程实际的数据源
 * 是做成static还是non-static？
 */
public class DynamicDataSourceContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();
    public static List<String> dataSourceIds = new ArrayList<>();

    public static void setDataSourceType(String dataSourceType) {
        contextHolder.set(dataSourceType);
    }

    public static String getDataSourceType() {
        return contextHolder.get();
    }

    public static void clearDataSourceType() {
        contextHolder.remove();
    }

    /**
     * 判断指定DataSrouce当前是否存在
     */
    public static boolean containsDataSource(String dataSourceId) {
        return dataSourceIds.contains(dataSourceId);
    }

    public static boolean addDataSource(String dataSourceId) {
        return dataSourceIds.add(dataSourceId);
    }
}
