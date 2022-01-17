package code.example.java.api.datetime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Test {
	
	public static void main(String[] args) {
		System.out.println(caculateNextExecuteTime("1", LocalDateTime.now()));
	}
	
	
	private static String caculateNextExecuteTime(String timeUnit, LocalDateTime start) {
		LocalDateTime nextExecuteTime = null;
		switch (timeUnit) {
		case "1": //日
			nextExecuteTime = start.plusDays(7);
			break;
		case "2": //周
			nextExecuteTime = start.plusWeeks(1);
			break;
		case "3": //月
			nextExecuteTime = start.plusMonths(1);
			break;
		case "4": //年
			nextExecuteTime = start.plusYears(1);
			break;
		default:
			nextExecuteTime = start.plusDays(7);
			break;
		}
		return nextExecuteTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
}
