package code.example.java.api.datetime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.Era;
import java.time.format.DateTimeFormatter;

public class Test {
	
	public static DateTimeFormatter FORMAT_YMDHMS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	public static DateTimeFormatter FORMAT_YMD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public void method() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String executeTime = "2022-01-24 00:00:00";
		LocalDateTime time = LocalDateTime.parse(executeTime, formatter);
		
	}
	
	public static void test1() {
		Object nullPointer1 = null;
		String nullPointer2 = null;
		System.out.println(nullPointer1 == nullPointer2);
		LocalDateTime ldt = LocalDateTime.now();
		LocalDate ld = LocalDate.now();
	}
	
	public static void main(String[] args) {
//		LocalDate now = LocalDate.now(); //2022-01-24
//		LocalDate d2 = LocalDate.parse("2022-01-25", FORMAT_YMD);
//		LocalDateTime starTime = d2.atStartOfDay();
//		System.out.println(starTime.format(FORMAT_YMDHMS));
		
		test1();
	}
	
	@SuppressWarnings("unused")
	private static String plusDateTime(String timeUnit, LocalDateTime start) {
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
