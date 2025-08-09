package spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Tuple2;

public class SparkLoggingDemo {

  static final Logger logger = LoggerFactory.getLogger(SparkLoggingDemo.class);

  static final String hdfs = "hdfs://192.168.65.130:9000";

  public static void main(String[] args) {
    SparkConf conf = new SparkConf();
    conf.setAppName("SparkShuffleDemo");
    conf.set("spark.eventLog.enabled", "true");
    conf.set("spark.eventLog.dir", "hdfs://192.168.65.130:9000/spark-logs");
    JavaSparkContext sc = new JavaSparkContext(conf);

    logger.info("this is some log {}", conf);

    JavaRDD<String> tranFile = sc.textFile(hdfs + "/ch4/ch04_data_transactions.txt");
    JavaRDD<String[]> tranData = tranFile.map((String line) -> line.split("#"));
    JavaPairRDD<Integer, String[]> transByCust = tranData.mapToPair((String[] tran) -> new Tuple2<>(Integer.valueOf(tran[2]), tran));

    logger.info("this is some log {}", conf);

    sc.close();
  }
}
