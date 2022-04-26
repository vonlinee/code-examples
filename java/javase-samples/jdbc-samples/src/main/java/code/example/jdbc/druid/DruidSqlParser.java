package code.example.jdbc.druid;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.sql.parser.SQLStatementParser;

/**
 * https://www.cnblogs.com/bigstrong/p/15190473.html
 *
 * 访问者模式
 */
public class DruidSqlParser {

    public static void main(String[] args) {
        // 待解析 SQL
        String selectSql = "select * from order o left join order_item oi on o.order_num = oi.order_num";
        // 新建 Parser
        SQLStatementParser parser = new SQLStatementParser(selectSql, DbType.mysql);
        // 使用 Parser 解析 SQL，生成 AST
        SQLStatement sqlStatement = parser.parseStatement();
        // 生成访问者
        MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
        // 使用 Visitor 访问 AST
        sqlStatement.accept(visitor);
        // 获取解析信息
        System.out.println(visitor.getColumns());
    }
}
