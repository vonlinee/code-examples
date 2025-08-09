package spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SparkShuffleDemo {

  public static void main(String[] args) throws IOException {
    final String hdfs = "hdfs://192.168.65.130:9000";
    SparkConf conf = new SparkConf();
    // conf.setMaster("local[*]");
    conf.setAppName("SparkShuffleDemo");
    conf.set("spark.eventLog.enabled", "true");
    conf.set("spark.eventLog.dir", hdfs + "/spark-logs");

    JavaSparkContext sc = new JavaSparkContext(conf);
    JavaRDD<String> tranFile = sc.textFile(hdfs + "/ch4/ch04_data_transactions.txt");
    JavaRDD<String[]> tranData = tranFile.map((String line) -> line.split("#"));
    // 按客户ID分组
    JavaPairRDD<Integer, String[]> transByCust = tranData.mapToPair((String[] tran) -> new Tuple2<>(Integer.valueOf(tran[2]), tran));

    JavaPairRDD<Integer, List<String>> prods = transByCust.aggregateByKey(new ArrayList<>(), (List<String> prods2, String[] tran) -> {
      prods2.add(tran[3]);
      return prods2;
    }, (List<String> prods1, List<String> prods2) -> {
      prods1.addAll(prods2);
      return prods1;
    });
    System.out.println("Products per customer: " + prods.collect());
    sc.close();
  }
}