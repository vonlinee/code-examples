package demo.dataset;

import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import scala.reflect.ClassTag$;
import spark.SparkApplication;

import java.util.HashMap;
import java.util.Map;

import static org.apache.spark.sql.functions.col;

public class BroadcastVariableDemo extends SparkApplication {
  @Override
  public void start(SparkSession spark) {
    // 模拟用户数据（大表）
    Dataset<Row> users = spark.range(1, 100)  // 100条用户记录
      .withColumn("user_id", col("id"))
      .withColumn("country_code", (col("id").mod(5)).cast("int"))
      .drop("id");
    // 国家代码字典（小表）
    Map<Integer, String> countryMap = new HashMap<>();
    countryMap.put(0, "中国");
    countryMap.put(1, "美国");
    countryMap.put(2, "日本");
    countryMap.put(3, "英国");
    countryMap.put(4, "德国");
    // 创建广播变量
    Broadcast<Map<Integer, String>> broadcastCountries =
      spark.sparkContext().broadcast(countryMap, ClassTag$.MODULE$.apply(Map.class));
    // 使用UDF + 广播变量
    spark.udf().register("getCountryName",
      (Integer code) -> broadcastCountries.value().get(code),
      org.apache.spark.sql.types.DataTypes.StringType);
    // 使用广播变量进行转换
    Dataset<Row> result1 = users
      .withColumn("country_name", functions.callUDF("getCountryName", col("country_code")))
      .orderBy("user_id");
    result1.show();
    // 显示执行计划
    result1.explain();
  }
}
