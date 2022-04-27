package sample.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogUtils {

	public static void log(String format, Object ...arguments) {
		String threadName = Thread.currentThread().getName();
		String nowTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS"));
		System.out.println(String.format("[%s][%s] => %s", nowTime, threadName, String.format(format, arguments)));
	}
}
