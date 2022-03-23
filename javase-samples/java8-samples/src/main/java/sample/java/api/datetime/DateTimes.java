package sample.java.api.datetime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimes {

    public static final DateTimeFormatter FORMAT_YMDHMS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter FORMAT_YMD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter FORMAT_HMS = DateTimeFormatter.ofPattern("HH:mm:ss");

    private DateTimes() {
    }

    /**
     * 是否是闰年
     *
     * @param year
     * @return
     */
    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    public static String nowTimeString() {
        return LocalDateTime.now().format(FORMAT_YMDHMS);
    }

    public static String format(LocalDateTime ldt) {
        return FORMAT_YMDHMS.format(ldt);
    }
}
