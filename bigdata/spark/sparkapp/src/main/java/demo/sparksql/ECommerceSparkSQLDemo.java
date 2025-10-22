package demo.sparksql;

import org.apache.spark.sql.*;
import org.apache.spark.sql.expressions.Window;
import org.apache.spark.sql.expressions.WindowSpec;
import scala.collection.immutable.Seq;

import static org.apache.spark.sql.functions.*;

import java.sql.Date;
import java.util.Properties;

public class ECommerceSparkSQLDemo {

    private final SparkSession spark;
    private final String jdbcUrl;
    private final Properties connectionProperties;

    public ECommerceSparkSQLDemo() {
        // 初始化 SparkSession
        this.spark = SparkSession.builder()
                .appName("ECommerceSparkSQLDemo")
                .master("local[*]")
                .config("spark.sql.adaptive.enabled", "true")
                .config("spark.sql.adaptive.coalescePartitions.enabled", "true")
                .getOrCreate();

        // 配置 PostgreSQL 连接
        this.jdbcUrl = "jdbc:postgresql://localhost:5432/ecommerce";
        this.connectionProperties = new Properties();
        connectionProperties.setProperty("user", "postgres");
        connectionProperties.setProperty("password", "password");
        connectionProperties.setProperty("driver", "org.postgresql.Driver");
    }

    /**
     * 从 PostgreSQL 读取数据
     */
    public Dataset<Row> readTable(String tableName) {
        return spark.read()
                .jdbc(jdbcUrl, tableName, connectionProperties);
    }

    /**
     * 复杂分析1: 用户购买行为分析
     */
    public void userPurchaseAnalysis() {
        System.out.println("=== 用户购买行为分析 ===");

        Dataset<Row> users = readTable("users");
        Dataset<Row> orders = readTable("orders");
        Dataset<Row> orderItems = readTable("order_items");

        // 使用 Spark SQL API 进行复杂操作
        Dataset<Row> userPurchaseStats = users
            .join(orders, "user_id")
            .join(orderItems, "order_id")
            .groupBy(
                col("user_id"),
                col("username"),
                col("city"),
                col("country")
            )
            .agg(
                countDistinct("order_id").alias("order_count"),
                sum("quantity").alias("total_items_purchased"),
                sum("subtotal").alias("total_spent"),
                avg("subtotal").alias("avg_order_value"),
                max("order_date").alias("last_purchase_date")
            )
            .withColumn("customer_segment",
                when(col("total_spent").gt(1000), "VIP")
                .when(col("total_spent").between(500, 1000), "Loyal")
                .when(col("total_spent").between(100, 500), "Regular")
                .otherwise("Occasional")
            )
            .orderBy(col("total_spent").desc());

        userPurchaseStats.show(20);

        // 等效的 SQL 查询
        users.createOrReplaceTempView("users");
        orders.createOrReplaceTempView("orders");
        orderItems.createOrReplaceTempView("order_items");

        spark.sql(
            "SELECT " +
            "   u.user_id, " +
            "   u.username, " +
            "   u.city, " +
            "   u.country, " +
            "   COUNT(DISTINCT o.order_id) as order_count, " +
            "   SUM(oi.quantity) as total_items_purchased, " +
            "   SUM(oi.subtotal) as total_spent, " +
            "   AVG(oi.subtotal) as avg_order_value, " +
            "   MAX(o.order_date) as last_purchase_date, " +
            "   CASE " +
            "       WHEN SUM(oi.subtotal) > 1000 THEN 'VIP' " +
            "       WHEN SUM(oi.subtotal) BETWEEN 500 AND 1000 THEN 'Loyal' " +
            "       WHEN SUM(oi.subtotal) BETWEEN 100 AND 500 THEN 'Regular' " +
            "       ELSE 'Occasional' " +
            "   END as customer_segment " +
            "FROM users u " +
            "JOIN orders o ON u.user_id = o.user_id " +
            "JOIN order_items oi ON o.order_id = oi.order_id " +
            "GROUP BY u.user_id, u.username, u.city, u.country " +
            "ORDER BY total_spent DESC"
        ).show(20);
    }

