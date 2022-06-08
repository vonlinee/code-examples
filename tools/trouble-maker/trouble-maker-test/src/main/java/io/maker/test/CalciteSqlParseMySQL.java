package io.maker.test;

import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlNodeList;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.util.SqlBasicVisitor;
import org.apache.calcite.sql.util.SqlVisitor;
import org.apache.calcite.tools.FrameworkConfig;
import org.apache.calcite.tools.Frameworks;

public class CalciteSqlParseMySQL {

	public static void main(String[] args) {
		
		SchemaPlus rootSchema = Frameworks.createRootSchema(true);

		final FrameworkConfig config = Frameworks.newConfigBuilder()
				.parserConfig(SqlParser.config()) // 默认配置
				.build();

		String sql = "select ids, name from test where id < 5 and name = 'zhang'";
		SqlParser parser = SqlParser.create(sql, SqlParser.config());
		
		SqlVisitor<String> visitor = new SqlBasicVisitor<>();
		
		try {
			// SqlNode sqlNode = parser.parseStmt();
			SqlNodeList sqlNodes = parser.parseStmtList();
			String value = sqlNodes.accept(visitor);
			System.out.println(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
