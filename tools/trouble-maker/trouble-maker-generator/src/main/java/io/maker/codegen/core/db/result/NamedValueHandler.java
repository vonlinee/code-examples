package io.maker.codegen.core.db.result;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import io.maker.base.lang.NamedValue;

/**
 * 
 */
public class NamedValueHandler implements ResultSetHandler<List<NamedValue>> {

	@Override
	public List<NamedValue> handle(ResultSet rs) throws SQLException {
		return null;
	}
}
