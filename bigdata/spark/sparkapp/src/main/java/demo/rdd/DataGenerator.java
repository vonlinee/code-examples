package demo.rdd;// DataGenerator.java

import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataGenerator {
  public static void main(String[] args) {
    SparkSession spark = SparkSession.builder()
      .appName("DataGenerator")
      .master("local[*]")
      .getOrCreate();

    // 生成用户交易数据（大表）
    List<Row> transactions = new ArrayList<>();
    Random random = new Random(42);

    // 生成 100 万条交易记录（测试用，实际可增加）
    for (int i = 0; i < 1000000; i++) {
      int userId = random.nextInt(10000);  // 1万个用户
      double amount = 10 + random.nextDouble() * 990;  // 10-1000元
      String category = "category_" + random.nextInt(100);  // 100个类别
      String date = String.format("2024-%02d-%02d",
        random.nextInt(12) + 1, random.nextInt(28) + 1);

      transactions.add(RowFactory.create(i, userId, amount, category, date));
    }

    StructType transactionSchema = new StructType(new StructField[]{
      DataTypes.createStructField("transaction_id", DataTypes.IntegerType, false),
      DataTypes.createStructField("user_id", DataTypes.IntegerType, false),
      DataTypes.createStructField("amount", DataTypes.DoubleType, false),
      DataTypes.createStructField("category", DataTypes.StringType, false),
      DataTypes.createStructField("date", DataTypes.StringType, false)
    });

    Dataset<Row> transactionDF = spark.createDataFrame(transactions, transactionSchema);
    transactionDF.repartition(8).write().mode(SaveMode.Overwrite).parquet("data/transactions");

    // 生成用户信息数据（小表）
    List<Row> users = new ArrayList<>();
    String[] cities = {"Beijing", "Shanghai", "Guangzhou", "Shenzhen", "Hangzhou", "Chengdu", "Wuhan", "Xi'an"};
    String[] levels = {"VIP", "Gold", "Silver", "Normal"};

    for (int i = 0; i < 10000; i++) {
      String city = cities[random.nextInt(cities.length)];
      String level = levels[random.nextInt(levels.length)];
      int age = 18 + random.nextInt(50);

      users.add(RowFactory.create(i, "user_" + i, age, city, level));
    }

    StructType userSchema = new StructType(new StructField[]{
      DataTypes.createStructField("user_id", DataTypes.IntegerType, false),
      DataTypes.createStructField("user_name", DataTypes.StringType, false),
      DataTypes.createStructField("age", DataTypes.IntegerType, false),
      DataTypes.createStructField("city", DataTypes.StringType, false),
      DataTypes.createStructField("level", DataTypes.StringType, false)
    });

    Dataset<Row> userDF = spark.createDataFrame(users, userSchema);
    userDF.write().mode(SaveMode.Overwrite).parquet("data/users");

    System.out.println("数据生成完成！");
    System.out.println("交易数据量: " + transactionDF.count());
    System.out.println("用户数据量: " + userDF.count());

    spark.stop();
  }
}