    /**
     * 复杂分析2: 商品销售排名和窗口函数
     */
    public void productSalesRanking() {
        System.out.println("=== 商品销售排名分析 ===");

        Dataset<Row> products = readTable("products");
        Dataset<Row> orderItems = readTable("order_items");
        Dataset<Row> orders = readTable("orders");

        // 定义窗口规范 - 按类别分区，按销售额排序
        WindowSpec categoryWindow = Window
            .partitionBy("category")
            .orderBy(col("total_sales").desc());

        WindowSpec overallWindow = Window
            .orderBy(col("total_sales").desc());

        Dataset<Row> productSales = products
            .join(orderItems, "product_id")
            .join(orders, "order_id")
            .filter(col("status").equalTo("completed"))
            .groupBy(
                col("product_id"),
                col("product_name"),
                col("category"),
                col("subcategory")
            )
            .agg(
                sum("quantity").alias("total_quantity_sold"),
                sum("subtotal").alias("total_sales"),
                countDistinct("order_id").alias("order_count"),
                approx_count_distinct("user_id").alias("unique_customers")
            )
            .withColumn("category_rank", rank().over(categoryWindow))
            .withColumn("overall_rank", rank().over(overallWindow))
            .withColumn("sales_percentage",
                col("total_sales").divide(sum("total_sales").over()).multiply(100))
            .filter(col("category_rank").leq(5))  // 每个类别前5名
            .orderBy(col("category"), col("category_rank"));

        productSales.show(30);

        // 等效 SQL
        products.createOrReplaceTempView("products");
        orderItems.createOrReplaceTempView("order_items");
        orders.createOrReplaceTempView("orders");

        spark.sql(
            "WITH product_sales AS (" +
            "   SELECT " +
            "       p.product_id, " +
            "       p.product_name, " +
            "       p.category, " +
            "       p.subcategory, " +
            "       SUM(oi.quantity) as total_quantity_sold, " +
            "       SUM(oi.subtotal) as total_sales, " +
            "       COUNT(DISTINCT oi.order_id) as order_count, " +
            "       APPROX_COUNT_DISTINCT(o.user_id) as unique_customers " +
            "   FROM products p " +
            "   JOIN order_items oi ON p.product_id = oi.product_id " +
            "   JOIN orders o ON oi.order_id = o.order_id " +
            "   WHERE o.status = 'completed' " +
            "   GROUP BY p.product_id, p.product_name, p.category, p.subcategory" +
            "), " +
            "ranked_products AS (" +
            "   SELECT *, " +
            "       RANK() OVER (PARTITION BY category ORDER BY total_sales DESC) as category_rank, " +
            "       RANK() OVER (ORDER BY total_sales DESC) as overall_rank, " +
            "       (total_sales * 100.0 / SUM(total_sales) OVER ()) as sales_percentage " +
            "   FROM product_sales" +
            ") " +
            "SELECT * FROM ranked_products " +
            "WHERE category_rank <= 5 " +
            "ORDER BY category, category_rank"
        ).show(30);
    }

