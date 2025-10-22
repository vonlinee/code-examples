package demo.rdd;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;

public class SparkWithoutBroadcast {

  public static void main(String[] args) {
    SparkSession spark = SparkSession.builder()
      .appName("SparkWithoutBroadcast")
      .master("local[*]")
      .config("spark.sql.adaptive.enabled", "true")
      .config("spark.sql.adaptive.coalescePartitions.enabled", "true")
      .config("spark.sql.autoBroadcastJoinThreshold", "-1")  // 禁用自动广播
      .getOrCreate();

    long startTime = System.currentTimeMillis();

    try {
      // 读取数据
      Dataset<Row> transactions = spark.read().parquet("data/transactions");
      Dataset<Row> users = spark.read().parquet("data/users");

      System.out.println("=== 不使用 Broadcast 变量版本 ===");
      System.out.println("交易数据分区数: " + transactions.rdd().getNumPartitions());
      System.out.println("用户数据分区数: " + users.rdd().getNumPartitions());

      // 直接进行join操作（会产生shuffle）
      Dataset<Row> joined = transactions
        .filter("amount > 100")
        .join(users, "user_id");

      System.out.println("Join后的数据分区数: " + joined.rdd().getNumPartitions());

      // 执行聚合操作
      Dataset<Row> aggregated = joined
        .groupBy("city", "level", "category")
        .agg(
          functions.sum("amount").as("total_amount"),
          functions.avg("amount").as("avg_amount"),
          functions.count("*").as("transaction_count")
        )
        .filter("total_amount > 10000")
        .orderBy(functions.desc("total_amount"));

      // 触发执行
      long count = aggregated.count();
      System.out.println("结果数量: " + count);

      // 显示部分结果
      aggregated.show(20, false);

      long endTime = System.currentTimeMillis();
      System.out.println("总执行时间: " + (endTime - startTime) + " ms");

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      spark.stop();
    }
  }
}
