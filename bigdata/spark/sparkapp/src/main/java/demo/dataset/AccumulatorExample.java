package demo.dataset;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.catalyst.encoders.ExpressionEncoder;
import org.apache.spark.util.CollectionAccumulator;
import org.apache.spark.util.LongAccumulator;
import spark.SparkApplication;

import java.io.Serializable;
import java.util.*;

public class AccumulatorExample extends SparkApplication {
  @Override
  public void start(SparkSession spark) {
    // 创建多个累加器用于业务指标统计
    LongAccumulator totalUsersAcc = spark.sparkContext().longAccumulator("total_users");
    LongAccumulator blacklistedActionsAcc = spark.sparkContext().longAccumulator("blacklisted_actions");
    LongAccumulator vipPurchasesAcc = spark.sparkContext().longAccumulator("vip_purchases");
    LongAccumulator highRiskActionsAcc = spark.sparkContext().longAccumulator("high_risk_actions");

    CollectionAccumulator<String> actionStatsAcc = spark.sparkContext().collectionAccumulator("action_stats");

    // 业务规则
    Set<String> blacklistedUsers = new HashSet<>(Arrays.asList("user123", "user456"));
    Set<String> highRiskRegions = new HashSet<>(Collections.singletonList("region_A"));
    Set<String> vipUsers = new HashSet<>(Collections.singletonList("vip001"));

    Map<String, String> productCategories = new HashMap<>();
    productCategories.put("cat001", "电子产品");
    productCategories.put("cat002", "服装");

    BusinessRules businessRules = new BusinessRules(
      blacklistedUsers, highRiskRegions, vipUsers, productCategories);

    Broadcast<BusinessRules> rulesBroadcast = spark.sparkContext().broadcast(
      businessRules, scala.reflect.ClassTag$.MODULE$.apply(BusinessRules.class));

    // 模拟用户行为数据
    Dataset<Row> userActions = spark.createDataset(
      Arrays.asList(
        new UserAction("user123", "view", "region_A"),
        new UserAction("user_normal", "purchase", "region_B"),
        new UserAction("vip001", "purchase", "region_C"),
        new UserAction("user456", "click", "region_B"),
        new UserAction("user_normal", "view", "region_A")
      ),
      Encoders.bean(UserAction.class)
    ).toDF();

    // 处理数据并更新累加器
    Dataset<Row> processedData = userActions.map(
      (MapFunction<Row, Row>) row -> {
        String userId = row.getAs("userId");
        String action = row.getAs("action");
        String region = row.getAs("region");
        BusinessRules rules = rulesBroadcast.value();

        // 更新累加器
        totalUsersAcc.add(1);
        actionStatsAcc.add("action_" + action);

        if (rules.getBlacklistedUsers().contains(userId)) {
          blacklistedActionsAcc.add(1);
        }

        if (rules.getVipUsers().contains(userId) && "purchase".equals(action)) {
          vipPurchasesAcc.add(1);
        }

        if (rules.getHighRiskRegions().contains(region)) {
          highRiskActionsAcc.add(1);
        }

        return row;
      },
      ExpressionEncoder.javaBean(Row.class)
    );

    // 触发计算
    processedData.count();

    // 输出累加器结果
    System.out.println("=== 业务指标统计 ===");
    System.out.println("总用户行为数: " + totalUsersAcc.value());
    System.out.println("黑名单用户行为数: " + blacklistedActionsAcc.value());
    System.out.println("VIP用户购买数: " + vipPurchasesAcc.value());
    System.out.println("高风险地区行为数: " + highRiskActionsAcc.value());

    // 统计行为类型
    Map<String, Long> actionCounts = new HashMap<>();
    for (String action : actionStatsAcc.value()) {
      actionCounts.put(action, actionCounts.getOrDefault(action, 0L) + 1);
    }

    System.out.println("行为类型统计:");
    for (Map.Entry<String, Long> entry : actionCounts.entrySet()) {
      System.out.println("  " + entry.getKey() + ": " + entry.getValue());
    }
  }
}

@Data
@AllArgsConstructor
class UserAction implements Serializable {
  private String userId;
  private String action;
  private String region;
}
