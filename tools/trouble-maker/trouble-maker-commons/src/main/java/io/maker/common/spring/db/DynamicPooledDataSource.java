package io.maker.common.spring.db;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * 动态的数据库连接池
 */
public class DynamicPooledDataSource implements DataSource {

    private final DataSource delagate;

    public DynamicPooledDataSource(DataSource dataSource) {
        this.delagate = dataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return delagate.getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return delagate.getConnection(username, password);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return delagate.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return delagate.isWrapperFor(iface);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return delagate.getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        delagate.setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        delagate.setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return delagate.getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return delagate.getParentLogger();
    }
}
