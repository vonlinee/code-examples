package io.maker.gen.db.meta.column;

/**
 * Column meta data.
 *
 * java.sql.DatabaseMetaData.getColumns(String, String, String, String)
 */
public class ColumnMetaData {

/*
TABLE_CAT          String , table catalog (may be null)
TABLE_SCHEM        String , table schema (may be null)
TABLE_NAME         String , table name
COLUMN_NAME        String , column name
DATA_TYPE          int , SQL type from java.sql.Types
TYPE_NAME          String , Data source dependent type name, for a UDT the type name is fully qualified
COLUMN_SIZE        int , column size.
BUFFER_LENGTH      is not used.
DECIMAL_DIGITS     int , the number of fractional digits. Null is returned for data types where DECIMAL_DIGITS is not applicable.
NUM_PREC_RADIX         int , Radix (typically either 10 or 2)
NULLABLE         int , is NULL allowed.
	DatabaseMetaData 三个常量
	columnNoNulls (0) - might not allow NULL values
	columnNullable (1) - definitely allows NULL values
	columnNullableUnknown (2) - nullability unknown
REMARKS         String , comment describing column (may be null)
COLUMN_DEF      String , default value for the column, which should be interpreted as a string when the value is enclosed in single quotes (may be null)
SQL_DATA_TYPE         int , unused
SQL_DATETIME_SUB         int , unused
CHAR_OCTET_LENGTH         int , for char types the
    maximum number of bytes in the column
ORDINAL_POSITION         int , index of column in table
   (starting at 1)
IS_NULLABLE         String  , ISO rules are used to determine the nullability for a column.
	YES           --- if the column can include NULLs
	NO            --- if the column cannot include NULLs
	empty string  --- if the nullability for the column is unknown
SCOPE_CATALOG         String , catalog of table that is the scope
   of a reference attribute (null if DATA_TYPE isn't REF)
SCOPE_SCHEMA         String , schema of table that is the scope
   of a reference attribute (null if the DATA_TYPE isn't REF)
SCOPE_TABLE         String , table name that this the scope
   of a reference attribute (null if the DATA_TYPE isn't REF)
SOURCE_DATA_TYPE         short , source type of a distinct type or user-generated
   Ref type, SQL type from java.sql.Types (null if DATA_TYPE
   isn't DISTINCT or user-generated REF)
IS_AUTOINCREMENT         String  , Indicates whether this column is auto incremented
	YES           --- if the column is auto incremented
	NO            --- if the column is not auto incremented
	empty string  --- if it cannot be determined whether the column is auto incremented
IS_GENERATEDCOLUMN         String  , Indicates whether this is a generated column
	YES           --- if this a generated column
	NO            --- if this not a generated column
	empty string  --- if it cannot be determined whether this is a generated column

the COLUMN_SIZE column specifies the column size for the given column.
For numeric data, this is the maximum precision.  For character data, this is the length in characters.
For datetime datatypes, this is the length in characters of the String representation (assuming the
maximum allowed precision of the fractional seconds component). For binary data, this is the length in bytes.  For the ROWID datatype,
this is the length in bytes. Null is returned for data types where the
column size is not applicable.
*/

	private String name;
	private int dataType;
	private String dataTypeName;
	private boolean primaryKey;
	private boolean generated;
	private boolean caseSensitive;
	private boolean autoIncrement = false;
	private boolean searchable;
	private boolean currency;
	private int nullable = 0;
	private boolean signed;
	private int columnDisplaySize;
	private String columnLabel;
	private String columnName;
	private String schemaName;
	private int precision;
	private int scale;
	private String tableName;
	private String catalogName;
	private int columnType;
	private String columnTypeName;
	private boolean readOnly;
	private boolean writable;
	private boolean definitelyWritable;
	private String columnClassName;

	public void setName(String name) {
		this.name = name;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public void setDataTypeName(String dataTypeName) {
		this.dataTypeName = dataTypeName;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public void setGenerated(boolean generated) {
		this.generated = generated;
	}

	public String getName() {
		return name;
	}

	public int getDataType() {
		return dataType;
	}

	public String getDataTypeName() {
		return dataTypeName;
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public boolean isGenerated() {
		return generated;
	}

	@Override
	public String toString() {
		return "ColumnMetaData [name=" + name + ", dataType=" + dataType + ", dataTypeName=" + dataTypeName
				+ ", primaryKey=" + primaryKey + ", generated=" + generated + ", caseSensitive=" + caseSensitive + "]";
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	public boolean isAutoIncrement() {
		return autoIncrement;
	}

	public void setAutoIncrement(boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
	}

	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	public boolean isSearchable() {
		return searchable;
	}

	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}

	public boolean isCurrency() {
		return currency;
	}

	public void setCurrency(boolean currency) {
		this.currency = currency;
	}

	public int getNullable() {
		return nullable;
	}

	public void setNullable(int nullable) {
		this.nullable = nullable;
	}

	public boolean isSigned() {
		return signed;
	}

	public void setSigned(boolean signed) {
		this.signed = signed;
	}

	public int getColumnDisplaySize() {
		return columnDisplaySize;
	}

	public void setColumnDisplaySize(int columnDisplaySize) {
		this.columnDisplaySize = columnDisplaySize;
	}

	public String getColumnLabel() {
		return columnLabel;
	}

	public void setColumnLabel(String columnLabel) {
		this.columnLabel = columnLabel;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public int getColumnType() {
		return columnType;
	}

	public void setColumnType(int columnType) {
		this.columnType = columnType;
	}

	public String getColumnTypeName() {
		return columnTypeName;
	}

	public void setColumnTypeName(String columnTypeName) {
		this.columnTypeName = columnTypeName;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public boolean isWritable() {
		return writable;
	}

	public void setWritable(boolean writable) {
		this.writable = writable;
	}

	public boolean isDefinitelyWritable() {
		return definitelyWritable;
	}

	public void setDefinitelyWritable(boolean definitelyWritable) {
		this.definitelyWritable = definitelyWritable;
	}

	public String getColumnClassName() {
		return columnClassName;
	}

	public void setColumnClassName(String columnClassName) {
		this.columnClassName = columnClassName;
	}
}
