package spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MapPartitionsDemo {

  public static void main(String[] args) throws IOException {
    final String hdfs = "hdfs://192.168.65.130:9000";
    SparkConf conf = new SparkConf();
    conf.setMaster("local[*]");
    conf.setAppName(MapPartitionsDemo.class.getName());
    conf.set("spark.eventLog.enabled", "true");
    conf.set("spark.eventLog.dir", hdfs + "/spark-logs");

    JavaSparkContext sc = new JavaSparkContext(conf);
    JavaRDD<Integer> rdd = sc.parallelize(IntStream.range(0, 10000).boxed().collect(Collectors.toList()), 10);
    System.out.println("count of partitions = " + rdd.partitions().size());
    JavaRDD<Integer> rdd1 = rdd.mapPartitions(new FlatMapFunction<Iterator<Integer>, Integer>() {
      @Override
      public Iterator<Integer> call(Iterator<Integer> it) throws Exception {
        List<Integer> list = new ArrayList<>();
        while (it.hasNext()) {
          list.add(it.next());
        }
        System.out.println(list.size());  // 1000
        return it;
      }
    }, true);
    System.out.println(rdd1.count()); // 0

    sc.close();
  }
}