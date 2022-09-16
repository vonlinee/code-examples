package io.netty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class MyClass {
    private int years;
    private int months;
    private volatile int days;

    public void update(int years, int months, int days) {
        this.years = years;
        this.months = months;
        this.days = days;
    }

    public static void main(String[] args) {
        LocalDateTime parse = LocalDateTime.parse("2022/08/16 8:32:32", DateTimeFormatter.ofPattern("yyyy/M/d H:mm:ss"));

    }
}

class TestTask implements Runnable {

    MyClass clazz;

    @Override
    public void run() {
        clazz.update(2022, 4, 21);
    }
}