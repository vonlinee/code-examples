package demo.rdd;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;
import spark.JavaSparkApplication;

import java.util.Arrays;
import java.util.List;

public class SimpleRDDExample extends JavaSparkApplication {

  @Override
  public void start(JavaSparkContext sc) {
    // 创建数据
    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    JavaRDD<Integer> numbersRDD = sc.parallelize(numbers, 2);

    System.out.println("初始分区数: " + numbersRDD.getNumPartitions());

    // 窄依赖操作链 - 同一个 Stage
    JavaRDD<Integer> transformedRDD = numbersRDD
      .map(x -> x * 2)          // Stage 1
      .filter(x -> x > 5)       // Stage 1
      .map(x -> x + 1);         // Stage 1

    List<Integer> stage1Result = transformedRDD.collect();
    System.out.println("Stage 1 结果: " + stage1Result);

    // 宽依赖操作 - 新的 Stage
    JavaPairRDD<Integer, Integer> pairRDD = transformedRDD
      .mapToPair(x -> new Tuple2<>(x % 3, x));  // Stage 1

    JavaPairRDD<Integer, Integer> reducedRDD = pairRDD
      .reduceByKey(Integer::sum);            // Stage 2 - 宽依赖

    List<Tuple2<Integer, Integer>> stage2Result = reducedRDD.collect();
    System.out.println("Stage 2 结果: " + stage2Result);

    System.out.println("\nStage 划分说明:");
    System.out.println("- map -> filter -> map: 都是窄依赖，在同一个 Stage");
    System.out.println("- reduceByKey: 宽依赖，产生新的 Stage");

  }
}
