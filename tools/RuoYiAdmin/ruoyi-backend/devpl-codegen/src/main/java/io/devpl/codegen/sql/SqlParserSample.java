package io.devpl.codegen.sql;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLDataType;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLCharacterDataType;
import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import com.alibaba.druid.sql.ast.statement.SQLTableElement;
import com.alibaba.druid.sql.dialect.mysql.ast.MySqlKey;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlCreateTableStatement;
import com.alibaba.druid.util.JdbcConstants;
import org.apache.calcite.config.Lex;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.parser.ddl.SqlDdlParserImpl;
import org.apache.calcite.sql.validate.SqlConformanceEnum;
import org.junit.Test;

import java.util.List;

public class SqlParserSample {

    static final String sql1 = "CREATE TABLE `dataview_item` (\n" + "\t`item_id`  NOT NULL AUTO_INCREMENT COMMENT '数据项ID',\n" + "\t`dataview_id` TINYINT(4) DEFAULT NULL COMMENT '数据视图ID',\n" + "\t`dataview_name` TINYINT(4) DEFAULT NULL COMMENT '数据视图名称',\n" + "\t`block_title` VARCHAR(50) NOT NULL COMMENT '数据块标题',\n" + "\t`display_title` VARCHAR(50) NOT NULL COMMENT '数据项展示名称',\n" + "\t`display_value` VARCHAR(50) NOT NULL DEFAULT '0' COMMENT '数据项的值',\n" + "\t`value_type` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '数据项的值类型:整数|小数|字符',\n" + "\t`display_unit_name` VARCHAR(50) NOT NULL COMMENT '数据项展示单位名称',\n" + "\t`source_db_name` VARCHAR(36) NOT NULL COMMENT '数据项来源表名称',\n" + "\t`source_column_name` VARCHAR(50) NOT NULL COMMENT '数据项来源表字段名称',\n" + "\t`source_row_id` VARCHAR(50) NOT NULL COMMENT '数据项来源行ID',\n" + "\t`editable` TINYINT(1) NOT NULL COMMENT '是否可编辑',\n" + "\tPRIMARY KEY (`item_id`) USING BTREE\n" + ") ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '动态数据视图数据项记录表'";

    public static void main(String[] args) throws SqlParseException {
        // Sql语句

        SqlParser.Config sqlParserConfig = SqlParser.config()
                                                    .withParserFactory(SqlDdlParserImpl.FACTORY)
                                                    .withConformance(SqlConformanceEnum.MYSQL_5)
                                                    .withLex(Lex.MYSQL);
        // 创建解析器
        SqlParser parser = SqlParser.create(sql1, sqlParserConfig);
        parser.parseStmt();
    }

    @Test
    public void testDruid() {
        List<SQLStatement> statementList = SQLUtils.parseStatements(sql1, JdbcConstants.MYSQL);
        for (SQLStatement sqlStatement : statementList) {
            MySqlCreateTableStatement ddlCreate = (MySqlCreateTableStatement) sqlStatement;
            final List<SQLColumnDefinition> columnDefinitions = ddlCreate.getColumnDefinitions();

            for (SQLColumnDefinition columnDefinition : columnDefinitions) {
                final SQLDataType dataType = columnDefinition.getDataType();
                if (dataType != null) {
                    System.out.println(dataType.getArguments());
                }
            }

            SQLColumnDefinition newColDef = new SQLColumnDefinition();
            newColDef.setName(SqlUtils.wrapWithBackquote("create_time"));
            newColDef.setComment("创建时间");
            final SQLCharacterDataType dataType = new SQLCharacterDataType("VARCHAR");

            newColDef.setDataType(dataType);

            final List<MySqlKey> mysqlKeys = ddlCreate.getMysqlKeys();

            final List<SQLTableElement> tableElementList = ddlCreate.getTableElementList();
            final int elmentCount = tableElementList.size();
            tableElementList.set(elmentCount - 2, newColDef);
            System.out.println(ddlCreate);
        }
    }
}
