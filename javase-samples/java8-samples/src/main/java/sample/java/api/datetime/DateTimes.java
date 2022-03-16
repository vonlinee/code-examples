package sample.java.api.datetime;

public class DateTimes {

    /**
     * 是否是闰年
     * @param year
     * @return
     */
    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }
}
