package spark;

import org.apache.spark.HashPartitioner;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.VoidFunction;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GlomDemo {

  public static void main(String[] args) throws IOException {
    final String hdfs = "hdfs://192.168.65.130:9000";
    SparkConf conf = new SparkConf();
    conf.setMaster("local[*]");
    conf.setAppName(GlomDemo.class.getName());
    conf.set("spark.eventLog.enabled", "true");
    conf.set("spark.eventLog.dir", hdfs + "/spark-logs");

    JavaSparkContext sc = new JavaSparkContext(conf);
    List<Integer> list = IntStream.range(1, 10000).boxed().collect(Collectors.toList());
    JavaRDD<Integer> rdd = sc.parallelize(list, 10);

    HashPartitioner hashPartitioner = new HashPartitioner(0);

    JavaRDD<List<Integer>> rdd1 = rdd.glom();
    rdd1.foreach(new VoidFunction<List<Integer>>() {
      @Override
      public void call(List<Integer> integers) throws Exception {
        System.out.println("partition size = " + integers.size()); // 100
      }
    });
    sc.close();
  }
}