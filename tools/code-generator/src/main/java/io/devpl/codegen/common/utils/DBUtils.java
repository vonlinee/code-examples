package io.devpl.codegen.common.utils;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import io.devpl.codegen.common.DbType;
import io.devpl.codegen.common.exception.DbDriverLoadingException;
import io.devpl.codegen.fxui.model.DatabaseConfiguration;
import io.devpl.codegen.fxui.model.UITableColumnVO;
import io.devpl.codegen.fxui.view.AlertDialog;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.internal.util.ClassloaderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DBUtils {

    private static final Logger _LOG = LoggerFactory.getLogger(DBUtils.class);
    private static final int DB_CONNECTION_TIMEOUTS_SECONDS = 1;

    private static final Map<DbType, Driver> drivers = new HashMap<>();

    private static ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static volatile boolean portForwaring = false;
    private static Map<Integer, Session> portForwardingSession = new ConcurrentHashMap<>();

    public static Session getSSHSession(DatabaseConfiguration databaseConfig) {
        if (StringUtils.isBlank(databaseConfig.getSshHost())
                || StringUtils.isBlank(databaseConfig.getSshPort())
                || StringUtils.isBlank(databaseConfig.getSshUser())
                || (StringUtils.isBlank(databaseConfig.getPrivateKey()) && StringUtils.isBlank(databaseConfig.getSshPassword()))
        ) {
            return null;
        }
        Session session = null;
        try {
            //Set StrictHostKeyChecking property to no to avoid UnknownHostKey issue
            Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            int sshPort = NumberUtils.decodeInt(databaseConfig.getSshPort(), 22);
            session = jsch.getSession(databaseConfig.getSshUser(), databaseConfig.getSshHost(), sshPort);
            if (StringUtils.isNotBlank(databaseConfig.getPrivateKey())) {
                //使用秘钥方式认证
                jsch.addIdentity(databaseConfig.getPrivateKey(), StringUtils.defaultIfBlank(databaseConfig.getPrivateKeyPassword(), null));
            } else {
                session.setPassword(databaseConfig.getSshPassword());
            }
            session.setConfig(config);
        } catch (JSchException e) {
            //Ignore
        }
        return session;
    }

    /**
     * 检查端口是否被占用
     * @param sshSession SSH连接会话
     * @param config     数据库连接配置
     */
    public static void engagePortForwarding(Session sshSession, DatabaseConfiguration config) {
        if (sshSession != null) {
            AtomicInteger assinged_port = new AtomicInteger();
            Future<?> result = executorService.submit(() -> {
                try {
                    int localPort = NumberUtils.decodeInt(config.getLport(), Integer.parseInt(config.getPort()));
                    int remotePort = NumberUtils.decodeInt(config.getRport(), Integer.parseInt(config.getPort()));
                    Session session = portForwardingSession.get(localPort);
                    if (session != null && session.isConnected()) {
                        String s = session.getPortForwardingL()[0];
                        String[] split = StringUtils.split(s, ":");
                        boolean portForwarding = String.format("%s:%s", split[0], split[1]).equals(localPort + ":" + config.getHost());
                        if (portForwarding) {
                            return;
                        }
                    }
                    sshSession.connect();
                    assinged_port.set(sshSession.setPortForwardingL(localPort, config.getHost(), remotePort));
                    portForwardingSession.put(localPort, sshSession);
                    portForwaring = true;
                    _LOG.info("portForwarding Enabled, {}", assinged_port);
                } catch (JSchException e) {
                    _LOG.error("Connect Over SSH failed", e);
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
                _LOG.info("executorService isShutdown:{}", executorService.isShutdown());
                AlertDialog.showError("OverSSH 失败，请检查连接设置:" + e.getMessage());
            }
        }
    }

    public static void shutdownPortForwarding(Session session) {
        portForwaring = false;
        if (session != null && session.isConnected()) {
            session.disconnect();
            _LOG.info("portForwarding turn OFF");
        }
//		executorService.shutdown();
    }

    /**
     * 获取数据库连接
     * @param config
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection getConnection(DatabaseConfiguration config) throws ClassNotFoundException, SQLException {
        DbType dbType = DbType.fromProductName(config.getDbType());
        if (drivers.get(dbType) == null) {
            loadDbDriver(dbType);
        }
        String url = getConnectionUrlWithSchema(config);
        Properties props = new Properties();

        props.setProperty("user", config.getUsername()); //$NON-NLS-1$
        props.setProperty("password", config.getPassword()); //$NON-NLS-1$

        DriverManager.setLoginTimeout(DB_CONNECTION_TIMEOUTS_SECONDS);
        Connection connection = drivers.get(dbType).connect(url, props);
        _LOG.info("getConnection, connection url: {}", connection);
        return connection;
    }

    public static List<String> getTableNames(DatabaseConfiguration config, String filter) throws Exception {
        Session sshSession = getSSHSession(config);
        engagePortForwarding(sshSession, config);
        try (Connection connection = getConnection(config)) {
            List<String> tables = new ArrayList<>();
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs;
            if (DbType.valueOf(config.getDbType()) == DbType.MICROSOFT_SQLSERVER) {
                String sql = "SELECT name FROM sysobjects  WHERE xtype='u' OR xtype='v' ORDER BY name";
                rs = connection.createStatement().executeQuery(sql);
                while (rs.next()) {
                    tables.add(rs.getString("name"));
                }
            } else if (DbType.valueOf(config.getDbType()) == DbType.ORACLE) {
                rs = md.getTables(null, config.getUsername().toUpperCase(), null, new String[]{"TABLE", "VIEW"});
            } else if (DbType.valueOf(config.getDbType()) == DbType.SQLITE) {
                String sql = "SELECT name FROM sqlite_master;";
                rs = connection.createStatement().executeQuery(sql);
                while (rs.next()) {
                    tables.add(rs.getString("name"));
                }
            } else {
                // rs = md.getTables(null, config.getUsername().toUpperCase(), null, null);
                rs = md.getTables(config.getSchema(), null, "%", new String[]{"TABLE", "VIEW"});//针对 postgresql 的左侧数据表显示
            }
            while (rs.next()) {
                tables.add(rs.getString(3));
            }
            if (StringUtils.isNotBlank(filter)) {
                tables.removeIf(x -> !x.contains(filter) && !(x.replaceAll("_", "").contains(filter)));
                ;
            }
            if (tables.size() > 1) {
                Collections.sort(tables);
            }
            return tables;
        } finally {
            shutdownPortForwarding(sshSession);
        }
    }

    public static List<UITableColumnVO> getTableColumns(DatabaseConfiguration dbConfig, String tableName) throws Exception {
        String url = getConnectionUrlWithSchema(dbConfig);
        _LOG.info("getTableColumns, connection url: {}", url);
        Session sshSession = getSSHSession(dbConfig);
        engagePortForwarding(sshSession, dbConfig);
        Connection conn = getConnection(dbConfig);
        try {
            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getColumns(dbConfig.getSchema(), null, tableName, null);
            List<UITableColumnVO> columns = new ArrayList<>();
            while (rs.next()) {
                UITableColumnVO columnVO = new UITableColumnVO();
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

    public static String getConnectionUrlWithSchema(DatabaseConfiguration dbConfig) throws ClassNotFoundException {
        DbType dbType = DbType.fromProductName(dbConfig.getDbType());
        String connectionUrl = String.format(dbType.getConnectionUrlPattern(),
                portForwaring ? "127.0.0.1" : dbConfig.getHost(), portForwaring ? dbConfig.getLport() : dbConfig.getPort(), dbConfig.getSchema(), dbConfig.getEncoding());
        _LOG.info("getConnectionUrlWithSchema, connection url: {}", connectionUrl);
        return connectionUrl;
    }

    /**
     * 加载数据库驱动
     * @param dbType 数据库类型
     */
    private static void loadDbDriver(DbType dbType) {
        List<String> driverJars = ConfigHelper.getAllJDBCDriverJarPaths();
        ClassLoader classloader = ClassloaderUtils.getCustomClassloader(driverJars);
        try {
            Class<?> clazz = Class.forName(dbType.getDriverClassName(), true, classloader);
            Driver driver = (Driver) clazz.newInstance();
            _LOG.info("load driver class: {}", driver);
            drivers.put(dbType, driver);
        } catch (Exception e) {
            _LOG.error("load driver error", e);
            throw new DbDriverLoadingException("找不到" + dbType.getDriverJarFileName() + "驱动");
        }
    }
}
