package io.devpl.tookit.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class DBHelper {

    static final JdbcTemplate template = createJdbcTemplate();

    public static <T> T selectOne(String sql, Class<T> type) {
        List<T> list = template.query(sql, new DataClassRowMapper<>(type));
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public static <T> List<T> selectList(String sql, Class<T> type) {
        return template.query(sql, new DataClassRowMapper<>(type));
    }

    public static Map<String, Object> selectMap(String sql) {
        List<Map<String, Object>> list = selectMapList(sql);
        if (list.isEmpty()) {
            return Collections.emptyMap();
        }
        return list.get(0);
    }

    public static List<Map<String, Object>> selectMapList(String sql) {
        return template.query(sql, new ColumnMapRowMapper());
    }

    private static JdbcTemplate createJdbcTemplate() {
        JdbcTemplate template = new JdbcTemplate();
        Properties properties = new Properties();
        try (InputStream is = DBHelper.class.getResourceAsStream("/druid.properties")) {
            // 通过流读取配置文件中的内容到集合中
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 通过Druid工厂加载文件注册驱动,初始化池子
        DataSource dataSource;
        try {
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        template.setDataSource(dataSource);
        return template;
    }
}
