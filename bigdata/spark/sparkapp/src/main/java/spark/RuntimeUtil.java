package spark;

import java.lang.management.ManagementFactory;
import java.util.List;

/**
 * JVM运行时信息工具类
 */
class RuntimeUtil {

  /**
   * 获取当前JVM进程的主类名
   */
  public static String getMainClass() {
    // 方法1: 通过堆栈跟踪
    String fromStackTrace = getMainClassFromStackTrace();
    if (fromStackTrace != null) {
      return fromStackTrace;
    }

    // 方法2: 通过系统属性
    String fromSystemProperty = getMainClassFromSystemProperty();
    if (fromSystemProperty != null) {
      return fromSystemProperty;
    }

    // 方法3: 通过运行时MXBean
    return getMainClassFromRuntimeMXBean();
  }

  public static String getMainClassFromStackTrace() {
    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
    for (StackTraceElement element : stackTrace) {
      if ("main".equals(element.getMethodName())) {
        return element.getClassName();
      }
    }
    return null;
  }

  public static String getMainClassFromSystemProperty() {
    String command = System.getProperty("sun.java.command");
    if (command != null && !command.isEmpty()) {
      // 处理包含参数的情况
      String[] parts = command.split("\\s+");
      return parts[0];
    }
    return null;
  }

  public static String getMainClassFromRuntimeMXBean() {
    return ManagementFactory.getRuntimeMXBean().getSystemProperties()
      .getOrDefault("sun.java.command", "Unknown");
  }

  /**
   * 获取JVM启动参数
   */
  public static List<String> getJVMArguments() {
    return ManagementFactory.getRuntimeMXBean().getInputArguments();
  }

  /**
   * 获取JVM进程ID
   */
  public static long getProcessId() {
    String runtimeName = ManagementFactory.getRuntimeMXBean().getName();
    try {
      return Long.parseLong(runtimeName.split("@")[0]);
    } catch (Exception e) {
      return -1;
    }
  }

  /**
   * 打印完整的运行时信息
   */
  public static void printRuntimeInfo() {
    System.out.println("=== JVM运行时信息 ===");
    System.out.println("主类: " + getMainClass());
    System.out.println("进程ID: " + getProcessId());
    System.out.println("JVM参数: " + getJVMArguments());
    System.out.println("Java版本: " + System.getProperty("java.version"));
    System.out.println("JVM名称: " + ManagementFactory.getRuntimeMXBean().getVmName());
    System.out.println("启动时间: " + ManagementFactory.getRuntimeMXBean().getStartTime());
  }

  public static void main(String[] args) {
    printRuntimeInfo();
  }
}
