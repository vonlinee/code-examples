package io.devpl.toolkit.sqlparser.druid;

import com.alibaba.druid.sql.ast.SQLDataType;
import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import lombok.Data;

/**
 * 通用的列定义信息
 * @see SQLColumnDefinition
 */
@Data
public class ColumnDefinitionWrapper {
    private String columnName;
    private SqlDataTypeEnum sqlType;
    private Boolean hasDefaultExpression;
    private String defaultExpression;

    final SQLColumnDefinition columnDefinition;

    public ColumnDefinitionWrapper(SQLColumnDefinition columnDefinition) {
        this.columnDefinition = columnDefinition;
        this.columnName = columnDefinition.getColumnName();
    }

    public boolean hasDefaultExpression() {
        if (hasDefaultExpression == null) {
            hasDefaultExpression = columnDefinition.getDefaultExpr() != null;
        }
        return hasDefaultExpression;
    }

    public String getDefaultExpressionAsString() {
        if (columnDefinition.getDefaultExpr() != null) {
            this.defaultExpression = columnDefinition.getDefaultExpr().toString();
        }
        return defaultExpression == null ? "" : defaultExpression;
    }

    public SqlDataTypeEnum getSqlType() {
        if (sqlType == null) {
            final SQLDataType dataType = columnDefinition.getDataType();
            if (dataType == null) return SqlDataTypeEnum.NULL;
            sqlType = SqlDataTypeEnum.valueOf(dataType.jdbcType());
        }
        return sqlType;
    }
}
