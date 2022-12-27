package io.devpl.toolkit.fxui.utils;

import io.devpl.sdk.util.ResourceUtils;
import io.devpl.toolkit.fxui.config.CodeGenConfiguration;
import io.devpl.toolkit.fxui.config.DBDriver;
import io.devpl.toolkit.fxui.config.DatabaseConfig;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

    public static void createEmptyFiles() throws Exception {
        File file = new File(BASE_DIR);
        if (!file.exists()) {
            boolean mkdir = file.mkdir();
        }
        File uiConfigFile = new File(BASE_DIR + CONFIG_FILE);
        if (!uiConfigFile.exists()) {
            createEmptyXMLFile(uiConfigFile);
        }
    }

    static void createEmptyXMLFile(File uiConfigFile) throws IOException {
        try (InputStream fis = Thread
                .currentThread()
                .getContextClassLoader()
                .getResourceAsStream("sqlite3.db"); FileOutputStream fos = new FileOutputStream(uiConfigFile)) {
            byte[] buffer = new byte[1024];
            int byteread;
            while (true) {
                assert fis != null;
                if ((byteread = fis.read(buffer)) == -1) break;
                fos.write(buffer, 0, byteread);
            }
        }
    }

    public static List<DatabaseConfig> loadDatabaseConfig() throws Exception {
        try (Connection conn = ConnectionManager.getConnection(); Statement stat = conn.createStatement(); ResultSet rs = stat.executeQuery("SELECT * FROM dbs")) {
            List<DatabaseConfig> configs = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String value = rs.getString("value");
                DatabaseConfig databaseConfig = JSONUtils.fromString(value, DatabaseConfig.class);
                databaseConfig.setId(id);
                configs.add(databaseConfig);
            }
            return configs;
        }
    }

    public static void saveDatabaseConfig(boolean isUpdate, Integer primaryKey, DatabaseConfig dbConfig) throws Exception {
        String configName = dbConfig.getName();
        try (Connection conn = ConnectionManager.getConnection(); Statement stat = conn.createStatement()) {
            if (!isUpdate) {
                ResultSet rs1 = stat.executeQuery("SELECT * from dbs where name = '" + configName + "'");
                if (rs1.next()) {
                    throw new RuntimeException("配置已经存在, 请使用其它名字");
                }
            }
            String jsonStr = JSONUtils.toString(dbConfig);
            String sql;
            if (isUpdate) {
                sql = String.format("UPDATE dbs SET name = '%s', value = '%s' where id = %d", configName, jsonStr, primaryKey);
            } else {
                sql = String.format("INSERT INTO dbs (name, value) values('%s', '%s')", configName, jsonStr);
            }
            stat.executeUpdate(sql);
        }
    }

    public static void deleteDatabaseConfig(DatabaseConfig databaseConfig) throws Exception {
        try (Connection conn = ConnectionManager.getConnection(); Statement stat = conn.createStatement()) {
            String sql = String.format("delete from dbs where id=%d", databaseConfig.getId());
            stat.executeUpdate(sql);
        }
    }

    public static void saveGeneratorConfig(CodeGenConfiguration generatorConfig) throws Exception {
        try (Connection conn = ConnectionManager.getConnection(); Statement stat = conn.createStatement()) {
            String jsonStr = JSONUtils.toString(generatorConfig);
            String sql = String.format("INSERT INTO generator_config (name, value) values('%s', '%s')", generatorConfig.getName(), jsonStr);
            stat.executeUpdate(sql);
        }
    }

    public static CodeGenConfiguration loadGeneratorConfig(String name) throws Exception {
        ResultSet rs;
        try (Connection conn = ConnectionManager.getConnection(); Statement stat = conn.createStatement()) {
            String sql = String.format("SELECT * FROM generator_config where name='%s'", name);
            _LOG.info("sql: {}", sql);
            rs = stat.executeQuery(sql);
            CodeGenConfiguration generatorConfig = null;
            if (rs.next()) {
                String value = rs.getString("value");
                generatorConfig = JSONUtils.fromString(value, CodeGenConfiguration.class);
            }
            return generatorConfig;
        }
    }

    public static List<CodeGenConfiguration> loadGeneratorConfigs() throws Exception {
        ResultSet rs = null;
        try (Connection conn = ConnectionManager.getConnection(); Statement stat = conn.createStatement()) {
            String sql = String.format("SELECT * FROM generator_config");
            _LOG.info("sql: {}", sql);
            rs = stat.executeQuery(sql);
            List<CodeGenConfiguration> configs = new ArrayList<>();
            while (rs.next()) {
                String value = rs.getString("value");
                configs.add(JSONUtils.fromString(value, CodeGenConfiguration.class));
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
