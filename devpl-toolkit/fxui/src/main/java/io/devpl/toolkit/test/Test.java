package io.devpl.toolkit.test;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import io.devpl.codegen.mbpg.jdbc.meta.ColumnMetadata;
import io.devpl.sdk.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Test {

    public static final String testSqlFile = "C:\\Users\\vonline\\Desktop\\test.sql";

    public static void main(String[] args) throws IOException {
        String sql = FileUtils.readFileToString(new File(testSqlFile));
        final SQLStatementParser parser = SQLParserUtils.createSQLStatementParser(sql, DbType.mysql, true);
        final List<SQLStatement> sqlStatements = parser.parseStatementList();

        final SQLStatement sqlStatement = sqlStatements.get(0);
        if (sqlStatement instanceof SQLSelectStatement) {
            List<String> selectedColumns = getSelectedColumns((SQLSelectStatement) sqlStatement);
        }
    }

    public static ColumnMetadata getColumnMetadata(Connection conn, String tableName, String columnName) throws SQLException {
        final DatabaseMetaData dbmd = conn.getMetaData();
        return null;
    }

    /**
     * 获取SQL中查询的所有列
     * @param sqlStatement SQLStatement
     * @return 查询的所有列
     */
    public static List<String> getSelectedColumns(SQLSelectStatement sqlStatement) {
        List<String> selectedColumnNames = new ArrayList<>();
        SQLSelect select = sqlStatement.getSelect();
        SQLSelectQuery query = select.getQuery();
        if (query instanceof SQLSelectQueryBlock) {
            SQLSelectQueryBlock queryBlock = (SQLSelectQueryBlock) query;
            List<SQLSelectItem> selectList = queryBlock.getSelectList();
            SQLTableSource from = queryBlock.getFrom();
            // 找到所有的别名和表明映射
            Map<String, String> tableNames = findAllTalbeNames(from);
            for (SQLSelectItem sqlSelectItem : selectList) {
                final SQLExpr expr = sqlSelectItem.getExpr();
                final String selectColumn = expr.toString();
                String[] tableColumnNames = selectColumn.split("\\.");
                if (tableColumnNames.length == 2) {
                    String tableAlias = tableColumnNames[0];
                    String tableName = tableNames.get(tableAlias);
                    selectedColumnNames.add(tableName + "." + tableColumnNames[1]);
                }
            }
        }
        return selectedColumnNames;
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
                // 没有别名
                if (alias == null) {
                    alias = currTableName;
                }

                tableAliasMapping.put(alias, currTableName);
            }
        } else if (from instanceof SQLExprTableSource) {
            // 单表
            SQLExprTableSource exprTableSource = (SQLExprTableSource) from;
            final String alias = exprTableSource.getAlias();
            final String tableName = exprTableSource.getTableName();
            tableAliasMapping.put(String.valueOf(alias), tableName);
        }
        return tableAliasMapping;
    }
}