    /**
     * 复杂分析3: 用户行为漏斗分析
     */
    public void userBehaviorFunnel() {
        System.out.println("=== 用户行为漏斗分析 ===");

        Dataset<Row> userBehavior = readTable("user_behavior");
        Dataset<Row> users = readTable("users");

        // 使用窗口函数分析用户行为序列
        WindowSpec userWindow = Window
            .partitionBy("user_id", "product_id")
            .orderBy("behavior_time");

        Dataset<Row> behaviorFunnel = userBehavior
            .join(users.select("user_id", "city", "country"), "user_id")
            .withColumn("behavior_sequence",
                collect_list("behavior_type").over(userWindow))
            .withColumn("next_behavior",
                lead("behavior_type", 1).over(userWindow))
            .withColumn("time_to_next_behavior",
                when(lead("behavior_time", 1).over(userWindow).isNotNull(),
                    unix_timestamp(lead("behavior_time", 1).over(userWindow))
                    .minus(unix_timestamp(col("behavior_time"))))
                .otherwise(null))
            .filter(col("behavior_type").isin("view", "cart", "purchase"))
            .groupBy("behavior_type", "city", "country")
            .agg(
                count("behavior_id").alias("behavior_count"),
                avg("time_to_next_behavior").alias("avg_time_to_next_seconds"),
                countDistinct("user_id").alias("unique_users")
            )
            .withColumn("conversion_rate",
                when(col("behavior_type").equalTo("view"),
                    col("behavior_count").cast("double")
                    .divide(first("behavior_count").over(Window.orderBy("behavior_type"))))
                .otherwise(null))
            .orderBy("behavior_type", (Seq<String>) col("behavior_count").desc());

        behaviorFunnel.show();

        // 漏斗分析 SQL
        userBehavior.createOrReplaceTempView("user_behavior");
        users.createOrReplaceTempView("users");

        spark.sql(
            "WITH behavior_counts AS (" +
            "   SELECT " +
            "       ub.behavior_type, " +
            "       u.city, " +
            "       u.country, " +
            "       COUNT(*) as behavior_count, " +
            "       COUNT(DISTINCT ub.user_id) as unique_users " +
            "   FROM user_behavior ub " +
            "   JOIN users u ON ub.user_id = u.user_id " +
            "   WHERE ub.behavior_type IN ('view', 'cart', 'purchase') " +
            "   GROUP BY ub.behavior_type, u.city, u.country" +
            "), " +
            "total_views AS (" +
            "   SELECT SUM(behavior_count) as total_view_count " +
            "   FROM behavior_counts " +
            "   WHERE behavior_type = 'view'" +
            ") " +
            "SELECT " +
            "   bc.behavior_type, " +
            "   bc.city, " +
            "   bc.country, " +
            "   bc.behavior_count, " +
            "   bc.unique_users, " +
            "   CASE " +
            "       WHEN bc.behavior_type = 'view' THEN 100.0 " +
            "       WHEN bc.behavior_type = 'cart' THEN " +
            "           (bc.behavior_count * 100.0 / (SELECT total_view_count FROM total_views)) " +
            "       WHEN bc.behavior_type = 'purchase' THEN " +
            "           (bc.behavior_count * 100.0 / (SELECT total_view_count FROM total_views)) " +
            "   END as conversion_rate_from_view " +
            "FROM behavior_counts bc " +
            "ORDER BY " +
            "   CASE bc.behavior_type " +
            "       WHEN 'view' THEN 1 " +
            "       WHEN 'cart' THEN 2 " +
            "       WHEN 'purchase' THEN 3 " +
            "   END, bc.behavior_count DESC"
        ).show();
    }

    /**
     * 复杂分析4: 月度销售趋势和同比环比分析
     */
    public void monthlySalesTrend() {
        System.out.println("=== 月度销售趋势分析 ===");

        Dataset<Row> orders = readTable("orders");
        Dataset<Row> orderItems = readTable("order_items");
        Dataset<Row> products = readTable("products");

        Dataset<Row> monthlySales = orders
            .filter(col("status").equalTo("completed"))
            .join(orderItems, "order_id")
            .join(products, "product_id")
            .withColumn("order_month", date_format(col("order_date"), "yyyy-MM"))
            .withColumn("order_year", year(col("order_date")))
            .withColumn("order_year_month", date_format(col("order_date"), "yyyy-MM"))
            .groupBy("order_year", "order_month", "order_year_month", "category")
            .agg(
                sum("subtotal").alias("monthly_sales"),
                sum("quantity").alias("monthly_quantity"),
                countDistinct("order_id").alias("order_count"),
                countDistinct("user_id").alias("customer_count")
            )
            .withColumn("prev_month_sales",
                lag("monthly_sales", 1).over(Window.partitionBy("category").orderBy("order_year_month")))
            .withColumn("mom_growth_rate",
                when(col("prev_month_sales").isNotNull().and(col("prev_month_sales").notEqual(0)),
                    col("monthly_sales").minus(col("prev_month_sales"))
                    .divide(col("prev_month_sales"))
                    .multiply(100))
                .otherwise(null))
            .withColumn("category_rank",
                rank().over(Window.partitionBy("order_year_month").orderBy(col("monthly_sales").desc())))
            .orderBy(col("order_year_month").desc(), col("category_rank"));

        monthlySales.show(30);
    }

