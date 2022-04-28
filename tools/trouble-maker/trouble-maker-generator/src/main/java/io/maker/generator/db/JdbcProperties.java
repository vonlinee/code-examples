package io.maker.generator.db;

import java.util.HashMap;
import java.util.Map;

public class JdbcProperties {

    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 3306;
	
    private String host = DEFAULT_HOST;
    private int port = DEFAULT_PORT;
	private DbType dbType;
	
    public static final Map<String, String> DEFAULT_CONN_OPTIONS = new HashMap<>();
    
    private interface ConnectionOption {
    	String USE_SSL = "useSSL";
    	String CREATE_DATABASE_IFNOT_EXISTS = "useSSL";
    	String CREATE_DATABASE_IF_NOT_EXISTS = "";
    	String USE_UNICODETRUE = "";
    	String CHARACTER_ENCODING = "";
    	String SERVER_TIMEZONE = "";
    }
    
    static {
    	DEFAULT_CONN_OPTIONS.put("useSSL", "false");
    	DEFAULT_CONN_OPTIONS.put("createDatabaseIfNotExists", "true"); //是否创建数据库
    	DEFAULT_CONN_OPTIONS.put("useUnicode", "true"); 
    	DEFAULT_CONN_OPTIONS.put("characterEncoding", "utf8");
    	DEFAULT_CONN_OPTIONS.put("serverTimezone", "GMT%2B8");
    }
    
}
