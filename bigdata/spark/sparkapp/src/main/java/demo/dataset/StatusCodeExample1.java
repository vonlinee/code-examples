package demo.dataset;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;
import spark.SparkApplication;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StatusCodeExample1 extends SparkApplication {
  @Override
  public void start(SparkSession spark) {
    JavaSparkContext jsc = JavaSparkContext.fromSparkContext(spark.sparkContext());
    final Random random = new Random();
    // 订单状态数据
    JavaRDD<Integer> orderStatusRDD = jsc.parallelize(IntStream.range(0, 10000)
      .map((i) -> random.nextInt(5))
      .boxed().collect(Collectors.toList()), 10);

    // 状态码映射字典
    Map<Integer, String> statusMap = new HashMap<>();
    statusMap.put(0, "待支付");
    statusMap.put(1, "已支付");
    statusMap.put(2, "已发货");
    statusMap.put(3, "已完成");
    statusMap.put(4, "已取消");

    JavaRDD<Tuple2<Integer, String>> rdd1 = orderStatusRDD.map(
      (Function<Integer, Tuple2<Integer, String>>) integer -> Tuple2.apply(integer, statusMap.get(integer)));

    rdd1.collect().forEach(System.out::println);

//    // 广播状态字典
//    Broadcast<Map<Integer, String>> broadcastStatus =
//      spark.sparkContext().broadcast(statusMap, scala.reflect.ClassTag$.MODULE$.apply(Map.class));
//    JavaRDD<Tuple2<Integer, String>> rdd2 = orderStatusRDD.map(
//      (Function<Integer, Tuple2<Integer, String>>) integer -> Tuple2.apply(integer, broadcastStatus.value().get(integer)));
//    rdd2.collect().forEach(System.out::println);
  }
}
