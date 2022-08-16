package io.devpl.commons.db.jdbc;

import javax.sql.DataSource;

public class ThreadLocalDataSource {

	final ThreadLocal<DataSource> dataSource = new ThreadLocal<>();
}
