package sample.dynamic.datasource.config;

public class DataSourceDecision {

    // 当前使用的数据源标识
    private static final ThreadLocal<String> name = new ThreadLocal<>();
    
    public static void decide(String databaseId) {
    	name.set(databaseId);
    }
    
    public static void reset() {
    	name.remove();
    }
    
    public static String getDatabaseId() {
    	return name.get();
    }
}