    /**
     * 复杂分析5: RFM 用户分群
     */
    public void rfmAnalysis() {
        System.out.println("=== RFM 用户分群分析 ===");

        Dataset<Row> users = readTable("users");
        Dataset<Row> orders = readTable("orders");

        // 计算 RFM 指标
        Dataset<Row> currentDate = spark.sql("SELECT current_date() as current_date");
        Date currentDateValue = currentDate.collectAsList().get(0).getDate(0);

        Dataset<Row> rfmData = orders
            .filter(col("status").equalTo("completed"))
            .groupBy("user_id")
            .agg(
                datediff(lit(currentDateValue), max("order_date")).alias("recency"),
                count("order_id").alias("frequency"),
                sum("total_amount").alias("monetary")
            )
            .join(users.select("user_id", "username", "city"), "user_id");

        // 使用分位数进行 RFM 分箱
        Dataset<Row> rfmWithQuartiles = rfmData
            .withColumn("r_quartile",
                when(col("recency").leq(30), 4)
                .when(col("recency").between(31, 90), 3)
                .when(col("recency").between(91, 180), 2)
                .otherwise(1))
            .withColumn("f_quartile",
                when(col("frequency").geq(10), 4)
                .when(col("frequency").between(5, 9), 3)
                .when(col("frequency").between(2, 4), 2)
                .otherwise(1))
            .withColumn("m_quartile",
                when(col("monetary").geq(1000), 4)
                .when(col("monetary").between(500, 999), 3)
                .when(col("monetary").between(100, 499), 2)
                .otherwise(1))
            .withColumn("rfm_score",
                concat(col("r_quartile").cast("string"),
                       col("f_quartile").cast("string"),
                       col("m_quartile").cast("string")))
            .withColumn("rfm_segment",
                when(col("rfm_score").isin("444", "443", "434"), "Champions")
                .when(col("rfm_score").like("4__"), "Loyal Customers")
                .when(col("rfm_score").like("3__"), "Potential Loyalists")
                .when(col("r_quartile").equalTo(1), "At Risk")
                .when(col("r_quartile").equalTo(1).and(col("f_quartile").geq(3)), "Can't Lose Them")
                .when(col("r_quartile").equalTo(2), "Need Attention")
                .otherwise("Other"))
            .orderBy(col("monetary").desc());

        rfmWithQuartiles.show(30);

        // RFM 分群统计
        rfmWithQuartiles
            .groupBy("rfm_segment")
            .agg(
                count("user_id").alias("customer_count"),
                avg("recency").alias("avg_recency"),
                avg("frequency").alias("avg_frequency"),
                avg("monetary").alias("avg_monetary"),
                sum("monetary").alias("total_monetary")
            )
            .orderBy(col("total_monetary").desc())
            .show();
    }

    /**
     * 将分析结果写回 PostgreSQL
     */
    public void writeResultsToPostgreSQL() {
        // 重新计算用户购买行为分析
        Dataset<Row> users = readTable("users");
        Dataset<Row> orders = readTable("orders");
        Dataset<Row> orderItems = readTable("order_items");

        Dataset<Row> userStats = users
            .join(orders, "user_id")
            .join(orderItems, "order_id")
            .groupBy("user_id")
            .agg(
                countDistinct("order_id").alias("order_count"),
                sum("quantity").alias("total_items_purchased"),
                sum("subtotal").alias("total_spent"),
                max("order_date").alias("last_purchase_date")
            );

        // 写回到 PostgreSQL 的新表
        userStats.write()
            .mode(SaveMode.Overwrite)
            .jdbc(jdbcUrl, "user_purchase_stats", connectionProperties);

        System.out.println("分析结果已写入 PostgreSQL");
    }

    public static void main(String[] args) {
        ECommerceSparkSQLDemo demo = new ECommerceSparkSQLDemo();

        try {
            // 执行各种分析
            demo.userPurchaseAnalysis();
            demo.productSalesRanking();
            demo.userBehaviorFunnel();
            demo.monthlySalesTrend();
            demo.rfmAnalysis();

            // 将结果写回数据库
            demo.writeResultsToPostgreSQL();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            demo.spark.stop();
        }
    }
}
