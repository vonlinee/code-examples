package io.maker.generator.db.pool;

import javax.sql.DataSource;

public abstract class DatabaseConnectionPool {

    public abstract String vendor();

    public abstract String website();

    public abstract DataSource pickDataSource();

}
