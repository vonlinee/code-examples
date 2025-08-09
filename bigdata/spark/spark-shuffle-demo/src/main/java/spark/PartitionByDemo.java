package spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SparkShuffleDemo2 {

  public static void main(String[] args) throws IOException {
    final String hdfs = "hdfs://192.168.65.130:9000";
    SparkConf conf = new SparkConf();
     conf.setMaster("local[*]");
    conf.setAppName("SparkShuffleDemo");
    conf.set("spark.eventLog.enabled", "true");
    conf.set("spark.eventLog.dir", hdfs + "/spark-logs");

    JavaSparkContext sc = new JavaSparkContext(conf);
    JavaRDD<Integer> rdd = sc.parallelize(IntStream.range(1, 10000).boxed().collect(Collectors.toList()));
    // 第一部分：map 和 swap 操作
    long count1 = rdd
      .mapToPair((PairFunction<Integer, Integer, Integer>) x -> new Tuple2<>(x, x * x))
      .mapToPair(Tuple2::swap) // 交换元组
      .count(); // 计算数量
    System.out.println("Count after swap: " + count1);

    // 第二部分：reduceByKey 操作
    long count2 = rdd
      .mapToPair((PairFunction<Integer, Integer, Integer>) x -> new Tuple2<>(x, x * x))
      .reduceByKey((Function2<Integer, Integer, Integer>) Integer::sum)
      .count();
    System.out.println("Count after reduceByKey: " + count2);
    sc.close();
  }
}