package demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import spark.SparkApplication;

import java.io.Serializable;
import java.util.*;

/**
 * 基于电商用户行为分析场景
 * 业务场景：电商用户行为分析与实时规则匹配
 * 假设我们需要对用户行为进行实时分析，同时根据业务规则（如黑名单用户、高风险地区等）进行过滤和统计。
 */
public class BroadcastVariableExample extends SparkApplication {

  @Override
  public void start(SparkSession spark) {
    // 模拟业务规则数据
    Set<String> blacklistedUsers = new HashSet<>(Arrays.asList("user123", "user456", "user789"));
    Set<String> highRiskRegions = new HashSet<>(Arrays.asList("region_A", "region_C"));
    Set<String> vipUsers = new HashSet<>(Arrays.asList("vip001", "vip002", "vip003"));

    Map<String, String> productCategories = new HashMap<>();
    productCategories.put("cat001", "电子产品");
    productCategories.put("cat002", "服装");
    productCategories.put("cat003", "食品");
    productCategories.put("cat004", "家居");

    BusinessRules businessRules = new BusinessRules(
      blacklistedUsers, highRiskRegions, vipUsers, productCategories);

    // 创建广播变量
    Broadcast<BusinessRules> rulesBroadcast = spark.sparkContext().broadcast(
      businessRules, scala.reflect.ClassTag$.MODULE$.apply(BusinessRules.class));

    // 模拟用户行为数据
    Dataset<Row> userBehaviorData = spark.createDataset(
      Arrays.asList(
        new UserBehavior("user123", "view", "product001", "cat001", "region_A", "2024-01-01 10:00:00"),
        new UserBehavior("user_normal", "purchase", "product002", "cat002", "region_B", "2024-01-01 10:01:00"),
        new UserBehavior("vip001", "purchase", "product003", "cat001", "region_C", "2024-01-01 10:02:00"),
        new UserBehavior("user456", "click", "product004", "cat003", "region_B", "2024-01-01 10:03:00"),
        new UserBehavior("user_normal2", "view", "product005", "cat004", "region_A", "2024-01-01 10:04:00")
      ),
      Encoders.bean(UserBehavior.class)
    ).toDF();

    // 使用广播变量进行数据过滤和增强
    Dataset<Row> processedData = userBehaviorData
      .filter((FilterFunction<Row>) row -> {
        BusinessRules rules = rulesBroadcast.value();
        return !rules.getBlacklistedUsers().contains(String.valueOf(row.getAs("userId")));
      })
      .withColumn("is_high_risk_region", functions.udf(
        (String region) -> {
          BusinessRules rules = rulesBroadcast.value();
          return rules.getHighRiskRegions().contains(region);
        }, DataTypes.BooleanType
      ).apply(functions.col("region")))
      .withColumn("is_vip_user", functions.udf(
        (String userId) -> {
          BusinessRules rules = rulesBroadcast.value();
          return rules.getVipUsers().contains(userId);
        }, DataTypes.BooleanType
      ).apply(functions.col("userId")))
      .withColumn("category_name", functions.udf(
        (String categoryId) -> {
          BusinessRules rules = rulesBroadcast.value();
          return rules.getProductCategories().getOrDefault(categoryId, "未知分类");
        }, DataTypes.StringType
      ).apply(functions.col("categoryId")));

    System.out.println("=== 处理后的用户行为数据 ===");
    processedData.show();

    // 统计各类别数据
    Dataset<Row> categoryStats = processedData
      .groupBy("category_name")
      .agg(
        functions.count("*").as("total_actions"),
        functions.sum(functions.when(functions.col("action").equalTo("purchase"), 1).otherwise(0)).as("purchase_count"),
        functions.sum(functions.when(functions.col("is_vip_user").equalTo(true), 1).otherwise(0)).as("vip_actions")
      );

    System.out.println("=== 分类统计 ===");
    categoryStats.show();
  }
}

/**
 * 定义业务规则类
 */
@Data
@AllArgsConstructor
class BusinessRules implements Serializable {
  private Set<String> blacklistedUsers;
  private Set<String> highRiskRegions;
  private Set<String> vipUsers;
  private Map<String, String> productCategories;
}

/**
 * 用户行为数据类
 */
@Data
@AllArgsConstructor
class UserBehavior implements Serializable {
  private String userId;
  private String action;
  private String productId;
  private String categoryId;
  private String region;
  private String timestamp;
}
