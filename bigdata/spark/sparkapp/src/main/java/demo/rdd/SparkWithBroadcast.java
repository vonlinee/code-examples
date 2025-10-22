package demo.rdd;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SparkWithBroadcast {

  public static void main(String[] args) {
    SparkSession spark = SparkSession.builder()
      .appName("SparkWithBroadcast")
      .master("local[*]")  // 测试用local，生产环境用yarn
      .config("spark.sql.adaptive.enabled", "true")
      .config("spark.sql.adaptive.coalescePartitions.enabled", "true")
      .config("spark.sql.autoBroadcastJoinThreshold", "10485760")
      .getOrCreate();

    JavaSparkContext jsc = JavaSparkContext.fromSparkContext(spark.sparkContext());

    long startTime = System.currentTimeMillis();
    try {
      // 读取数据
      Dataset<Row> transactions = spark.read().parquet("data/transactions");
      Dataset<Row> users = spark.read().parquet("data/users");

      System.out.println("=== 使用 Broadcast 变量版本 ===");
      System.out.println("交易数据分区数: " + transactions.rdd().getNumPartitions());
      System.out.println("用户数据分区数: " + users.rdd().getNumPartitions());

      // 收集用户数据到Driver并广播
      List<Row> userList = users.collectAsList();
      Map<Integer, UserInfo> userMap = new HashMap<>();

      for (Row row : userList) {
        int userId = row.getInt(0);
        String userName = row.getString(1);
        int age = row.getInt(2);
        String city = row.getString(3);
        String level = row.getString(4);
        userMap.put(userId, new UserInfo(userName, age, city, level));
      }

      // 广播用户信息
      Broadcast<Map<Integer, UserInfo>> userBroadcast = jsc.broadcast(userMap);
      System.out.println("广播用户数据大小: " + userList.size() + " 条记录");

      // 使用mapPartitions处理数据
      JavaRDD<Row> resultRDD = transactions.toJavaRDD().mapPartitions(iterator -> {
        Map<Integer, UserInfo> localUserMap = userBroadcast.value();
        List<Row> resultList = new ArrayList<>();

        while (iterator.hasNext()) {
          Row transaction = iterator.next();
          int userId = transaction.getInt(1);
          UserInfo userInfo = localUserMap.get(userId);

          if (userInfo != null && transaction.getDouble(2) > 100) {
            resultList.add(RowFactory.create(
              transaction.getInt(0),
              transaction.getInt(1),
              transaction.getDouble(2),
              transaction.getString(3),
              transaction.getString(4),
              userInfo.getUserName(),
              userInfo.getAge(),
              userInfo.getCity(),
              userInfo.getLevel()
            ));
          }
        }
        return resultList.iterator();
      });

      // 定义schema
      StructType resultSchema = new StructType(new StructField[]{
        DataTypes.createStructField("transaction_id", DataTypes.IntegerType, false),
        DataTypes.createStructField("user_id", DataTypes.IntegerType, false),
        DataTypes.createStructField("amount", DataTypes.DoubleType, false),
        DataTypes.createStructField("category", DataTypes.StringType, false),
        DataTypes.createStructField("date", DataTypes.StringType, false),
        DataTypes.createStructField("user_name", DataTypes.StringType, false),
        DataTypes.createStructField("age", DataTypes.IntegerType, false),
        DataTypes.createStructField("city", DataTypes.StringType, false),
        DataTypes.createStructField("level", DataTypes.StringType, false)
      });

      Dataset<Row> result = spark.createDataFrame(resultRDD, resultSchema);

      // 执行聚合操作
      Dataset<Row> aggregated = result
        .groupBy("city", "level", "category")
        .agg(
          org.apache.spark.sql.functions.sum("amount").as("total_amount"),
          org.apache.spark.sql.functions.avg("amount").as("avg_amount"),
          org.apache.spark.sql.functions.count("*").as("transaction_count")
        )
        .filter("total_amount > 10000")
        .orderBy(org.apache.spark.sql.functions.desc("total_amount"));

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
