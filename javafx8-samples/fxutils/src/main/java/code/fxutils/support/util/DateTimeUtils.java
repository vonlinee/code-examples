package code.fxutils.support.util;

import java.time.LocalDateTime;
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

    public static int compare(LocalDateTime dt1, LocalDateTime dt2, long secondsOffset) {
        return 1;
    }
}
