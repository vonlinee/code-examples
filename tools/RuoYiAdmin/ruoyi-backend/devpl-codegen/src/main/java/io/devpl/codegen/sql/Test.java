package io.devpl.codegen.sql;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import io.devpl.sdk.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Test {

    public static final String testSqlFile = "C:\\Users\\Von\\Desktop\\test.sql";

    public static void main(String[] args) throws IOException {

        String sql = FileUtils.readFileToString(new File(testSqlFile));
        final SQLStatementParser parser = SQLParserUtils.createSQLStatementParser(sql, DbType.mysql, true);

        final List<SQLStatement> sqlStatements = parser.parseStatementList();
        for (SQLStatement sqlStatement : sqlStatements) {
            if (sqlStatement instanceof SQLSelectStatement) {
                SQLSelectStatement selectStmt = (SQLSelectStatement) sqlStatement;

                SQLSelect select = selectStmt.getSelect();
                SQLSelectQuery query = select.getQuery();

                if (query instanceof SQLSelectQueryBlock) {
                    SQLSelectQueryBlock queryBlock = (SQLSelectQueryBlock) query;
                    List<SQLSelectItem> selectList = queryBlock.getSelectList();
                    SQLTableSource from = queryBlock.getFrom();
                    Map<String, String> tableNames = findAllTalbeNames(from);
                }
            }
        }
    }

    private static Map<String, String> findAllTalbeNames(SQLTableSource from) {
        Map<String, String> tableAliasMapping = new LinkedHashMap<>();
        // 存在表连接
        if (from instanceof SQLJoinTableSource) {
            SQLTableSource tmp = from;
            for (; ; ) {
                if (!(tmp instanceof SQLJoinTableSource)) {
                    if (tmp instanceof SQLExprTableSource) {
                        SQLExprTableSource tableSource = (SQLExprTableSource) tmp;
                        final String alias = tableSource.getAlias();
                        tableAliasMapping.put(alias, tableSource.getTableName());
                    }
                    break;
                }
                // 当前表：倒数第二个表
                SQLJoinTableSource currentTableSource = (SQLJoinTableSource) tmp;
                tmp = currentTableSource.getLeft();
                // 获取右连接表
                SQLTableSource right = currentTableSource.getRight();

                String currTableName = null;
                if (right instanceof SQLExprTableSource) {
                    SQLExprTableSource sqlExprTableSource = (SQLExprTableSource) right;
                    currTableName = sqlExprTableSource.getTableName();
                }
                String alias = right.getAlias();
                if (alias == null) {
                    throw new RuntimeException("别名为空");
                }
                tableAliasMapping.put(alias, currTableName);
            }
        } else if (from instanceof SQLExprTableSource) {
            // 单表
        }
        return tableAliasMapping;
    }
}
