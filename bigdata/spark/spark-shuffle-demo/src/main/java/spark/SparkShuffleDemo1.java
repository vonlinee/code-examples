package spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SparkShuffleDemo {

  public static void main(String[] args) throws IOException {
    final String hdfs = "hdfs://192.168.65.130:9000";

    SparkConf conf = new SparkConf();
    conf.setMaster("local[*]");
    conf.setAppName("SparkShuffleDemo");
    conf.set("spark.eventLog.enabled", "true");
    conf.set("spark.eventLog.dir", "hdfs://192.168.65.130:9000/spark-logs");

    JavaSparkContext sc = new JavaSparkContext(conf);
    JavaRDD<String> tranFile = sc.textFile(hdfs + "/ch4/ch04_data_transactions.txt");
    JavaRDD<String[]> tranData = tranFile.map((String line) -> line.split("#"));
    JavaPairRDD<Integer, String[]> transByCust = tranData.mapToPair((String[] tran) -> new Tuple2<>(Integer.valueOf(tran[2]), tran));

    long distinctKeysCount = transByCust.keys().distinct().count();
    System.out.println("Distinct keys count: " + distinctKeysCount);

    Map<Integer, Long> transCountByCust = transByCust.countByKey();

    long sumOfValues = 0;
    for (Long o : transCountByCust.values())
      sumOfValues += o;
    System.out.println("Sum of counts by key: " + sumOfValues);

    Map.Entry<Integer, Long> maxPurch = null;

    for (Map.Entry<Integer, Long> entry : transCountByCust.entrySet()) {
      if (maxPurch == null || entry.getValue() > maxPurch.getValue()) {
        maxPurch = entry;
      }
    }
    System.out.println();
    System.out.println("Max purchases (" + maxPurch.getValue() + ") were made by " + maxPurch.getKey());

    List<String[]> complTrans = new ArrayList<>();
    String[] newTrans = {"2015-03-30", "11:59 PM", "53", "4", "1", "0.00"};
    complTrans.add(newTrans);

    List<String[]> cust53Trans = transByCust.lookup(53);
    System.out.println();
    System.out.println("Customer 53's transactions: ");
    for (String[] tran : cust53Trans) {
      System.out.println(String.join(", ", tran));
    }

    transByCust = transByCust.mapValues((String[] tran) -> {
      if (Integer.parseInt(tran[3]) == 25 && Double.parseDouble(tran[4]) > 1)
        tran[5] = String.valueOf(Double.parseDouble(tran[5]) * 0.95);
      return tran;
    });

    transByCust = transByCust.flatMapValues((String[] tran) -> {
      ArrayList<String[]> newlist = new ArrayList<>();
      newlist.add(tran);
      if (Integer.parseInt(tran[3]) == 81 && Integer.parseInt(tran[4]) >= 5) {
        String[] cloned = Arrays.copyOf(tran, tran.length);
        cloned[5] = "0.00";
        cloned[3] = "70";
        cloned[4] = "1";
        newlist.add(cloned);
      }
      return newlist.iterator();
    });

    System.out.println();
    System.out.println("TransByCust new count: " + transByCust.count());

    JavaPairRDD<Integer, Double> amounts = transByCust.mapValues((String[] t) -> Double.parseDouble(t[5]));
    List<Tuple2<Integer, Double>> totals = amounts.foldByKey((double) 0, Double::sum).collect();
    System.out.println();
    System.out.println("Totals with 0 zero value: " + totals);

    Tuple2<Integer, Double> maxAmount = null;
    for (Tuple2<Integer, Double> am : totals) {
      if (maxAmount == null || am._2 > maxAmount._2) maxAmount = am;
    }

    System.out.println("Customer " + maxAmount._1 + " spent " + maxAmount._2);

    List<Tuple2<Integer, Double>> totals2 = amounts.foldByKey(100000.0, Double::sum).collect();
    System.out.println("Totals with 100000 zero value: " + totals2);

    String[] newTrans2 = {"2015-03-30", "11:59 PM", "76", "63", "1", "0.00"};
    complTrans.add(newTrans2);
    transByCust = transByCust.union(sc.parallelize(complTrans).mapToPair((String[] t) -> new Tuple2<>(Integer.valueOf(t[2]), t)));
    transByCust.map((Tuple2<Integer, String[]> t) -> String.join("#", t._2))
      .saveAsTextFile(hdfs + "/ch4/ch04output-transByCust");

    JavaPairRDD<Integer, List<String>> prods = transByCust.aggregateByKey(new ArrayList<>(), (List<String> prods2, String[] tran) -> {
      prods2.add(tran[3]);
      return prods2;
    }, (List<String> prods1, List<String> prods2) -> {
      prods1.addAll(prods2);
      return prods1;
    });
    System.out.println("Products per customer: " + prods.collect());

    JavaRDD<Integer> rdd = sc.parallelize(IntStream.range(1, 10000).boxed().collect(Collectors.toList()));
    System.out.println();
    System.out.println("Map without a shuffle: " + rdd.mapToPair((Integer x) -> new Tuple2<>(x, x * x))
      .map(Tuple2::swap)
      .count());
    System.out.println("Map with a shuffle: " + rdd.mapToPair((Integer x) -> new Tuple2<>(x, x * x))
      .reduceByKey(Integer::sum)
      .count());

    sc.close();
  }
}