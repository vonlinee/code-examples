package spark;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

public abstract class JavaSparkApplication extends SparkApplication {
  @Override
  public final void start(SparkSession spark) {
    JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());
    start(jsc);
    jsc.close();
  }

  public abstract void start(JavaSparkContext jsc);
}
