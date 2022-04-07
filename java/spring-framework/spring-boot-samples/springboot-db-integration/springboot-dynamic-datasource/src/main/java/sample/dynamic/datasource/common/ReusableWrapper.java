package sample.dynamic.datasource.common;

import java.sql.SQLException;
import java.sql.Wrapper;

public class ReusableWrapper implements Wrapper {

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        System.out.println(iface);
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
