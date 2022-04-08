package org.setamv.shardingsphere.starter.sharding.algorithm;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class AlgorithmUtils {

    /**
     * 从参数dateValue中解析处年份信息
     * @param dateValue 日期值
     * @return
     */
    public static int parseYear(Object dateValue) {
        LocalDate date = parseLocalDate(dateValue);
        if (date == null) {
            throw new IllegalArgumentException("当前参数不支持获取年份。" + dateValue);
        }
        return date.getYear();
    }

    static final int DATE_LEN = "yyyy-MM-dd".length();

    /**
     * 从参数value中解析处LocalDate值
     * @param value 日期值，支持：<ul>
     *              <li>{@link LocalDate}</li>
     *              <li>{@link Date}</li>
     *              <li>{@link LocalDateTime}</li>
     *              <li>{@link String}，必须是以yyyy-MM-dd开头的格式</li>
     * </ul>
     * @return
     */
    public static LocalDate parseLocalDate(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof LocalDate) {
            return (LocalDate)value;
        } else if (value instanceof Date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime((Date)value);
            return LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        } else if (value instanceof LocalDateTime) {
            LocalDateTime localDateTime = (LocalDateTime)value;
            return localDateTime.toLocalDate();
        } else if (value instanceof String) {
            String datePart = (String)value;
            if (datePart.length() > DATE_LEN) {
                datePart = datePart.substring(0, DATE_LEN);
            }
            return LocalDate.parse(datePart, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else {
            throw new IllegalArgumentException("当前参数类型[" + value.getClass() + "]不支持获取格式化的日期。仅支持：java.util.Date、java.time.LocalDate、java.time.LocalDate");
        }
    }
}
