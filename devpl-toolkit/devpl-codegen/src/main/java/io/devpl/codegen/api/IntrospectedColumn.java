package io.devpl.codegen.api;

import io.devpl.codegen.jdbc.meta.ColumnMetadata;
import io.devpl.codegen.utils.StringUtils;

import java.sql.Types;
import java.util.Properties;

/**
 * This class holds information about an introspected column.
 */
public class IntrospectedColumn {

    private boolean isPrimaryKey;

    /**
     * 主键是否为自增类型
     */
    private boolean autoIncrement;

    private String name;
    private String type;

    private Context context;

    /**
     * 数据库字段（关键字含转义符号）
     * @since 3.3.2
     */
    private String columnName;

    /**
     * 字段元数据信息
     * @since 3.5.0
     */
    private IntrospectedColumn columnInfo;

    /**
     * 构造方法
     * @param context  配置构建
     * @param metadata 数据库字段元数据
     * @since 3.5.0
     */
    public TableColumn(Context context, ColumnMetadata metadata) {
        this.context = context;
        this.columnInfo = new IntrospectedColumn(metadata);
    }

    /**
     * 是否为乐观锁字段
     * @return 是否为乐观锁字段
     * @see com.baomidou.mybatisplus.annotation.Version
     * @since 3.5.0
     */
    public boolean isVersionField() {
        return true;
    }

    /**
     * 是否为逻辑删除字段
     * @return 是否为逻辑删除字段
     * @see com.baomidou.mybatisplus.annotation.TableLogic
     * @since 3.5.0
     */
    public boolean isLogicDeleteField() {
        return true;
    }

