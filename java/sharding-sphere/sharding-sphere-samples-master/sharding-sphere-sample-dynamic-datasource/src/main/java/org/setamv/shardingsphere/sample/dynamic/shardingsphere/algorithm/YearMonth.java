package org.setamv.shardingsphere.sample.dynamic.shardingsphere.algorithm;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * 年月对象。主要用于年月的比较
 * @author setamv
 * @date 2021-04-10 10:00:00
 */
public class YearMonth implements Comparable<YearMonth> {

    private int year;
    private int month;

    public YearMonth(int year, int month) {
        this.year = year;
        this.month = month;
    }

    /**
     * 返回格式化的年。如：2021
     * @return 格式化的年。如：2021
     */
    public String toFormattedYear() {
        return String.format("%04d", year);
    }

    /**
     * 返回格式化的年月。如：2021_04。
     * @return 格式化的年月。如：2021_04。
     */
    public String toFormattedMonth() {
        return String.format("%04d_%02d", year, month);
    }

    @Override
    public int compareTo(YearMonth o) {
        if (year == o.year) {
            return month - o.month;
        }
        return year - o.year;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new int[]{year, month});
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof YearMonth
                && year == ((YearMonth)obj).year
                && month == ((YearMonth)obj).month;
    }

    /**
     * 解析日期的年份和月份类型
     * @param date 支持以下类型的对象：{@link Date}、{@link LocalDate}、{@link LocalDateTime}
     */
    public static YearMonth parse(Object date) {
        if (date == null) {
            throw new IllegalArgumentException("日期类型的值不能为空");
        }
        int year;
        int month;
        if (date instanceof LocalDate) {
            LocalDate localDate = (LocalDate)date;
            year = localDate.getYear();
            month = localDate.getMonthValue();
        } else if (date instanceof Date) {
            Calendar calendar = getCalendar((Date)date);
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH) + 1;
        } else if (date instanceof LocalDateTime) {
            LocalDateTime localDateTime = (LocalDateTime)date;
            year = localDateTime.getYear();
            month = localDateTime.getMonthValue();
        } else {
            throw new IllegalArgumentException("当前参数类型[" + date.getClass() + "]不支持获取格式化的年月。仅支持：java.util.Date、java.time.LocalDate、java.time.LocalDateTime");
        }
        return new YearMonth(year, month);
    }

    public static Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }
}
