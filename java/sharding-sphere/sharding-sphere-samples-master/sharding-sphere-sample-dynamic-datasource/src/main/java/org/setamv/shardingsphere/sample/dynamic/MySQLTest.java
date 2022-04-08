package org.setamv.shardingsphere.sample.dynamic;


import com.google.common.util.concurrent.RateLimiter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MySQLTest {

    public static void main(String[] args) throws Exception {
        testQuery();
//        testSyncPerf();
    }

    public static void testQuery() throws Exception {
        Thread[] queryThreads = new QueryThread[2];
        for (int i = 0; i < queryThreads.length; i++) {
            RateLimiter rateLimiter = RateLimiter.create(100);
            queryThreads[i] = new QueryThread("thread-" + i, rateLimiter);
            queryThreads[i].start();
        }
        for (Thread queryThread : queryThreads) {
            queryThread.join();
        }
    }

    public static class QueryThread extends Thread {
        private String name;
        private RateLimiter rateLimiter;

        public QueryThread(String name, RateLimiter rateLimiter) {
            this.name = name;
            this.rateLimiter = rateLimiter;
        }

        @Override
        public void run() {
            query(name, rateLimiter);
        }
    }


    public static void testSyncPerf() throws Exception {
        int n = 10;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            sync();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("avg time: " + (endTime - startTime) / n);
    }

    public static void sync() throws Exception {
        String cmd = "cmd.exe /c python2 D:/DevTools/datax/bin/datax.py D:/Workspace/datax/mysql2mysql.json";
        Runtime rt = Runtime.getRuntime();
        Process p = rt.exec(cmd);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream(), "GBK"))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        };
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\nerror:\n");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getErrorStream(), "GBK"))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        };
        System.out.println("exit value: " + p.exitValue());
        p.waitFor();
        p.destroy();
    }

    public static void query(String threadName, RateLimiter rateLimiter) {
        String sql = "select * from market_quotation order by id";
        try {
            Connection connection = getConnection();
            Statement stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            stmt.setFetchSize(Integer.MIN_VALUE);
            stmt.setQueryTimeout(10);
            ResultSet rs = stmt.executeQuery(sql);
            DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd");
            int count = 0;
            long startTime = System.currentTimeMillis();
            while (rs.next()) {
                String stockCode = rs.getString("stock_code");
                Date tradeDate = rs.getDate("trade_date");
                count ++;
                long costTime = System.currentTimeMillis() - startTime;
                System.out.println(String.format("[%s] [%s] [%s] [%s(ms)]: stockCode=%s, reportDate=%s",
                        threadName,
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS")),
                        count,
                        costTime,
                        stockCode,
                        dfm.format(tradeDate)));
                if (rateLimiter != null) {
                    rateLimiter.acquire();
                }
                if (count >= 20000) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void queryStock(String threadName, RateLimiter rateLimiter) {
        String sql = "select * from stock";
        try {
            Connection connection = getConnection();
            Statement stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            stmt.setFetchSize(Integer.MIN_VALUE);
            // 超时时间，单位秒
            stmt.setQueryTimeout(10);
            ResultSet rs = stmt.executeQuery(sql);
            int count = 0;
            long startTime = System.currentTimeMillis();
            while (rs.next()) {
                String stockCode = rs.getString("stock_code");
                String stockName = rs.getString("stock_name");
                count ++;
                long costTime = System.currentTimeMillis() - startTime;
                System.out.println(String.format("[%s] [%s] [%s] [%s(ms)]: stockCode=%s, stockName=%s",
                        threadName,
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS")),
                        count,
                        costTime,
                        stockCode,
                        stockName));
                if (rateLimiter != null) {
                    rateLimiter.acquire();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/stock";
        String user = "root";
        String password = "root";
        String driverClass = "com.mysql.jdbc.Driver";

        // 加载JDBC驱动程序
        Class.forName(driverClass);
        return DriverManager.getConnection(url, user, password);
    }
}
