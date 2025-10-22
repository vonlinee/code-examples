package demo.dataset;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import spark.SparkApplication;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.apache.spark.sql.functions.col;

public class NoBroadcastVariableDemo extends SparkApplication {
  @Override
  public void start(SparkSession spark) {
    // 模拟用户数据（大表）
    Dataset<Row> users = spark.range(1, 100)  // 100条用户记录
      .withColumn("user_id", col("id"))
      .withColumn("country_code", (col("id").mod(5)).cast("int"))
      .drop("id");

    System.out.println("=== 原始用户数据 ===");
    users.show();

    // 国家代码字典（小表）
    Map<Integer, String> countryMap = new HashMap<>();
    countryMap.put(0, "中国");
    countryMap.put(1, "美国");
    countryMap.put(2, "日本");
    countryMap.put(3, "英国");
    countryMap.put(4, "德国");

    // 方法2：直接join对比（不使用广播）
    System.out.println("=== 不使用广播变量（直接join）===");

    // 创建国家代码DF
    Dataset<Row> countriesDF = spark.createDataFrame(
      Arrays.asList(
        RowFactory.create(0, "中国"),
        RowFactory.create(1, "美国"),
        RowFactory.create(2, "日本"),
        RowFactory.create(3, "英国"),
        RowFactory.create(4, "德国")
      ),
      DataTypes.createStructType(Arrays.asList(
        DataTypes.createStructField("code", DataTypes.IntegerType, false),
        DataTypes.createStructField("name", DataTypes.StringType, false)
      ))
    );

    Dataset<Row> result2 = users
      .join(countriesDF, users.col("country_code").equalTo(countriesDF.col("code")))
      .select("user_id", "country_code", "name")
      .orderBy("user_id");

    result2.show();

    System.out.println("=== 普通Join执行计划 ===");
    result2.explain();

    spark.stop();
  }
}
