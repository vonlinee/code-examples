package demo.sparksql;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.expressions.Window;
import org.apache.spark.sql.expressions.WindowSpec;

import java.util.Properties;

import static org.apache.spark.sql.functions.*;

public class SimpleECommerceDemo {

  public static void main(String[] args) {
    SparkSession spark = SparkSession.builder()
      .appName("SimpleECommerceDemo")
      .master("local[*]")
      .getOrCreate();

    // PostgreSQL 连接配置
    String jdbcUrl = "jdbc:postgresql://localhost:5432/ecommerce";
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("user", "postgres");
    connectionProperties.setProperty("password", "password");
    connectionProperties.setProperty("driver", "org.postgresql.Driver");

    try {
      // 读取数据
      Dataset<Row> users = spark.read().jdbc(jdbcUrl, "users", connectionProperties);
      Dataset<Row> orders = spark.read().jdbc(jdbcUrl, "orders", connectionProperties);
      Dataset<Row> orderItems = spark.read().jdbc(jdbcUrl, "order_items", connectionProperties);
      Dataset<Row> products = spark.read().jdbc(jdbcUrl, "products", connectionProperties);

      // 示例1: 用户购买统计
      System.out.println("=== 用户购买统计 ===");
      Dataset<Row> userStats = users
        .join(orders, "user_id")
        .join(orderItems, "order_id")
        .filter(col("status").equalTo("completed"))
        .groupBy("user_id", "username")
        .agg(
          countDistinct("order_id").alias("order_count"),
          sum("quantity").alias("total_items"),
          sum("subtotal").alias("total_spent")
        )
        .orderBy(col("total_spent").desc());

      userStats.show(10);

      // 示例2: 商品销售排名
      System.out.println("=== 商品销售排名 ===");
      WindowSpec categoryWindow = Window
        .partitionBy("category")
        .orderBy(col("total_sales").desc());

      Dataset<Row> productRanking = products
        .join(orderItems, "product_id")
        .join(orders, "order_id")
        .filter(col("status").equalTo("completed"))
        .groupBy("product_id", "product_name", "category")
        .agg(
          sum("quantity").alias("total_quantity"),
          sum("subtotal").alias("total_sales")
        )
        .withColumn("category_rank", rank().over(categoryWindow))
        .filter(col("category_rank").leq(3))
        .orderBy("category", "category_rank");

      productRanking.show(20);

      // 示例3: 月度销售趋势
      System.out.println("=== 月度销售趋势 ===");
      Dataset<Row> monthlyTrend = orders
        .join(orderItems, "order_id")
        .filter(col("status").equalTo("completed"))
        .withColumn("order_month", date_format(col("order_date"), "yyyy-MM"))
        .groupBy("order_month")
        .agg(
          sum("subtotal").alias("monthly_sales"),
          countDistinct("order_id").alias("order_count")
        )
        .withColumn("prev_month_sales",
          lag("monthly_sales", 1).over(Window.orderBy("order_month")))
        .withColumn("growth_rate",
          when(col("prev_month_sales").isNotNull(),
            col("monthly_sales").minus(col("prev_month_sales"))
              .divide(col("prev_month_sales"))
              .multiply(100))
            .otherwise(null))
        .orderBy("order_month");

      monthlyTrend.show();

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      spark.stop();
    }
  }
}
