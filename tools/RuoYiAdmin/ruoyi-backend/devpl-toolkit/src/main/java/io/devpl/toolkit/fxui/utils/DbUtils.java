package io.devpl.toolkit.fxui.utils;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import io.devpl.toolkit.fxui.common.model.ColumnCustomConfiguration;
import io.devpl.toolkit.fxui.framework.Alerts;
import io.devpl.toolkit.fxui.config.DatabaseConfig;
import io.devpl.toolkit.fxui.config.DBDriver;
import io.devpl.sdk.util.ResourceUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;

import java.io.File;
import java.sql.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DbUtils {

    private static final Log log = LogFactory.getLog(DbUtils.class);

    /**
     * 数据库连接超时时长
     */
    private static final int DB_CONNECTION_TIMEOUTS_SECONDS = 1;

    private static final Map<DBDriver, Driver> drivers = new HashMap<>();

    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static volatile boolean portForwaring = false;
    private static final Map<Integer, Session> portForwardingSession = new ConcurrentHashMap<>();

    private static final JSch jsch = new JSch();

    public static Session getSSHSession(DatabaseConfig databaseConfig) {
        if (StringUtils.isBlank(databaseConfig.getSshHost()) || StringUtils.isBlank(databaseConfig.getSshPort()) || StringUtils.isBlank(databaseConfig.getSshUser()) || (StringUtils.isBlank(databaseConfig.getPrivateKey()) && StringUtils.isBlank(databaseConfig.getSshPassword()))) {
            return null;
        }
        Session session = null;
        try {
            // Set StrictHostKeyChecking property to no to avoid UnknownHostKey issue
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            Integer port = NumberUtils.decode(databaseConfig.getSshPort(), 22);
            session = jsch.getSession(databaseConfig.getSshUser(), databaseConfig.getSshHost(), port);
            if (StringUtils.isNotBlank(databaseConfig.getPrivateKey())) {
                // 使用秘钥方式认证
                jsch.addIdentity(databaseConfig.getPrivateKey(), StringUtils.defaultIfBlank(databaseConfig.getPrivateKeyPassword(), null));
            } else {
                session.setPassword(databaseConfig.getSshPassword());
            }
            session.setConfig(config);
        } catch (JSchException e) {
            // Ignore
        }
        return session;
    }

    public static void engagePortForwarding(Session sshSession, DatabaseConfig config) {
        if (sshSession != null) {
            AtomicInteger assinged_port = new AtomicInteger();
            Future<?> result = executorService.submit(() -> {
                try {
                    Integer lport = NumberUtils.decode(config.getLport(), NumberUtils.parseInt(config.getLport(), 3306));
                    Integer rport = NumberUtils.decode(config.getRport(), NumberUtils.parseInt(config.getRport(), 3306));
                    Session session = portForwardingSession.get(lport);
                    if (session != null && session.isConnected()) {
                        String s = session.getPortForwardingL()[0];
                        String[] split = StringUtils.split(s, ":");
                        boolean portForwarding = String.format("%s:%s", split[0], split[1])
                                                       .equals(lport + ":" + config.getHost());
                        if (portForwarding) {
                            return;
                        }
                    }
                    sshSession.connect();
                    assinged_port.set(sshSession.setPortForwardingL(lport, config.getHost(), rport));
                    portForwardingSession.put(lport, sshSession);
                    portForwaring = true;
                    log.info("portForwarding Enabled, {}", assinged_port);
                } catch (JSchException e) {
                    log.error("Connect Over SSH failed", e);
                    if (e.getCause() != null && e.getCause().getMessage().equals("Address already in use: JVM_Bind")) {
                        throw new RuntimeException("Address already in use: JVM_Bind");
                    }
                    throw new RuntimeException(e.getMessage());
                }
            });
            try {
                result.get(5, TimeUnit.SECONDS);
            } catch (Exception e) {
                shutdownPortForwarding(sshSession);
                if (e.getCause() instanceof RuntimeException) {
                    throw (RuntimeException) e.getCause();
                }
                if (e instanceof TimeoutException) {
                    throw new RuntimeException("OverSSH 连接超时：超过5秒");
                }
                log.info("executorService isShutdown:{}", executorService.isShutdown());
                Alerts.error("OverSSH 失败，请检查连接设置:" + e.getMessage()).showAndWait();
            }
        }
    }

    public static void shutdownPortForwarding(Session session) {
        portForwaring = false;
        if (session != null && session.isConnected()) {
            session.disconnect();
            log.info("portForwarding turn OFF");
        }
    }

    public static Connection getConnection(DatabaseConfig config) throws SQLException {
        DBDriver dbType = DBDriver.valueOf(config.getDbType());
        loadDbDriver(dbType);
        String url = getConnectionUrlWithSchema(config);
        Properties props = new Properties();
        props.setProperty("user", config.getUsername()); //$NON-NLS-1$
        props.setProperty("password", config.getPassword()); //$NON-NLS-1$
        DriverManager.setLoginTimeout(DB_CONNECTION_TIMEOUTS_SECONDS);
        Connection connection = getConnection(url, props);
        if (connection == null) {
            throw new RuntimeException("获取连接失败");
        }
        return connection;
    }

    public static Connection getConnection(String url, Properties properties) {
        try {
            return DriverManager.getConnection(url, properties);
        } catch (SQLException e) {
            return null;
        }
    }

    public static List<String> getTableNames(DatabaseConfig config, String filter) throws Exception {
        Session sshSession = getSSHSession(config);
        engagePortForwarding(sshSession, config);
        try (Connection connection = getConnection(config)) {
            List<String> tables = new ArrayList<>();
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs;
            if (DBDriver.valueOf(config.getDbType()) == DBDriver.SQL_SERVER) {
                String sql = SQL.SqlServer.SELECT;
                rs = connection.createStatement().executeQuery(sql);
                while (rs.next()) {
                    tables.add(rs.getString("name"));
                }
            } else if (DBDriver.valueOf(config.getDbType()) == DBDriver.ORACLE) {
                rs = md.getTables(null, config.getUsername().toUpperCase(), null, new String[]{"TABLE", "VIEW"});
            } else if (DBDriver.valueOf(config.getDbType()) == DBDriver.SQLITE) {
                String sql = SQL.Sqllite.SELECT;
                rs = connection.createStatement().executeQuery(sql);
                while (rs.next()) {
                    tables.add(rs.getString("name"));
                }
            } else {
                // rs = md.getTables(null, config.getUsername().toUpperCase(), null, null);
                rs = md.getTables(config.getSchema(), null, "%", new String[]{"TABLE", "VIEW"});// 针对 postgresql 的左侧数据表显示
            }
            while (rs.next()) {
                tables.add(rs.getString(3));
            }
            if (StringUtils.isNotBlank(filter)) {
                tables.removeIf(x -> !x.contains(filter) && !(x.replaceAll("_", "").contains(filter)));
            }
            if (tables.size() > 1) {
                Collections.sort(tables);
            }
            return tables;
        } finally {
            shutdownPortForwarding(sshSession);
        }
    }

    public static List<ColumnCustomConfiguration> getTableColumns(DatabaseConfig dbConfig, String tableName) throws Exception {
        String url = getConnectionUrlWithSchema(dbConfig);
        log.info("getTableColumns, connection url: {}", url);
        Session sshSession = getSSHSession(dbConfig);
        engagePortForwarding(sshSession, dbConfig);
        Connection conn = getConnection(dbConfig);
        try {
            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getColumns(dbConfig.getSchema(), null, tableName, null);
            List<ColumnCustomConfiguration> columns = new ArrayList<>();
            while (rs.next()) {
                ColumnCustomConfiguration columnVO = new ColumnCustomConfiguration();
                String columnName = rs.getString("COLUMN_NAME");
                columnVO.setColumnName(columnName);
                columnVO.setJdbcType(rs.getString("TYPE_NAME"));
                columns.add(columnVO);
            }
            return columns;
        } finally {
            conn.close();
            shutdownPortForwarding(sshSession);
        }
    }

    public static String getConnectionUrlWithSchema(DatabaseConfig dbConfig) {
        DBDriver dbType = DBDriver.valueOf(dbConfig.getDbType());
        String connectionUrl = String.format(dbType.getConnectionUrlPattern(), portForwaring ? "127.0.0.1" : dbConfig.getHost(), portForwaring ? dbConfig.getLport() : dbConfig.getPort(), dbConfig.getSchema(), dbConfig.getEncoding());
        log.info("getConnectionUrlWithSchema, connection url: {}", connectionUrl);
        return connectionUrl;
    }

    public static List<String> getAllJDBCDriverJarPaths() {
        List<String> jarFilePathList = new ArrayList<>();
        try {
            File file = ResourceUtils.getProjectFile("/lib");
            if (file == null) {
                return Collections.emptyList();
            }
            final File[] jarFiles = FileUtils.listAllFiles(file);
            for (int i = 0; i < jarFiles.length; i++) {
                if (jarFiles[i].isFile() && jarFiles[i].getAbsolutePath().endsWith(".jar")) {
                    jarFilePathList.add(jarFiles[i].getAbsolutePath());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("找不到驱动文件，请联系开发者");
        }
        return jarFilePathList;
    }

    /**
     * 加载数据库驱动
     * @param dbType 数据库类型
     * @see DBDriver
     */
    private static void loadDbDriver(DBDriver dbType) {
        if (drivers.containsKey(dbType)) {
            return;
        }
        ClassLoader classloader = ResourceUtils.getClassLoader();
        try {
            Class<?> clazz = Class.forName(dbType.getDriverClass(), true, classloader);
            Driver driver = (Driver) clazz.getConstructor().newInstance();
            log.info("load driver class: {}", driver);
            drivers.putIfAbsent(dbType, driver);
        } catch (Exception e) {
            log.error("load driver error", e);
            throw new RuntimeException("找不到" + dbType.getConnectorJarFile() + "驱动");
        }
    }
}
