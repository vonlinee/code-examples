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

public class RDDStageTaskExample {

  // 自定义监听器来观察 Stage 和 Task 划分
  public static class StageTaskListener extends SparkListener {
    @Override
    public void onStageSubmitted(SparkListenerStageSubmitted stageSubmitted) {
      StageInfo stageInfo = stageSubmitted.stageInfo();
      System.out.println("=== Stage " + stageInfo.stageId() + " Submitted ===");
      System.out.println("Stage Name: " + stageInfo.name());
      System.out.println("Number of Tasks: " + stageInfo.numTasks());

      // 修复：正确转换 Scala Seq 到 Java List
      List<Object> parentIds = JavaConverters.seqAsJavaListConverter(stageInfo.parentIds()).asJava();
      System.out.println("Parent Stage IDs: " + parentIds);
      System.out.println("----------------------------------------");
    }

    @Override
    public void onTaskStart(SparkListenerTaskStart taskStart) {
      System.out.println("Task " + taskStart.taskInfo().taskId() + " started on executor " +
                         taskStart.taskInfo().executorId() + " (Stage " + taskStart.stageId() + ")");
    }

    @Override
    public void onTaskEnd(SparkListenerTaskEnd taskEnd) {
      System.out.println("Task " + taskEnd.taskInfo().taskId() + " completed with status: " +
                         taskEnd.taskInfo().status() + " (Stage " + taskEnd.stageId() + ")");
    }

    @Override
    public void onStageCompleted(SparkListenerStageCompleted stageCompleted) {
      System.out.println("=== Stage " + stageCompleted.stageInfo().stageId() + " Completed ===");
      System.out.println("----------------------------------------\n");
    }
  }

  public static void main(String[] args) throws Exception {
    // Spark 配置
    SparkConf conf = new SparkConf()
      .setAppName("RDD Stage Task Example")
      .setMaster("local[2]")  // 使用2个核心，便于观察并行执行
      .set("spark.default.parallelism", "4");  // 设置默认并行度

    JavaSparkContext sc = new JavaSparkContext(conf);

    // 添加监听器
    sc.sc().addSparkListener(new StageTaskListener());

    try {
      System.out.println("开始执行 RDD 操作...\n");

      // 1. 创建初始 RDD
      List<Integer> data = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
      JavaRDD<Integer> initialRDD = sc.parallelize(data, 3);  // 3个分区

      System.out.println("初始 RDD 分区数: " + initialRDD.getNumPartitions());

      // 2. 窄依赖操作 - 不会产生新的 Stage
      JavaRDD<Integer> mappedRDD = initialRDD.map(x -> {
        System.out.println("Mapping: " + x);
        return x * 2;
      });

      // 3. 窄依赖操作 - 仍在同一个 Stage
      JavaRDD<Integer> filteredRDD = mappedRDD.filter(x -> {
        System.out.println("Filtering: " + x);
        return x > 10;
      });

      // 4. 触发行动操作 - 执行计算
      System.out.println("\n执行 collect() 行动操作:");
      List<Integer> result1 = filteredRDD.collect();
      System.out.println("结果: " + result1);

      // 5. 宽依赖操作 - 会产生新的 Stage
      System.out.println("\n=== 测试宽依赖操作 ===");

      JavaRDD<Integer> rdd1 = sc.parallelize(Arrays.asList(1, 2, 3, 4, 5), 2);
      JavaRDD<Integer> rdd2 = sc.parallelize(Arrays.asList(6, 7, 8, 9, 10), 2);

      // union 是窄依赖
      JavaRDD<Integer> unionRDD = rdd1.union(rdd2);

      // 修复：groupBy 操作需要正确的类型
      JavaPairRDD<String, Iterable<String>> groupedRDD = unionRDD
        .mapToPair(x -> {
          String key = (x % 2 == 0) ? "even" : "odd";
          return new Tuple2<>(key, key + "_" + x);
        })
        .groupByKey();

      long count = groupedRDD.count();

      System.out.println("GroupBy 结果计数: " + count);

    } finally {
      sc.stop();
    }
  }
}
