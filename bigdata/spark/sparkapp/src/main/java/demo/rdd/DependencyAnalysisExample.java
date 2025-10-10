package demo.rdd;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

public class DependencyAnalysisExample {

  public static void main(String[] args) {
    SparkConf conf = new SparkConf()
      .setAppName("Dependency Analysis")
      .setMaster("local[2]");

    JavaSparkContext sc = new JavaSparkContext(conf);

    try {
      // 创建基础 RDD
      List<String> textData = Arrays.asList(
        "apple banana cherry",
        "date elderberry fig",
        "grape honeydew apple",
        "banana cherry date"
      );
      JavaRDD<String> textRDD = sc.parallelize(textData, 2);

      System.out.println("=== 窄依赖操作链 ===");

      // 窄依赖操作链
      JavaRDD<String> wordsRDD = textRDD
        .flatMap(line -> Arrays.asList(line.split(" ")).iterator())
        .map(String::toLowerCase)
        .filter(word -> word.length() > 3);

      System.out.println("窄依赖操作后分区数: " + wordsRDD.getNumPartitions());

      // 触发计算
      List<String> words = wordsRDD.collect();
      System.out.println("单词结果: " + words);

      System.out.println("\n=== 宽依赖操作 ===");

      // 转换为 PairRDD 并进行宽依赖操作
      JavaPairRDD<String, Integer> wordPairs = wordsRDD
        .mapToPair(word -> new Tuple2<>(word, 1));

      // reduceByKey 是宽依赖操作，会产生 shuffle
      JavaPairRDD<String, Integer> wordCounts = wordPairs
        .reduceByKey((a, b) -> a + b);

      System.out.println("reduceByKey 后分区数: " + wordCounts.getNumPartitions());

      // 重新分区操作 - 显式宽依赖
      JavaPairRDD<String, Integer> repartitioned = wordCounts
        .repartition(4);

      System.out.println("重新分区后分区数: " + repartitioned.getNumPartitions());

      // 最终行动操作
      List<Tuple2<String, Integer>> results = repartitioned.collect();
      System.out.println("词频统计结果: " + results);

      System.out.println("\n=== Stage 划分总结 ===");
      System.out.println("1. textRDD -> flatMap -> map -> filter: 窄依赖，一个 Stage");
      System.out.println("2. reduceByKey: 宽依赖，产生新的 Stage");
      System.out.println("3. repartition: 宽依赖，产生新的 Stage");
      System.out.println("4. collect: 行动操作，触发执行");

    } finally {
      sc.stop();
    }
  }
}
