package spark;

import org.apache.spark.sql.SparkSession;

public class SparkSQLDemo {

  public static void main(String[] args) {
    SparkSession session = SparkSession.builder()
      .master("local[*]")
      .appName("SparkSQLDemo1")
      .getOrCreate();

  }
}
