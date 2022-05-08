package utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.LoggerFactory;

public class LogUtils {

	static {
		// initializeLog4j("conf/log4j.properties");
	}

	public static void log(String format, Object ...arguments) {
		String threadName = Thread.currentThread().getName();
		String nowTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS"));
		System.out.printf("[%s][%s] => %s%n", nowTime, threadName, String.format(format, arguments));
	}

	public static void info(Class<?> clazz, String msg, Object... arguments) {
		LoggerFactory.getLogger(clazz).info(msg, arguments);
	}

	public static void info(String msg, Object... arguments) {
		LoggerFactory.getLogger(LogUtils.class).info(msg, arguments);
	}

	public static void debug(Class<?> clazz, String msg, Object... arguments) {
		LoggerFactory.getLogger(clazz).debug(msg, arguments);
	}

	/**
	 * 在默认路径下有一个配置的情况下，会先加载默认配置，再用指定配置去更新那些冲突项。指定配置中没有的依然会使用默认配置。
	 */
	public static void initializeLog4j(String configFilePath) {
        //check configuration file
        Path configurationPath = Paths.get(configFilePath);
        if (!Files.exists(configurationPath) || !Files.isRegularFile(configurationPath)) {
            return;
        }
        try {
            System.setProperty("log4j.defaultInitOverride", "1"); // 覆盖默认配置
            PropertyConfigurator.configure(configurationPath.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
