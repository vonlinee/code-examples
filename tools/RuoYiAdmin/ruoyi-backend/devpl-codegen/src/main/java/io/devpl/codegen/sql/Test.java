package io.devpl.codegen.sql;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLDropTableStatement;

import java.util.List;

public class Test {

    static final String sql1 = "CREATE TABLE `dataview_item` (\n" +
            "\t`item_id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '数据项ID',\n" +
            "\t`dataview_id` TINYINT(4) DEFAULT NULL COMMENT '数据视图ID',\n" +
            "\t`dataview_name` TINYINT(4) DEFAULT NULL COMMENT '数据视图名称',\n" +
            "\t`block_title` VARCHAR(50) NOT NULL COMMENT '数据块标题',\n" +
            "\t`display_title` VARCHAR(50) NOT NULL COMMENT '数据项展示名称',\n" +
            "\t`display_value` VARCHAR(50) NOT NULL DEFAULT '0' COMMENT '数据项的值',\n" +
            "\t`value_type` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '数据项的值类型:整数|小数|字符',\n" +
            "\t`display_unit_name` VARCHAR(50) NOT NULL COMMENT '数据项展示单位名称',\n" +
            "\t`source_db_name` VARCHAR(36) NOT NULL COMMENT '数据项来源表名称',\n" +
            "\t`source_column_name` VARCHAR(50) NOT NULL COMMENT '数据项来源表字段名称',\n" +
            "\t`source_row_id` VARCHAR(50) NOT NULL COMMENT '数据项来源行ID',\n" +
            "\t`editable` TINYINT(1) NOT NULL COMMENT '是否可编辑',\n" +
            "\tPRIMARY KEY (`item_id`) USING BTREE\n" +
            ") ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '动态数据视图数据项记录表'";

    public static void main(String[] args) {
        DruidSqlParser parser = new DruidSqlParser(sql1, DbType.mysql);
        parser.parseCreate();
    }
}
