package code.magicode.generator.lang;

import code.magicode.generator.db.extra.ResultSetHandlers;
import code.magicode.generator.db.meta.table.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.maker.base.lang.NamedValue;

import java.sql.*;
import java.util.*;

/**
 * <p>
 * Description: 获取数据库基本信息的工具类
 * </p>
 * DatabaseMetaData类: 包含驱动信息、数据库名、表名(视图、存储过程等) ResultSetMetaData类:
 * 包含表结构相关的信息，如:列(字段)的个数、名字、类型和属性等信息
 */
public class DatabaseMetadataHolder {

    private static final Logger LOG = LoggerFactory.getLogger(DatabaseMetadataHolder.class);

    private DatabaseMetaData databaseMetaData;
    // "TABLE", "VIEW", "SYSTEM TABLE", "GLOBAL TEMPORARY", "LOCAL TEMPORARY",
    // "ALIAS", "SYNONYM".
    private String[] availableTableType;
    private Object[] catalogs;
    private String procedureTerm;
    private boolean allProceduresAreCallable;
    private boolean allTablesAreSelectable;
    private boolean autoCommitFailureClosesAllResultSets;
    private boolean dataDefinitionCausesTransactionCommit;
    private boolean dataDefinitionIgnoredInTransactions;

    public DatabaseMetadataHolder(DatabaseMetaData databaseMetaData) {
        this.databaseMetaData = databaseMetaData;
        cacheDatabaseMetaData();
    }

    private void cacheDatabaseMetaData() {
        try {
            ResultSet tableTypes = databaseMetaData.getTableTypes(); // 结果只有一列:TABLE_TYPE
            ResultSetMetaData resultSetMetaData = tableTypes.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            this.availableTableType = new String[columnCount];
            while (tableTypes.next()) {
                for (int i = 0; i < columnCount; i++) {
                    availableTableType[i] = tableTypes.getString(i + 1);
                }
            }
            catalogs = (Object[]) ResultSetHandlers.ARRAY.handle(databaseMetaData.getCatalogs());
        } catch (SQLException e) {
            LOG.error("get database metadata table types failed", e);
        }
        try {
            procedureTerm = databaseMetaData.getProcedureTerm();
            allProceduresAreCallable = databaseMetaData.allProceduresAreCallable();
            allTablesAreSelectable = databaseMetaData.allTablesAreSelectable();
            autoCommitFailureClosesAllResultSets = databaseMetaData.autoCommitFailureClosesAllResultSets();
            dataDefinitionCausesTransactionCommit = databaseMetaData.dataDefinitionCausesTransactionCommit();
            dataDefinitionIgnoredInTransactions = databaseMetaData.dataDefinitionIgnoredInTransactions();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ResultSet resultSet = databaseMetaData.getClientInfoProperties();
            List<List<NamedValue>> namedValues = ResultSetHandlers.NAMED_VALUES.handle(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static final String ORACLE_DRIVER_CLASS_NAME = "oracle.jdbc.driver.OracleDriver";
    public static final String ORACLE_URL = "jdbc:oracle:thin:@192.168.12.44:1521:orcl";
    public static final String USERNAME = "bdc";
    public static final String PASSWORD = "bdc123";

    public static final String MYSQL_DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    public static final String MYSQL_USERNAME = "root";
    public static final String MYSQL_PASSWORD = "123456";
    public static final String MYSQL_URL = "jdbc:mysql://localhost/mysql_learn"
            + "?useUnicode=true&characterEncoding=UTF-8";

    /**
     * 根据数据库的连接参数，获取指定表的基本信息：字段名、字段类型、字段注释
     */
    public static List<Table> getTableInfo(String driver, String url, String user, String pwd, String table) {
        List<Table> result = new ArrayList<>();
        Connection conn = null;
        try {
            conn = getConnections(driver, url, user, pwd);
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet resultSet = dbmd.getTables(null, "%", table, new String[]{"TABLE"});
            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                if (tableName.equals(table)) {
                    ResultSet rs = conn.getMetaData().getColumns(null, getSchema(conn), tableName.toUpperCase(), "%");
                    while (rs.next()) {
                        final Map<String, String> map = new HashMap<>();
                        String colName = rs.getString("COLUMN_NAME");
                        map.put("code", colName);
                        String remarks = rs.getString("REMARKS");
                        if (remarks == null || remarks.equals("")) {
                            remarks = colName;
                        }
                        map.put("name", remarks);
                        String dbType = rs.getString("TYPE_NAME");
                        map.put("dbType", dbType);
                        map.put("valueType", changeDbType(dbType));
                        Table tab = new Table();
                        result.add(tab);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeQuitely(conn);
        }
        return result;
    }

    public static List<Table> getTableMetadata(ResultSet tableResultSet) {
        try {
            while (tableResultSet.next()) {
                tableResultSet.getString("TABLE_CAT");
                tableResultSet.getString("TABLE_SCHEM");
                tableResultSet.getString("TABLE_NAME");
                tableResultSet.getString("TABLE_TYPE");
                tableResultSet.getString("REMARKS");
                tableResultSet.getString("TYPE_CAT");
                tableResultSet.getString("TYPE_SCHEM");
                tableResultSet.getString("TYPE_NAME");
                tableResultSet.getString("TABLE_TYPE");
                tableResultSet.getString("SELF_REFERENCING_COL_NAME");
                tableResultSet.getString("REF_GENERATION");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void closeQuitely(AutoCloseable... closeable) {
        for (AutoCloseable c : closeable) {
            assert c != null;
            try {
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static String changeDbType(String dbType) {
        dbType = dbType.toUpperCase();
        switch (dbType) {
            case "VARCHAR":
            case "VARCHAR2":
            case "CHAR":
                return "1";
            case "NUMBER":
            case "DECIMAL":
                return "4";
            case "INT":
            case "SMALLINT":
            case "INTEGER":
                return "2";
            case "BIGINT":
                return "6";
            case "DATETIME":
            case "TIMESTAMP":
            case "DATE":
                return "7";
            default:
                return "1";
        }
    }

    private static Connection getConnections(String driver, String url, String user, String password) {
        Connection conn = null;
        try {
            Properties props = new Properties();
            props.put("remarksReporting", "true");
            props.put("user", user);
            props.put("password", password);
            Class.forName(driver);
            conn = DriverManager.getConnection(url, props);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static Connection getConnections() {
        return getConnections(MYSQL_DRIVER_CLASS_NAME, MYSQL_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
    }

    /**
     * 其他数据库不需要这个方法 oracle和db2需要
     */
    private static String getSchema(Connection conn) throws Exception {
        String schema = conn.getMetaData().getUserName();
        if ((schema == null) || (schema.length() == 0)) {
            throw new Exception("ORACLE数据库模式不允许为空");
        }
        return schema.toUpperCase();
    }

    public static void main(String[] args) throws SQLException {
        Connection connection = getConnections();
        DatabaseMetaData metaData = connection.getMetaData();

    }

    public static void printResult(List<List<NamedValue>> data) {
        for (List<NamedValue> item : data) {
            StringBuilder row = new StringBuilder();
            for (NamedValue namedValue : item) {
                row.append(namedValue).append("\t");
            }
            System.out.println(row.toString() + "\n");
        }
    }


}
