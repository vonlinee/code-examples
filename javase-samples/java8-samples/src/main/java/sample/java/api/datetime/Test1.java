package sample.java.api.datetime;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Test1 {
	
	public static final DateTimeFormatter FORMAT_YMDHMS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	public static final DateTimeFormatter FORMAT_YMD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
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

		// LocalDate date1 = LocalDate.parse("2022-2-14", FORMAT_YMD); //解析报错
		LocalDate date2 = LocalDate.parse("2022-02-14", FORMAT_YMD);
		
		System.out.println(date2);
		
		Timestamp nowTimestamp = Timestamp.from(Instant.now());
		
		System.out.println(nowTimestamp);
		
		LocalDateTime dateTime = nowTimestamp.toLocalDateTime();
		
		LocalDateTime dt1 = LocalDate.parse("2022-02-14", DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
		
		System.out.println(dt1.format(FORMAT_YMDHMS));
	}
	
	public static LocalDateTime string2DateTime(String text, DateTimeFormatter formatter) {
		return LocalDateTime.parse(text, formatter);
	}
	
	public static LocalDateTime string2DateTime(String text) {
		return LocalDateTime.parse(text, FORMAT_YMDHMS);
	}
	
	public static LocalDate string2Date(String text) {
		return LocalDateTime.parse(text, FORMAT_YMDHMS).toLocalDate();
	}
	
	public static LocalDate string2Date(String text, DateTimeFormatter formatter) {
		return LocalDateTime.parse(text, formatter).toLocalDate();
	}
	
	public static String dateTime2String(LocalDateTime dateTime) {
		return dateTime.toString();
	}
	
	public static String nowAsString() {
		return LocalDateTime.now().format(FORMAT_YMDHMS).toString();
	}
	
	public static String plusDateTime(String timeUnit, LocalDateTime start) {
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
	
	public static int compare(LocalDateTime dt1, LocalDateTime dt2, long secondsOffset) {
		int i = dt1.compareTo(dt2.minusSeconds(secondsOffset));
		if (i < 0) {
			return i;
		}
		if ((i = dt1.compareTo(dt2.plusSeconds(secondsOffset))) > 0) {
			return i;
		}
		return 0;
	}


}
