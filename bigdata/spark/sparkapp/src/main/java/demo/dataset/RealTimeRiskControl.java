package demo.dataset;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.*;
import org.apache.spark.util.LongAccumulator;
import spark.SparkApplication;

import java.io.Serializable;
import java.util.*;

public class RealTimeRiskControl extends SparkApplication {
  @Override
  public void start(SparkSession spark) {

    // 风险规则配置
    Set<String> suspiciousPatterns = new HashSet<>(Arrays.asList(
      "midnight_purchase", "multiple_quick_purchases"));

    RiskRules riskRules = new RiskRules(10000.0, suspiciousPatterns, 10);

    Broadcast<RiskRules> riskRulesBroadcast = spark.sparkContext().broadcast(
      riskRules, scala.reflect.ClassTag$.MODULE$.apply(RiskRules.class));

    // 累加器用于风险统计
    LongAccumulator highAmountTransactionsAcc = spark.sparkContext().longAccumulator("high_amount_transactions");
    LongAccumulator suspiciousPatternsAcc = spark.sparkContext().longAccumulator("suspicious_patterns");
    LongAccumulator totalProcessedAcc = spark.sparkContext().longAccumulator("total_processed");

    // 模拟交易数据
    Dataset<Row> transactions = spark.createDataset(
      Arrays.asList(
        new Transaction("user001", 500.0, "normal", "2024-01-01 10:00:00"),
        new Transaction("user002", 15000.0, "normal", "2024-01-01 10:01:00"),
        new Transaction("user003", 200.0, "midnight_purchase", "2024-01-01 10:02:00"),
        new Transaction("user004", 3000.0, "normal", "2024-01-01 10:03:00"),
        new Transaction("user005", 8000.0, "multiple_quick_purchases", "2024-01-01 10:04:00")
      ),
      Encoders.bean(Transaction.class)
    ).toDF();

    // 风险检测处理
    Dataset<RiskAnalysisResult> riskAnalysis = transactions.map(
      (MapFunction<Row, RiskAnalysisResult>) row -> {
        totalProcessedAcc.add(1);

        String userId = row.getAs("userId");
        double amount = row.getAs("amount");
        String pattern = row.getAs("pattern");
        RiskRules rules = riskRulesBroadcast.value();

        String riskLevel = "low";
        List<String> riskReasons = new ArrayList<>();

        // 金额风险检测
        if (amount > rules.getMaxAmountPerTransaction()) {
          highAmountTransactionsAcc.add(1);
          riskLevel = "high";
          riskReasons.add("金额超过阈值: " + amount);
        }

        // 模式风险检测
        if (rules.getSuspiciousPatterns().contains(pattern)) {
          suspiciousPatternsAcc.add(1);
          riskLevel = riskLevel.equals("low") ? "medium" : riskLevel;
          riskReasons.add("可疑模式: " + pattern);
        }

        return new RiskAnalysisResult(userId, amount, pattern, riskLevel,
          String.join("; ", riskReasons));
      },
      Encoders.bean(RiskAnalysisResult.class)
    );

    // 触发计算
    riskAnalysis.count();

    System.out.println("=== 风险分析结果 ===");
    riskAnalysis.show();

    System.out.println("=== 风险统计 ===");
    System.out.println("处理总交易数: " + totalProcessedAcc.value());
    System.out.println("高金额交易数: " + highAmountTransactionsAcc.value());
    System.out.println("可疑模式交易数: " + suspiciousPatternsAcc.value());

    // 高风险交易明细
    System.out.println("=== 高风险交易明细 ===");
    riskAnalysis.filter(functions.col("riskLevel").equalTo("high")).show();


    // 使用sql的方式
    useSql(transactions, riskRules, highAmountTransactionsAcc, totalProcessedAcc, suspiciousPatternsAcc);
  }

  private void useSql(Dataset<Row> transactions, RiskRules riskRules, LongAccumulator highAmountTransactionsAcc, LongAccumulator totalProcessedAcc, LongAccumulator suspiciousPatternsAcc) {
    // 使用SQL表达式的方式（替代map操作）
    Dataset<Row> riskAnalysis = transactions
      .withColumn("risk_level",
        functions.when(functions.col("amount").gt(riskRules.getMaxAmountPerTransaction()), "high")
          .when(functions.col("pattern").isin(riskRules.getSuspiciousPatterns().toArray(new String[0])), "medium")
          .otherwise("low"))
      .withColumn("risk_reasons",
        functions.concat(
          functions.when(functions.col("amount").gt(riskRules.getMaxAmountPerTransaction()),
            functions.lit("金额超过阈值; ")).otherwise(functions.lit("")),
          functions.when(functions.col("pattern").isin(riskRules.getSuspiciousPatterns().toArray(new String[0])),
            functions.lit("可疑模式; ")).otherwise(functions.lit(""))
        ));

    // 然后使用foreach更新累加器
    riskAnalysis.foreach(row -> {
      totalProcessedAcc.add(1);
      if (row.getAs("risk_level").equals("high")) {
        highAmountTransactionsAcc.add(1);
      }
      if (row.<String>getAs("risk_reasons").contains("可疑模式")) {
        suspiciousPatternsAcc.add(1);
      }
    });
  }
}

// 交易数据类
@Data
@AllArgsConstructor
class Transaction implements Serializable {
  private String userId;
  private double amount;
  private String pattern;
  private String timestamp;
}

// 风险分析结果类
@Data
@AllArgsConstructor
class RiskAnalysisResult implements Serializable {
  private String userId;
  private double amount;
  private String pattern;
  private String riskLevel;
  private String riskReasons;
}

// 风险规则类
@Data
@AllArgsConstructor
class RiskRules implements Serializable {
  private double maxAmountPerTransaction;
  private Set<String> suspiciousPatterns;
  private int highFrequencyThreshold;
}
