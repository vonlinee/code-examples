package io.doraemon.pocket.generator.sql;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcUtil {
    
	private static final Logger LOG = LoggerFactory.getLogger(JdbcUtil.class);
	
    /**
     * Get schema.
     * @param connection connection
     * @param databaseType database type
     * @return schema
     */
    public static String getSchema(final Connection connection, final String databaseType) {
        String result = null;
        try {
            if ("Oracle".equals(databaseType)) {
                return null;
            }
            result = connection.getSchema();
        } catch (final SQLException ignore) {
        }
        return result;
    }
    
    public static void registerDriver(String driverClassName) {
    	try {
			Class.forName(driverClassName);
		} catch (Exception e) {
			LOG.error("register driver failed");
		}
    }
    
    public static Connection getConnection(final String connection, final String databaseType) {
    	return null;
    }
}