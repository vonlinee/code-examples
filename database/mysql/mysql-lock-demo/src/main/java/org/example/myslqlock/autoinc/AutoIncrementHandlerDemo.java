package org.example.myslqlock.autoinc;

import org.example.myslqlock.DB;
import org.example.myslqlock.utils.Utils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

/**
 * CREATE TABLE `autoinc_table` (
 * `id` bigint NOT NULL AUTO_INCREMENT,
 * `col1` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
 * `col2` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
 * `col3` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
 * PRIMARY KEY (`id`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
 */
public class AutoIncrementHandlerDemo {

    static final DB db = new DB();

    /**
     * 方式一
     * 普通批量插入，直接将插入语句执行多次即可
     */
    @Test
    public void bulkSubmissionTest1() throws SQLException {
        String sql = "INSERT INTO autoinc_table (col1, col2, col3) VALUES(?, ?, ?);";
        try (Connection conn = db.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                long start = System.currentTimeMillis();//开始计时【单位：毫秒】
                for (int i = 1; i <= 1000000; i++) {
                    ps.setObject(1, "col1-" + i);
                    ps.setObject(2, "col2-" + i);
                    ps.setObject(3, "col3-" + i);
                    ps.execute();  // 执行sql
                }
                System.out.println("百万条数据插入用时：" + Utils.convertToReadableTime(System.currentTimeMillis() - start, TimeUnit.MILLISECONDS));
            }
        }
    }
}
