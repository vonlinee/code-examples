package spark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class SparkSQLDemo {

  static final String HDFS = "hdfs://192.168.65.130:9000";

  public static void main(String[] args) {
    SparkSession session = SparkSession.builder()
      .master("local[*]")
      .appName("SparkSQLDemo1")
      .getOrCreate();

    Dataset<Row> dataset = session.read().text(HDFS + "/person.txt");

  }
}
