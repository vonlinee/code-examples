package spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CoalesceByDemo {

  public static void main(String[] args) throws IOException {
    final String hdfs = "hdfs://192.168.65.130:9000";
    SparkConf conf = new SparkConf();
    conf.setMaster("local[*]");
    conf.setAppName("SparkShuffleDemo");
    conf.set("spark.eventLog.enabled", "true");
    conf.set("spark.eventLog.dir", hdfs + "/spark-logs");

    JavaSparkContext sc = new JavaSparkContext(conf);
    JavaRDD<Integer> rdd = sc.parallelize(IntStream.range(1, 10000).boxed().collect(Collectors.toList()));
    JavaPairRDD<Integer, Integer> pairRDD = rdd.mapToPair((PairFunction<Integer, Integer, Integer>) x -> new Tuple2<>(x, x * x));
    JavaRDD<Tuple2<Integer, Integer>> rdd1 = pairRDD.map((Function<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>>) v -> new Tuple2<>(v._1 + v._2, v._1 * v._2));
    rdd1.coalesce(10);
    rdd1.map((Function<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>>) v -> new Tuple2<>(v._1 + v._2, v._1 * v._2));
    sc.close();
  }
}