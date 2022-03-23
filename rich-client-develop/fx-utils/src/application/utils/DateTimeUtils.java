package application.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class DateTimeUtils {

	public static final String FORMAT_1 = "yy-MM-dd HH:mm:ss";
	public static final String FORMAT_2 = "yy-MM-dd HH:mm:ss SSS";

	private static final DateTimeFormatterBuilder formatterBuilder = new DateTimeFormatterBuilder();

	static {
		formatterBuilder.appendPattern("yy-MM-dd HH:mm:ss");
	}

	public static String nowTimeString() {
		return formatterBuilder.toFormatter().format(LocalDateTime.now());
	}

	public static final DateTimeFormatter DT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static LocalDateTime plusDateTime(LocalDateTime dt, DateTimeUnit timeUnit, int count) {
		switch (timeUnit) {
		case DAY: // 日
			dt = dt.plusDays(count);
			break;
		case WEEK: // 周
			dt = dt.plusWeeks(count);
			break;
		case MONTH: // 月
			dt = dt.plusMonths(count);
			break;
		case YEAR: // 年
			dt = dt.plusYears(count);
			break;
		default:
			break;
		}
		return dt;
	}

	public static LocalDateTime plusDateTime(LocalDateTime dt, String timeUnit, int count) {
		switch (timeUnit) {
		case "1": // 日
			dt = dt.plusDays(count);
			break;
		case "2": // 周
			dt = dt.plusWeeks(count);
			break;
		case "3": // 月
			dt = dt.plusMonths(count);
			break;
		case "4": // 年
			dt = dt.plusYears(count);
			break;
		default:
			break;
		}
		return dt;
	}

	public static int compareDateTime(LocalDateTime dt1, LocalDateTime dt2, long secondsOffset) {
		int i = dt1.compareTo(dt2.minusSeconds(secondsOffset));
		if (i < 0) {
			return i;
		}
		if ((i = dt1.compareTo(dt2.plusSeconds(secondsOffset))) > 0) {
			return i;
		}
		return 0;
	}

	public static LocalDateTime string2DateTime(String datetime) {
		LocalDateTime dt = null;
		try {
			dt = LocalDateTime.parse(datetime, DT_FORMAT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dt == null ? LocalDateTime.now() : dt;
	}

	public static LocalDateTime string2DateTime(String datetime, String format) {
		LocalDateTime dt = null;
		try {
			dt = LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern(format));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dt == null ? LocalDateTime.now() : dt;
	}

	public static String dateTime2String(LocalDateTime dt) {
		return dt.format(DT_FORMAT);
	}

	public static String dateTime2String(LocalDateTime dt, String format) {
		return dt.format(DateTimeFormatter.ofPattern(format));
	}

	public static String nowAsString() {
		return dateTime2String(LocalDateTime.now());
	}

	public static LocalDateTime now() {
		return LocalDateTime.now();
	}

	public static int compare(LocalDateTime dt1, LocalDateTime dt2, long secondsOffset) {
		return 1;
	}
}
