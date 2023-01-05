package io.devpl.toolkit.fxui.utils;

import io.devpl.log.Log;
import io.devpl.log.LogFactory;
import io.devpl.sdk.util.ResourceUtils;
import io.devpl.toolkit.fxui.common.DBDriver;
import io.devpl.toolkit.fxui.model.DatabaseInfo;
import io.devpl.toolkit.fxui.model.props.GenericConfiguration;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// 暂时先不保存配置
public class ConfigHelper {

    private static final Log _LOG = LogFactory.getLog(ConfigHelper.class);
    private static final String BASE_DIR = "config";
    private static final String CONFIG_FILE = "/sqlite3.db";

    public static List<DatabaseInfo> loadDatabaseConfig() throws Exception {
        try (Connection conn = ConnectionManager.getConnection(); Statement stat = conn.createStatement(); ResultSet rs = stat.executeQuery("SELECT * FROM dbs")) {
            List<DatabaseInfo> configs = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String value = rs.getString("value");
                DatabaseInfo databaseConfig = JSONUtils.toObject(value, DatabaseInfo.class);
                databaseConfig.setId(id);
                configs.add(databaseConfig);
            }
            return configs;
        }
    }

    private static final String sql1 = "SELECT * from dbs where name = %s";
    private static final String sql2 = "UPDATE dbs SET name = '%s', value = '%s' where id = %d";
    private static final String sql3 = "INSERT INTO dbs (name, value) values('%s', '%s')";

    public static void saveDatabaseConfig(boolean isUpdate, Integer primaryKey, DatabaseInfo dbConfig) throws Exception {
        String configName = dbConfig.getName();
        try (Connection conn = ConnectionManager.getConnection(); Statement stat = conn.createStatement()) {
            if (!isUpdate) {
                ResultSet rs1 = stat.executeQuery(String.format(sql1, sql1));
                if (rs1.next()) {
                    throw new RuntimeException("配置已经存在, 请使用其它名字");
                }
            }
            String jsonStr = JSONUtils.toString(dbConfig);
            String sql;
            if (isUpdate) {
                sql = String.format(sql2, configName, jsonStr, primaryKey);
            } else {
                sql = String.format(sql3, configName, jsonStr);
            }
            stat.executeUpdate(sql);
        }
    }

    public static void deleteDatabaseConfig(DatabaseInfo databaseConfig) throws Exception {
        try (Connection conn = ConnectionManager.getConnection(); Statement stat = conn.createStatement()) {
            String sql = String.format("delete from dbs where id=%d", databaseConfig.getId());
            stat.executeUpdate(sql);
        }
    }

    public static void saveGeneratorConfig(GenericConfiguration generatorConfig) throws Exception {
        try (Connection conn = ConnectionManager.getConnection(); Statement stat = conn.createStatement()) {
            String jsonStr = JSONUtils.toString(generatorConfig);
            String sql = String.format("INSERT INTO generator_config (name, value) values('%s', '%s')", generatorConfig.getName(), jsonStr);
            stat.executeUpdate(sql);
        }
    }

    public static GenericConfiguration loadGeneratorConfig(String name) throws Exception {
        ResultSet rs;
        try (Connection conn = ConnectionManager.getConnection(); Statement stat = conn.createStatement()) {
            String sql = String.format("SELECT * FROM generator_config where name='%s'", name);
            _LOG.info("sql: {}", sql);
            rs = stat.executeQuery(sql);
            GenericConfiguration generatorConfig = null;
            if (rs.next()) {
                String value = rs.getString("value");
                generatorConfig = JSONUtils.toObject(value, GenericConfiguration.class);
            }
            return generatorConfig;
        }
    }

    public static List<GenericConfiguration> loadGeneratorConfigs() throws Exception {
        ResultSet rs = null;
        try (Connection conn = ConnectionManager.getConnection(); Statement stat = conn.createStatement()) {
            String sql = String.format("SELECT * FROM generator_config");
            _LOG.info("sql: {}", sql);
            rs = stat.executeQuery(sql);
            List<GenericConfiguration> configs = new ArrayList<>();
            while (rs.next()) {
                String value = rs.getString("value");
                configs.add(JSONUtils.toObject(value, GenericConfiguration.class));
            }
            return configs;
        }
    }

    public static int deleteGeneratorConfig(String name) throws Exception {
        try (Connection conn = ConnectionManager.getConnection(); Statement stat = conn.createStatement()) {
            String sql = String.format("DELETE FROM generator_config where name='%s'", name);
            _LOG.info("sql: {}", sql);
            return stat.executeUpdate(sql);
        }
    }

    /**
     * 获取对应数据库类型的驱动jar包路径
     * @param dbType 数据库类型
     * @return
     */
    public static String findConnectorLibPath(String dbType) {
        DBDriver type = DBDriver.valueOf(dbType);
        final Path path = Paths.get("lib", type.getConnectorJarFile());
        final File file;
        try {
            file = new File(ResourceUtils.getProjectResource(path.toString()).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        if (!file.exists()) {
            throw new RuntimeException("找不到驱动文件，请联系开发者");
        }
        return PathUtils.decodeUrl(file.getAbsolutePath());
    }
}
