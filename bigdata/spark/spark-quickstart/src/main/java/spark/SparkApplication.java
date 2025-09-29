package spark;

import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

public abstract class SparkApplication {

  private static final Logger bootstrapLogger = LoggerFactory.getLogger(SparkApplication.class);

  protected final Logger log = LoggerFactory.getLogger(getClass());

  public String getAppName() {
    return getClass().getName();
  }

  public boolean isLocal() {
    return true;
  }

  public String getMaster() {
    if (isLocal()) {
      return "local[*]";
    } else {
      return "yarn";
    }
  }

  public abstract void start(SparkSession spark);

  public static void main(String[] args) {
    String className;
    if (args.length == 0) {
      className = RuntimeUtil.getMainClassFromRuntimeMXBean();
    } else {
      className = args[0];
    }
    bootstrapLogger.info("driverClassName = {}", className);
    Class<?> appClass;
    try {
      appClass = Class.forName(className);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException("cannot find driver class: " + className, e);
    }
    if (!SparkApplication.class.isAssignableFrom(appClass)) {
      throw new IllegalArgumentException("appClass [" + appClass.getName() + "] is not a SparkApplication class");
    }

    SparkApplication app;
    try {
      bootstrapLogger.info("create driverClass: appClassName = {}", appClass.getName());
      app = (SparkApplication) appClass.getConstructor().newInstance();
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      throw new RuntimeException(e);
    }

    final String appName = app.getAppName();
    final String master = app.getMaster();

    final SparkSession.Builder builder = SparkSession.builder()
      .appName(appName)
      .master(master)  // 或者: yarn
      .config("spark.eventLog.enabled", "true")  // 开启事件日志
      .config("spark.eventLog.dir", "hdfs://localhost:8020/user/spark/eventLogs")  // 事件日志存放目录
      .config("spark.history.fs.logDirectory", "hdfs://localhost:8020/user/spark/eventLogs")
      .config("spark.yarn.historyServer.address", "http://localhost:18080") // 指定HistoryServer地址
      .config("spark.sql.adaptive.enabled", "true")
      .config("spark.sql.adaptive.coalescePartitions.enabled", "true");

    bootstrapLogger.info("HDFS: \thttp://localhost:9870");
    bootstrapLogger.info("Spark Web UI: \thttp://localhost:18080");
    SparkSession sparkSession = builder.getOrCreate();

    app.start(sparkSession);
  }
}
