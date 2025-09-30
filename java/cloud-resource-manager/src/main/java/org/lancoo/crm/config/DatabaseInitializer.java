package org.lancoo.crm.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.util.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class DatabaseInitializer implements SpringApplicationRunListener, BeanPostProcessor {

    private String databaseName;
    SpringApplication application;

    public DatabaseInitializer(SpringApplication application, String[] appName) {
        this.application = application;
    }

    /**
     * 根据url获取库名
     *
     * @param url
     * @return
     */
    public static String extractDatabaseName(String url) {
        Pattern p = Pattern.compile("jdbc:(?<db>\\w+):.*((//)|@)(?<host>.+):(?<port>\\d+)(/|(;DatabaseName=)|:)(?<dbName>\\w+.+)\\?");
        Matcher m = p.matcher(url);
        if (m.find()) {
            return m.group("dbName");
        }
        return null;
    }

    @Override
    public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
        String dataSourceUrl = environment.getProperty("spring.datasource.url");
        if (dataSourceUrl == null) {
            throw new RuntimeException("数据库连接地址为空");
        }
        String username = environment.getProperty("spring.datasource.username");
        String password = environment.getProperty("spring.datasource.password");
        String databaseName = extractDatabaseName(dataSourceUrl);
        if (!StringUtils.hasText(databaseName)) {
            databaseName = environment.getProperty("spring.application.name");
        } else {
            dataSourceUrl = dataSourceUrl.replace("/" + databaseName, "");
        }


        try (Connection connection = DriverManager.getConnection(dataSourceUrl, username, password)) {

            Statement statement = connection.createStatement();

            boolean createDb = true;
            if (statement.execute("SHOW DATABASES")) {
                List<String> list = new ArrayList<>();
                try (ResultSet resultSet = statement.getResultSet()) {
                    SingleColumnRowMapper<String> rowMapper = new SingleColumnRowMapper<>();
                    int rowNum = 0;
                    while (resultSet.next()) {
                        String dbName = rowMapper.mapRow(resultSet, rowNum);
                        list.add(dbName);
                    }
                }
                createDb = !list.contains(databaseName);
            }
            if (createDb) {
                String sql = "CREATE DATABASE IF NOT EXISTS " + databaseName;
                boolean execute = statement.execute(sql);
                if (execute) {
                    log.info("创建数据库{}成功", this.databaseName);
                }
            }
        } catch (SQLException e) {
            log.info("创建数据库{}失败", this.databaseName);
            throw new RuntimeException(e);
        }
    }
}
