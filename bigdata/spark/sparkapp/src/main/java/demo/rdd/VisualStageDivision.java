package demo.rdd;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.scheduler.*;
import scala.Tuple2;
import scala.collection.JavaConverters;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class VisualStageDivision {

  private static final CountDownLatch latch = new CountDownLatch(3);

  public static class DetailedListener extends SparkListener {
    @Override
    public void onJobStart(SparkListenerJobStart jobStart) {
      System.out.println("🔹 Job " + jobStart.jobId() + " 开始");
      System.out.println("   阶段数量: " + jobStart.stageIds().size());

      // 修复：正确转换 Scala Seq
      List<Object> stageIds = JavaConverters.seqAsJavaListConverter(jobStart.stageIds()).asJava();
      System.out.println("   阶段IDs: " + stageIds);
    }

    @Override
    public void onStageSubmitted(SparkListenerStageSubmitted stageSubmitted) {
      StageInfo stageInfo = stageSubmitted.stageInfo();
      System.out.println("\n📦 Stage " + stageInfo.stageId() + " 提交");
      System.out.println("   Stage 名称: " + stageInfo.name());
      System.out.println("   任务数量: " + stageInfo.numTasks());

      // 修复：正确转换父阶段 IDs
      List<Object> parentIds = JavaConverters.seqAsJavaListConverter(stageInfo.parentIds()).asJava();
      System.out.println("   父阶段: " + parentIds);

      // 分析依赖类型
      if (parentIds.isEmpty()) {
        System.out.println("   🔗 依赖类型: 无依赖 (初始阶段)");
      } else if (parentIds.size() == 1) {
        System.out.println("   🔗 依赖类型: 窄依赖");
      } else {
        System.out.println("   🔗 依赖类型: 宽依赖 (Shuffle)");
      }
    }

    @Override
    public void onStageCompleted(SparkListenerStageCompleted stageCompleted) {
      System.out.println("✅ Stage " + stageCompleted.stageInfo().stageId() + " 完成");
      latch.countDown();
    }
  }

  public static void main(String[] args) throws InterruptedException {
    SparkConf conf = new SparkConf()
      .setAppName("Visual Stage Division")
      .setMaster("local[2]")
      .set("spark.sql.adaptive.enabled", "false"); // 关闭自适应查询，便于观察

    JavaSparkContext sc = new JavaSparkContext(conf);
    sc.sc().addSparkListener(new DetailedListener());

    System.out.println("🎯 开始演示 RDD Stage 划分...\n");

    // 模拟复杂的数据处理流程
    List<Integer> dataList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
    JavaRDD<Integer> data = sc.parallelize(dataList, 4);

    System.out.println("初始数据分区数: " + data.getNumPartitions());

    // 操作链1: 窄依赖
    JavaRDD<Integer> processed1 = data
      .map(x -> x * 2)              // 窄依赖
      .filter(x -> x > 10)          // 窄依赖
      .map(x -> x + 1);             // 窄依赖

    // 操作链2: 包含宽依赖
    JavaPairRDD<Integer, Integer> pairRDD = processed1
      .mapToPair(x -> new Tuple2<>(x % 3, x));  // 窄依赖

    JavaPairRDD<Integer, Integer> reducedRDD = pairRDD
      .reduceByKey(Integer::sum);            // 宽依赖 - Stage 边界

    JavaRDD<String> processed2 = reducedRDD
      .map(tuple -> "Key_" + tuple._1() + ": " + tuple._2()); // 窄依赖

    // 操作链3: 另一个宽依赖
    JavaRDD<String> finalResult = processed2
      .map(String::toUpperCase)                // 窄依赖
      .repartition(2)                         // 宽依赖 - Stage 边界
      .filter(s -> s.contains("KEY_1"));      // 窄依赖

    System.out.println("\n🚀 触发行动操作...");
    List<String> results = finalResult.collect();

    System.out.println("\n📊 最终结果: " + results);

    // 等待所有阶段完成
    latch.await();

    System.out.println("\n🎉 执行完成！");
    System.out.println("\n总结:");
    System.out.println("1. data -> map -> filter -> map: 窄依赖，一个 Stage");
    System.out.println("2. mapToPair -> reduceByKey: reduceByKey 产生宽依赖，新的 Stage");
    System.out.println("3. map -> repartition: repartition 产生宽依赖，新的 Stage");
    System.out.println("4. filter -> collect: 窄依赖，同一个 Stage");

    sc.stop();
  }
}
