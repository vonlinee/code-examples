package demo.sparksql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.Random;

public class TestDataGenerator {

    public static void generateTestData() throws Exception {
        String url = "jdbc:postgresql://localhost:5432/ecommerce";
        String user = "postgres";
        String password = "password";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Random random = new Random();

            // 生成用户数据
            String[] cities = {"北京", "上海", "广州", "深圳", "杭州", "成都", "武汉", "南京"};
            String[] countries = {"中国"};

            PreparedStatement userStmt = conn.prepareStatement(
                "INSERT INTO users (user_id, username, email, registration_date, city, country) VALUES (?, ?, ?, ?, ?, ?)");

            for (int i = 1; i <= 1000; i++) {
                userStmt.setInt(1, i);
                userStmt.setString(2, "user" + i);
                userStmt.setString(3, "user" + i + "@example.com");
                userStmt.setDate(4, java.sql.Date.valueOf(LocalDate.now().minusDays(random.nextInt(365))));
                userStmt.setString(5, cities[random.nextInt(cities.length)]);
                userStmt.setString(6, countries[0]);
                userStmt.addBatch();

                if (i % 100 == 0) {
                    userStmt.executeBatch();
                }
            }
            userStmt.executeBatch();

            // 生成商品数据
            String[] categories = {"电子产品", "服装", "家居", "美妆", "食品", "图书"};
            String[][] subcategories = {
                {"手机", "电脑", "相机"},
                {"男装", "女装", "童装"},
                {"家具", "厨具", "装饰"},
                {"护肤品", "化妆品", "香水"},
                {"零食", "生鲜", "饮料"},
                {"小说", "科技", "教育"}
            };

            PreparedStatement productStmt = conn.prepareStatement(
                "INSERT INTO products (product_id, product_name, category, subcategory, price, stock_quantity, created_date) VALUES (?, ?, ?, ?, ?, ?, ?)");

            for (int i = 1; i <= 200; i++) {
                int catIndex = random.nextInt(categories.length);
                productStmt.setInt(1, i);
                productStmt.setString(2, "商品" + i);
                productStmt.setString(3, categories[catIndex]);
                productStmt.setString(4, subcategories[catIndex][random.nextInt(subcategories[catIndex].length)]);
                productStmt.setDouble(5, 10 + random.nextDouble() * 990);
                productStmt.setInt(6, random.nextInt(1000));
                productStmt.setDate(7, java.sql.Date.valueOf(LocalDate.now().minusDays(random.nextInt(365))));
                productStmt.addBatch();

                if (i % 50 == 0) {
                    productStmt.executeBatch();
                }
            }
            productStmt.executeBatch();

            System.out.println("测试数据生成完成！");
        }
    }

    public static void main(String[] args) {
        try {
            generateTestData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
