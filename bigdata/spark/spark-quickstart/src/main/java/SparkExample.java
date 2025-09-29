import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import spark.SparkApplication;

import java.util.Arrays;
import java.util.List;

public class SparkExample extends SparkApplication {

  @Override
  public void start(SparkSession spark) {
    JavaSparkContext sc = new JavaSparkContext(spark.sparkContext());
    List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
    JavaRDD<Integer> rdd = sc.parallelize(data);
  }
}
