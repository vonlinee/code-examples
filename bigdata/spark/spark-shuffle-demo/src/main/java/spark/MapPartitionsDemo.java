package spark;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.util.CollectionsUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CoalesceByDemo1 {

  public static void main(String[] args) throws IOException {
    final String hdfs = "hdfs://192.168.65.130:9000";
    SparkConf conf = new SparkConf();
    conf.setMaster("local[*]");
    conf.setAppName("CoalesceByDemo1");
    conf.set("spark.eventLog.enabled", "true");
    conf.set("spark.eventLog.dir", hdfs + "/spark-logs");

    JavaSparkContext sc = new JavaSparkContext(conf);
    JavaRDD<Integer> rdd = sc.parallelize(IntStream.range(1, 10000).boxed().collect(Collectors.toList()), 10);

    System.out.println(rdd.partitions().size());

    JavaRDD<Integer> rdd1 = rdd.mapPartitions(new FlatMapFunction<Iterator<Integer>, Integer>() {
      @Override
      public Iterator<Integer> call(Iterator<Integer> it) throws Exception {
        List<Integer> list = new ArrayList<>();
        while (it.hasNext()) {
          list.add(it.next());
        }

        System.out.println(list.size());
        return it;
      }
    });

    System.out.println(rdd1.count());

//    JavaRDD<List<Integer>> glom = rdd1.glom();


    sc.close();
  }
}