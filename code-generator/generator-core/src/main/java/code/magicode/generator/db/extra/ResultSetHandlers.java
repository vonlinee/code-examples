package code.magicode.generator.db.extra;

import java.util.List;

public class ResultSetHandlers {
	public static final ResultSetHandler<List<List<NamedValue>>> NAMED_VALUES = new DefaultResultSetHandler();
	public static final ResultSetHandler<Object[]> ARRAY = new ArrayResultSetHandler();
}
