package io.maker.generator.db.sql;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;
import com.alibaba.druid.util.Utils;

import java.io.IOException;
import java.util.List;

/**
 * SQL Parser实现产品
 * https://xie.infoq.cn/article/4cd031b6a83372a8d6abb37cc
 */
public abstract class SqlParser {

    private String sql;

    protected void setUp() throws Exception {

    }

    private String execMySql(String sql) {
        StringBuilder out = new StringBuilder();
        MySqlOutputVisitor visitor = new MySqlOutputVisitor(out);
        MySqlStatementParser parser = new MySqlStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        for (SQLStatement statement : statementList) {
            statement.accept(visitor);
            visitor.println();
        }
        return out.toString();
    }
}
