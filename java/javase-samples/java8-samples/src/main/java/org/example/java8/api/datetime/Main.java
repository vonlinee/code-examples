package org.example.java8.api.datetime;

import static org.example.java8.api.datetime.DateTimes.isLeapYear;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

public class Main {

    public void test1() {
        System.out.println(isLeapYear(2025));
        System.out.println(isLeapYear(2024));
        System.out.println(isLeapYear(2023));
        LocalDateTime dt = LocalDateTime.of(2024, 2, 30, 0, 0, 0);
        System.out.println(dt);
    }

    public void me() {
        for (int i = 1; i <= 12; i++) {
            System.out.println(Month.of(i).maxLength());
        }
    }

    public void m1() {
        LocalDate.of(2024, 2, 29);
    }
}
