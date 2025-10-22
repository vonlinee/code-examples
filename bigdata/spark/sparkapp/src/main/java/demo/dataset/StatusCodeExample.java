package demo.dataset;

import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.types.DataTypes;
import spark.SparkApplication;

import java.util.HashMap;
import java.util.Map;

import static org.apache.spark.sql.functions.*;

public class StatusCodeExample extends SparkApplication {
  @Override
  public void start(SparkSession spark) {
    // 模拟订单状态日志
    Dataset<Row> orderLogs = spark.range(1, 50)
      .select(
        col("id").as("order_id"),
        (rand().multiply(5)).cast("int").as("status_code"),
        current_timestamp().as("log_time")
      );

    // 状态码映射字典
    Map<Integer, String> statusMap = new HashMap<>();
    statusMap.put(0, "待支付");
    statusMap.put(1, "已支付");
    statusMap.put(2, "已发货");
    statusMap.put(3, "已完成");
    statusMap.put(4, "已取消");

    // 广播状态字典
    Broadcast<Map<Integer, String>> broadcastStatus =
      spark.sparkContext().broadcast(statusMap, scala.reflect.ClassTag$.MODULE$.apply(Map.class));
    // 使用广播变量转换状态码 UDF
    spark.udf().register("getStatusName",
      (Integer code) -> {
        String name = broadcastStatus.value().get(code);
        return name != null ? name : "未知状态";
      }, DataTypes.StringType);
    Dataset<Row> enrichedLogs = orderLogs
      .withColumn("status_name", functions.callUDF("getStatusName", col("status_code")))
      .orderBy("order_id");

    // 不使用广播变量

    System.out.println("=== 使用广播变量转换后的日志 ===");
    enrichedLogs.show();

    // 显示执行计划
    enrichedLogs.explain();

    // 统计各状态订单数量
    System.out.println("=== 状态统计 ===");
    enrichedLogs
      .groupBy("status_code", "status_name")
      .count()
      .orderBy("status_code")
      .show();
  }
}