    /**
     * @param type 类型
     * @return this
     */
    public void setType(String type) {
        this.type = type;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getColumnName() {
        return columnName;
    }

    protected String actualColumnName;

    protected int jdbcType;

    /**
     * The platform specific data type name as reported from DatabaseMetadata.getColumns()
     */
    protected String actualTypeName;

    protected String jdbcTypeName;

    protected boolean nullable;

    protected int length;

    protected int scale;

    protected boolean identity;

    protected boolean isSequenceColumn;

    protected String javaProperty;

    protected String tableAlias;

    protected String typeHandler;

    protected boolean isColumnNameDelimited;

    protected IntrospectedTable introspectedTable;

    protected final Properties properties;

    protected ColumnMetadata metadata;

    // any database comment associated with this column. May be null
    protected String remarks;

    protected String defaultValue;

    /**
     * true if the JDBC driver reports that this column is auto-increment.
     */
    protected boolean isAutoIncrement;

    /**
     * true if the JDBC driver reports that this column is generated.
     */
    protected boolean isGeneratedColumn;

    /**
     * True if there is a column override that defines this column as GENERATED ALWAYS.
     */
    protected boolean isGeneratedAlways;

    /**
     * Constructs a Column definition. This object holds all the information
     * about a column that is required to generate Java objects and SQL maps;
     */
    public IntrospectedColumn() {
        super();
        properties = new Properties();
    }

    public IntrospectedColumn(ColumnMetadata metadata) {
        this.metadata = metadata;
        properties = new Properties();
    }

    public int getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(int jdbcType) {
        this.jdbcType = jdbcType;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    /*
     * This method is primarily used for debugging, so we don't externalize the
     * strings
     */
    @Override
    public String toString() {
        return "Actual Column Name: " //$NON-NLS-1$
                + actualColumnName
                + ", JDBC Type: " //$NON-NLS-1$
                + jdbcType
                + ", Nullable: " //$NON-NLS-1$
                + nullable
                + ", Length: " //$NON-NLS-1$
                + length
                + ", Scale: " //$NON-NLS-1$
                + scale
                + ", Identity: " //$NON-NLS-1$
                + identity;
    }

    public void setActualColumnName(String actualColumnName) {
        this.actualColumnName = actualColumnName;
        isColumnNameDelimited = StringUtils.hasText(actualColumnName);
    }

    public boolean isIdentity() {
        return identity;
    }

    public void setIdentity(boolean identity) {
        this.identity = identity;
    }

    /**
     * @return if the column is blob
     */
    public boolean isBLOBColumn() {
        String typeName = getJdbcTypeName();
        return "BINARY".equals(typeName) || "BLOB".equals(typeName) //$NON-NLS-1$ //$NON-NLS-2$
                || "CLOB".equals(typeName) || "LONGNVARCHAR".equals(typeName) //$NON-NLS-1$ //$NON-NLS-2$
                || "LONGVARBINARY".equals(typeName) || "LONGVARCHAR".equals(typeName) //$NON-NLS-1$ //$NON-NLS-2$
                || "NCLOB".equals(typeName) || "VARBINARY".equals(typeName); //$NON-NLS-1$ //$NON-NLS-2$
    }

    public boolean isJdbcCharacterColumn() {
        return jdbcType == Types.CHAR || jdbcType == Types.CLOB
                || jdbcType == Types.LONGVARCHAR || jdbcType == Types.VARCHAR
                || jdbcType == Types.LONGNVARCHAR || jdbcType == Types.NCHAR
                || jdbcType == Types.NCLOB || jdbcType == Types.NVARCHAR;
    }

    public String getJavaProperty() {
        return getJavaProperty(null);
    }

    public String getJavaProperty(String prefix) {
        if (prefix == null) {
            return javaProperty;
        }
        return prefix + javaProperty;
    }

    public void setJavaProperty(String javaProperty) {
        this.javaProperty = javaProperty;
    }

    public boolean isJDBCDateColumn() {
        return "DATE".equalsIgnoreCase(jdbcTypeName); //$NON-NLS-1$
    }

    public boolean isJDBCTimeColumn() {
        return "TIME".equalsIgnoreCase(jdbcTypeName); //$NON-NLS-1$
    }

    public String getTypeHandler() {
        return typeHandler;
    }

    public void setTypeHandler(String typeHandler) {
        this.typeHandler = typeHandler;
    }

    public String getActualColumnName() {
        return actualColumnName;
    }

    public void setColumnNameDelimited(boolean isColumnNameDelimited) {
        this.isColumnNameDelimited = isColumnNameDelimited;
    }

    public boolean isColumnNameDelimited() {
        return isColumnNameDelimited;
    }

    public String getJdbcTypeName() {
        if (jdbcTypeName == null) {
            return "OTHER"; //$NON-NLS-1$
        }

        return jdbcTypeName;
    }

    public void setJdbcTypeName(String jdbcTypeName) {
        this.jdbcTypeName = jdbcTypeName;
    }

    public String getTableAlias() {
        return tableAlias;
    }

    public void setTableAlias(String tableAlias) {
        this.tableAlias = tableAlias;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public IntrospectedTable getIntrospectedTable() {
        return introspectedTable;
    }

    public void setIntrospectedTable(IntrospectedTable introspectedTable) {
        this.introspectedTable = introspectedTable;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties.putAll(properties);
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isSequenceColumn() {
        return isSequenceColumn;
    }

    public void setSequenceColumn(boolean isSequenceColumn) {
        this.isSequenceColumn = isSequenceColumn;
    }

    public boolean isAutoIncrement() {
        return isAutoIncrement;
    }

    public void setAutoIncrement(boolean isAutoIncrement) {
        this.isAutoIncrement = isAutoIncrement;
    }

    public boolean isGeneratedColumn() {
        return isGeneratedColumn;
    }

    public void setGeneratedColumn(boolean isGeneratedColumn) {
        this.isGeneratedColumn = isGeneratedColumn;
    }

    public boolean isGeneratedAlways() {
        return isGeneratedAlways;
    }

    public void setGeneratedAlways(boolean isGeneratedAlways) {
        this.isGeneratedAlways = isGeneratedAlways;
    }

    /**
     * The platform specific type name as reported by the JDBC driver. This value is determined
     * from the DatabaseMetadata.getColumns() call - specifically ResultSet.getString("TYPE_NAME").
     * This value is platform dependent.
     * @return the platform specific type name as reported by the JDBC driver
     */
    public String getActualTypeName() {
        return actualTypeName;
    }

    public void setActualTypeName(String actualTypeName) {
        this.actualTypeName = actualTypeName;
    }
}
