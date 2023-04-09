package io.devpl.tookit.utils.ssh;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import io.fxtras.Alerts;
import io.devpl.tookit.fxui.common.JDBCDriver;
import io.devpl.tookit.fxui.model.DatabaseInfo;
import io.devpl.tookit.fxui.model.props.ColumnCustomConfiguration;
import io.devpl.tookit.utils.DBUtils;
import io.devpl.tookit.utils.NumberUtils;
import io.devpl.tookit.utils.SQL;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class JSchUtils {

    static Logger log = LoggerFactory.getLogger(JSchUtils.class);

    private static final JSch jsch = new JSch();

    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static volatile boolean portForwaring = false;
    private static final Map<Integer, Session> portForwardingSession = new ConcurrentHashMap<>();

    public static Session getSSHSession(DatabaseInfo databaseConfig) {
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

    public static void shutdownPortForwarding(Session session) {
        portForwaring = false;
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
    }

    public static void engagePortForwarding(Session sshSession, DatabaseInfo config) {
        if (sshSession != null) {
            AtomicInteger assinged_port = new AtomicInteger();
            Future<?> result = executorService.submit(() -> {
                try {
                    Integer lport = NumberUtils.decode(config.getLport(), NumberUtils.parseInteger(config.getLport(), 3306));
                    Integer rport = NumberUtils.decode(config.getRport(), NumberUtils.parseInteger(config.getRport(), 3306));
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
                    if (e.getCause() != null && e.getCause()
                            .getMessage()
                            .equals("Address already in use: JVM_Bind")) {
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
                Alerts.error("OverSSH 失败，请检查连接设置:" + e.getMessage())
                        .showAndWait();
            }
        }
    }

    public static List<String> getTableNames(DatabaseInfo config, String filter) throws Exception {
        Session sshSession = getSSHSession(config);
        engagePortForwarding(sshSession, config);
        try (Connection connection = DBUtils.getConnection(config)) {
            List<String> tables = new ArrayList<>();
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs;
            if (JDBCDriver.valueOf(config.getDbType()) == JDBCDriver.SQL_SERVER) {
                String sql = SQL.SqlServer.SELECT;
                rs = connection.createStatement()
                        .executeQuery(sql);
                while (rs.next()) {
                    tables.add(rs.getString("name"));
                }
            } else if (JDBCDriver.valueOf(config.getDbType()) == JDBCDriver.ORACLE) {
                rs = md.getTables(null, config.getUsername()
                        .toUpperCase(), null, new String[]{"TABLE", "VIEW"});
            } else if (JDBCDriver.valueOf(config.getDbType()) == JDBCDriver.SQLITE) {
                String sql = SQL.Sqllite.SELECT;
                rs = connection.createStatement()
                        .executeQuery(sql);
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
                tables.removeIf(x -> !x.contains(filter) && !(x.replaceAll("_", "")
                        .contains(filter)));
            }
            if (tables.size() > 1) {
                Collections.sort(tables);
            }
            return tables;
        } finally {
            shutdownPortForwarding(sshSession);
        }
    }

    public static List<ColumnCustomConfiguration> getTableColumns(DatabaseInfo dbConfig, String tableName) throws Exception {
        Session sshSession = getSSHSession(dbConfig);
        engagePortForwarding(sshSession, dbConfig);
        Connection conn = DBUtils.getConnection(dbConfig);
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

    public static String getConnectionUrlWithSchema(DatabaseInfo dbConfig) {
        JDBCDriver dbType = JDBCDriver.valueOf(dbConfig.getDbType());
        String connectionUrl = String.format(dbType.getConnectionUrlPattern(), portForwaring ? "127.0.0.1" : dbConfig.getHost(), portForwaring ? dbConfig.getLport() : dbConfig.getPort(), dbConfig.getSchema(), dbConfig.getEncoding());
        log.info("getConnectionUrlWithSchema, connection url: {}", connectionUrl);
        return connectionUrl;
    }
}
