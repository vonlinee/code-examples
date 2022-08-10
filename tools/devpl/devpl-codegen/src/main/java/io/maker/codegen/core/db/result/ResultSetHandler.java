package io.maker.codegen.core.db.result;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultSetHandler<T> {
	T handle(ResultSet rs) throws SQLException;
	
	MapListHandler MAP_LIST = new MapListHandler();
}
