package code.fxutils.core.db.pool;

import code.fxutils.core.db.pool.DatabaseConnectionPool;

import javax.sql.DataSource;

public class Dbcp2Pool extends DatabaseConnectionPool {
    @Override
    public String vendor() {
        return "";
    }

    @Override
    public String website() {
        return "";
    }

    @Override
    public DataSource pickDataSource() {
        return null;
    }
}
