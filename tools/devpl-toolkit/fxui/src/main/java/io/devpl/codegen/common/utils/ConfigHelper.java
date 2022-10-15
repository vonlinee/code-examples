package io.devpl.codegen.common.utils;

import com.alibaba.fastjson.JSON;
import io.devpl.codegen.common.DbType;
import io.devpl.codegen.ui.fxui.model.CodeGenConfiguration;
import io.devpl.codegen.ui.fxui.model.DatabaseConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConfigHelper {

    private static final Logger _LOG = LoggerFactory.getLogger(ConfigHelper.class);
    private static final String BASE_DIR = "config";
    private static final String CONFIG_FILE = "/sqlite3.db";

    public static void createEmptyFiles() throws Exception {
        File file = new File(BASE_DIR);
        if (!file.exists()) {
            file.mkdir();
        }
        File uiConfigFile = new File(BASE_DIR + CONFIG_FILE);
        if (!uiConfigFile.exists()) {
            createEmptyXMLFile(uiConfigFile);
        }
    }

    static void createEmptyXMLFile(File uiConfigFile) throws IOException {
        try (InputStream fis = Thread.currentThread().getContextClassLoader().getResourceAsStream("sqlite3.db"); FileOutputStream fos = new FileOutputStream(uiConfigFile)) {
            byte[] buffer = new byte[1024];
            int byteread;
            while ((byteread = Objects.requireNonNull(fis).read(buffer)) != -1) {
                fos.write(buffer, 0, byteread);
            }
        }
    }

    /**
     * 从数据库加载配置
     * @return
     * @throws Exception
     */
    public static List<DatabaseConfiguration> loadDatabaseConfig() throws Exception {
        try (Connection conn = ConnectionManager.getConnection();
             Statement stat = conn.createStatement()) {
            ResultSet rs = stat.executeQuery("SELECT * FROM dbs");
            List<DatabaseConfiguration> configs = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String value = rs.getString("value");
                DatabaseConfiguration databaseConfig = JSON.parseObject(value, DatabaseConfiguration.class);
                databaseConfig.setId(id);
                configs.add(databaseConfig);
            }
            return configs;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void saveDatabaseConfig(boolean isUpdate, Integer primaryKey, DatabaseConfiguration dbConfig) throws Exception {
        String configName = dbConfig.getName();
        ResultSet rs = null;
        try (Connection conn = ConnectionManager.getConnection(); Statement stat = conn.createStatement()) {
            if (!isUpdate) {
                ResultSet rs1 = stat.executeQuery("SELECT * from dbs where name = '" + configName + "'");
                if (rs1.next()) {
                    // throw new RuntimeException("配置已经存在, 请使用其它名字");
                    stat.execute("update dbs set where name = ' + " + configName + "'");
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

    public static void deleteDatabaseConfig(DatabaseConfiguration databaseConfig) throws Exception {
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
            String sql = String.format("INSERT INTO generator_config values('%s', '%s')", generatorConfig.getName(),
                    jsonStr);
            stat.executeUpdate(sql);
        }
    }

    public static CodeGenConfiguration loadGeneratorConfig(String name) throws Exception {
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();
            stat = conn.createStatement();
            String sql = String.format("SELECT * FROM generator_config where name='%s'", name);
            _LOG.info("sql: {}", sql);
            rs = stat.executeQuery(sql);
            CodeGenConfiguration generatorConfig = null;
            if (rs.next()) {
                String value = rs.getString("value");
                generatorConfig = JSON.parseObject(value, CodeGenConfiguration.class);
            }
            return generatorConfig;
        } finally {
            if (rs != null) rs.close();
            if (stat != null) stat.close();
            if (conn != null) conn.close();
        }
    }

    public static List<CodeGenConfiguration> loadGeneratorConfigs() throws Exception {
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();
            stat = conn.createStatement();
            String sql = "SELECT * FROM generator_config";
            _LOG.info("sql: {}", sql);
            rs = stat.executeQuery(sql);
            List<CodeGenConfiguration> configs = new ArrayList<>();
            while (rs.next()) {
                String value = rs.getString("value");
                configs.add(JSON.parseObject(value, CodeGenConfiguration.class));
            }
            return configs;
        } finally {
            if (rs != null) rs.close();
            if (stat != null) stat.close();
            if (conn != null) conn.close();
        }
    }

    public static int deleteGeneratorConfig(String name) throws Exception {
        try (Connection conn = ConnectionManager.getConnection(); Statement stat = conn.createStatement()) {
            String sql = String.format("DELETE FROM generator_config where name='%s'", name);
            _LOG.info("sql: {}", sql);
            return stat.executeUpdate(sql);
        }
    }

    public static String findConnectorLibPath(String dbType) {
        DbType type = DbType.fromProductName(dbType);
        URL resource = Thread.currentThread().getContextClassLoader().getResource("logback-2.xml");
        _LOG.info("jar resource: {}", resource);
        if (resource != null) {
            try {
                File file = new File(resource.toURI().getRawPath() + "/../lib/" + type.getDriverJarFileName());
                return URLDecoder.decode(file.getCanonicalPath(), StandardCharsets.UTF_8.displayName());
            } catch (Exception e) {
                throw new RuntimeException("找不到驱动文件，请联系开发者");
            }
        } else {
            throw new RuntimeException("lib can't find");
        }
    }

    public static List<String> getAllJDBCDriverJarPaths() {
        List<String> jarFilePathList = new ArrayList<>();
        URL url = Thread.currentThread().getContextClassLoader().getResource("logback-2.xml");
        try {
            File file;
            assert url != null;
            if (url.getPath().contains(".jar")) {
                file = new File("lib/");
            } else {
                file = new File("src/main/resources/lib");
            }
            _LOG.info("jar lib path: {}", file.getCanonicalPath());
            File[] jarFiles = file.listFiles();
            if (jarFiles != null && jarFiles.length > 0) {
                for (File jarFile : jarFiles) {
                    _LOG.info("jar file: {}", jarFile.getAbsolutePath());
                    if (jarFile.isFile() && jarFile.getAbsolutePath().endsWith(".jar")) {
                        jarFilePathList.add(jarFile.getAbsolutePath());
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("找不到驱动文件，请联系开发者");
        }
        return jarFilePathList;
    }
}
