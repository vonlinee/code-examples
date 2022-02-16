package code.magicode.generator.db.pool;

import javax.sql.DataSource;

import code.fxutils.support.db.pool.DatabaseConnectionPool;

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
