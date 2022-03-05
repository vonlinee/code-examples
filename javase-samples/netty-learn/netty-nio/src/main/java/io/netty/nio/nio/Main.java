package io.netty.nio.nio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {

    private static final DateTimeFormatter DT_FORMAT_YMDHMS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime ldt1 = LocalDateTime.parse("2025-01-31 20:24:03", DT_FORMAT_YMDHMS);


        System.out.println(ldt1 = ldt1.plusMonths(1));

        System.out.println(ldt1 = ldt1.plusMonths(1));
        System.out.println(ldt1 = ldt1.plusMonths(1));
        System.out.println(ldt1 = ldt1.plusMonths(1));



        int monthValue = now.getMonthValue();
        int monthValue1 = ldt1.getMonthValue();

        System.out.println(monthValue);
        System.out.println(monthValue1);
    }


}
