package io.devpl.codegen.fxui.utils;

import com.alibaba.fastjson.JSON;
import io.devpl.codegen.fxui.config.DatabaseConfig;
import io.devpl.codegen.fxui.config.DBDriver;
import io.devpl.codegen.fxui.config.CodeGenConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConfigHelper {

    private static final Logger _LOG = LoggerFactory.getLogger(ConfigHelper.class);
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
        try (InputStream fis = Thread.currentThread().getContextClassLoader()
                                     .getResourceAsStream("sqlite3.db"); FileOutputStream fos = new FileOutputStream(uiConfigFile)) {
            byte[] buffer = new byte[1024];
            int byteread = 0;
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
                DatabaseConfig databaseConfig = JSON.parseObject(value, DatabaseConfig.class);
                databaseConfig.setId(id);
                configs.add(databaseConfig);
            }
            return configs;
        }
    }

    public static void saveDatabaseConfig(boolean isUpdate, Integer primaryKey, DatabaseConfig dbConfig) throws Exception {
        String configName = dbConfig.getName();
        ResultSet rs = null;
        try (Connection conn = ConnectionManager.getConnection(); Statement stat = conn.createStatement()) {
            if (!isUpdate) {
                ResultSet rs1 = stat.executeQuery("SELECT * from dbs where name = '" + configName + "'");
                if (rs1.next()) {
                    throw new RuntimeException("配置已经存在, 请使用其它名字");
                }
            }
            String jsonStr = JSON.toJSONString(dbConfig);
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
        ResultSet rs = null;
        try (Connection conn = ConnectionManager.getConnection(); Statement stat = conn.createStatement()) {
            String sql = String.format("delete from dbs where id=%d", databaseConfig.getId());
            stat.executeUpdate(sql);
        }
    }

    public static void saveGeneratorConfig(CodeGenConfiguration generatorConfig) throws Exception {
        ResultSet rs = null;
        try (Connection conn = ConnectionManager.getConnection(); Statement stat = conn.createStatement()) {
            String jsonStr = JSON.toJSONString(generatorConfig);
            String sql = String.format("INSERT INTO generator_config values('%s', '%s')", generatorConfig.getName(), jsonStr);
            stat.executeUpdate(sql);
        }
    }

    public static CodeGenConfiguration loadGeneratorConfig(String name) throws Exception {
        ResultSet rs = null;
        try (Connection conn = ConnectionManager.getConnection(); Statement stat = conn.createStatement()) {
            String sql = String.format("SELECT * FROM generator_config where name='%s'", name);
            _LOG.info("sql: {}", sql);
            rs = stat.executeQuery(sql);
            CodeGenConfiguration generatorConfig = null;
            if (rs.next()) {
                String value = rs.getString("value");
                generatorConfig = JSON.parseObject(value, CodeGenConfiguration.class);
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
                configs.add(JSON.parseObject(value, CodeGenConfiguration.class));
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
