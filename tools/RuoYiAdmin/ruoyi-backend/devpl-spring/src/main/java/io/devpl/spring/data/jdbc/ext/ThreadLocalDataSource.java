package io.devpl.spring.data.jdbc.ext;

import javax.sql.DataSource;

public class ThreadLocalDataSource {

	final ThreadLocal<DataSource> dataSource = new ThreadLocal<>();
}
