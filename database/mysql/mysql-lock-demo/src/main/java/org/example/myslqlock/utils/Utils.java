package org.example.myslqlock.utils;

import java.util.concurrent.TimeUnit;

public class Utils {

    public static void main(String[] args) {
        // 示例输入：数字和单位
        System.out.println(convertToReadableTime(65000, TimeUnit.MILLISECONDS)); // 65 秒
        System.out.println(convertToReadableTime(1, TimeUnit.MINUTES));            // 1 分
        System.out.println(convertToReadableTime(3600, TimeUnit.SECONDS));         // 1 小时
        System.out.println(convertToReadableTime(2, TimeUnit.HOURS));              // 2 小时
        System.out.println(convertToReadableTime(30, TimeUnit.NANOSECONDS));       // 0 秒
    }

    public static String convertToReadableTime(long value, TimeUnit unit) {
        // 将输入的时间值转换为毫秒
        long milliseconds = unit.toMillis(value);
        // 进行时间格式转换
        return formatReadableTime(milliseconds);
    }

    private static String formatReadableTime(long milliseconds) {
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        if (seconds < 60) {
            return seconds + " 秒";
        } else if (minutes < 60) {
            return minutes + " 分";
        } else {
            return hours + " 小时";
        }
    }
}